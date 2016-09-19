package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCommentRepositoryImpl implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    private UserRepository userRepository;
    private CommentMapper mapper = new CommentMapper();

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
            if(namedParameterJdbcTemplate.update(
                    "UPDATE comments SET content= :content, date= :date, user_id = :user_id WHERE id= :id", map) ==0) {
                return null;
            }
        }
        return comment;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM comments WHERE id=?", id) != 0;
    }

    @Override
    public Comment get(long id) {
        String sql = "SELECT id, content, date, user_id FROM comments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public List<Comment> getAllByVisitorId(long id) {
        String sql = "select c.id, c.content, c.date, c.user_id from visitors_comments vc " +
                "LEFT JOIN comments c on vc.comment_id = c.id WHERE vc.visitor_id = ?";
        return getAllById(sql, id);
    }

    @Override
    public List<Comment> getAllByEventId(long id) {
        String sql = "SELECT c.id, c.content, c.date, c.user_id FROM events_comments ec " +
                "LEFT JOIN comments c on ec.comment_id = c.id WHERE ec.event_id = ?";
        return getAllById(sql, id);
    }

    @Override
    public List<Comment> getAllByTaskId(long id) {
        String sql = "SELECT c.id, c.content, c.date, c.user_id FROM tasks_comments tc " +
                "LEFT JOIN comments c on tc.comment_id = c.id WHERE tc.task_id = ?";
        return getAllById(sql, id);
    }

    @Override
    public List<Comment> getAll() {
        String sql = "SELECT id, content, date, user_id FROM comments";
        return jdbcTemplate.query(sql, mapper);
    }

    private List<Comment> getAllById(String sql, long id) {
        return jdbcTemplate.query(sql, new Object[] {id}, (ResultSet rs) -> {
            Map<Long, User> userMap = new HashMap<>();
            List<Comment> commentList = new ArrayList<>();
            while(rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getLong("id"));
                comment.setContent(rs.getString("content"));
                comment.setDate(rs.getTimestamp("date").toLocalDateTime());
                Long userId = rs.getLong("user_id");
                User author = null;
                if(userMap.containsKey(userId)) {
                    author = userMap.get(userId);
                }
                else {
                    author = userRepository.get(userId);
                    userMap.put(userId, author);
                }
                comment.setAuthor(author);
                commentList.add(comment);
            }
            return commentList;
        });
    }

    private final class CommentMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
            Comment comment = new Comment();
            comment.setId(resultSet.getLong("id"));
            comment.setContent(resultSet.getString("content"));
            comment.setDate(resultSet.getTimestamp("date").toLocalDateTime());
            comment.setAuthor(userRepository.get(resultSet.getLong("user_id")));
            return comment;
        }
    }
}
