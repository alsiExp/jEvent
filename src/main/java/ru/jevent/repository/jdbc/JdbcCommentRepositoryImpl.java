package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Comment;
import ru.jevent.repository.CommentRepository;
import ru.jevent.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

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
    public Comment save(Comment comment) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", comment.getId())
                .addValue("content", comment.getContent())
                .addValue("date", Timestamp.valueOf(comment.getDate()));
        if (!comment.getAuthor().isNew()) {
            map.addValue("user_id", comment.getAuthor().getId());
        } else {
            return null;
        }
        if (comment.isNew()) {
            Number newKey = insertComment.executeAndReturnKey(map);
            comment.setId(newKey.longValue());
        } else {
            if(namedParameterJdbcTemplate.update(
                    "UPDATE comments SET content= :content, date= :date, user_id = :user_id WHERE id= :id", map) == 0) {
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
    public List<Comment> getAll() {
        String sql = "SELECT id, content, date, user_id FROM comments ORDER BY date";
        return jdbcTemplate.query(sql, mapper);
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
