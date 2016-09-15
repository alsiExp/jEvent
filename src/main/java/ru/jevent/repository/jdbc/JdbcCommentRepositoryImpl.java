package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Comment;
import ru.jevent.model.User;
import ru.jevent.repository.CommentRepository;
import ru.jevent.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCommentRepositoryImpl implements CommentRepository {

    private static final BeanPropertyRowMapper<Comment> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Comment.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    private UserRepository userRepository;

    @Autowired
    public JdbcCommentRepositoryImpl(JdbcTemplate jdbcTemplate,
                                     NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                     DataSource dataSource,
                                     UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertComment = new SimpleJdbcInsert(dataSource)
                .withTableName("comments")
                .usingGeneratedKeyColumns("id");

        this.userRepository = userRepository;
    }


    @Override
    public Comment save(Comment comment, long userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", comment.getId())
                .addValue("content", comment.getContent())
                .addValue("date", Timestamp.valueOf(comment.getDate()))
                .addValue("user_id", userId);
        if (comment.isNew()) {
            Number newKey = insertComment.executeAndReturnKey(map);
            comment.setId(newKey.longValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE comments SET content=:content, date=:date, user_id =: user_id WHERE id=:id", map);
        }
        return comment;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Comment get(long id) {
        String sql = "SELECT id, content, date, user_id FROM comments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, i) -> {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setContent(rs.getString("content"));
            comment.setDate(rs.getTimestamp("date").toLocalDateTime());
            comment.setAuthor(userRepository.get(rs.getLong("user_id")));
            return comment;
        }, id);
    }

    @Override
    public List<Comment> getAllByVisitorId(long id) {
        String sql = "select c.id, c.content, c.date, c.user_id from visitors_comments vc " +
                "LEFT JOIN comments c on vc.comment_id = c.id WHERE vc.visitor_id = ?";
        return jdbcTemplate.query(sql, new Object[] {id}, (ResultSet rs) -> {
            Map<Long, User> userMap = new HashMap<>();
            List<Comment> commentList = new ArrayList<>();
            while(rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getLong("id"));
                comment.setContent(rs.getString("content"));
                comment.setDate(rs.getTimestamp("date").toLocalDateTime());
                Long autorId = rs.getLong("user_id");
                User author = null;
                if(userMap.containsKey(autorId)) {
                    author = userMap.get(autorId);
                }
                else {
                    author = userRepository.get(autorId);
                    userMap.put(autorId, author);
                }
                comment.setAuthor(author);
                commentList.add(comment);
            }
            return commentList;
        });
    }
}
