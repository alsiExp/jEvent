package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Task;
import ru.jevent.repository.TaskRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcTaskRepositoryImpl implements TaskRepository {

    private static final BeanPropertyRowMapper<Task> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Task.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    @Autowired
    public JdbcTaskRepositoryImpl(JdbcTemplate jdbcTemplate,
                                     NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                     DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertComment = new SimpleJdbcInsert(dataSource)
                .withTableName("tasks")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Task save(Task task, long userId) {
        return null;
    }

    @Override
    public Task get(long id, long userId) {
        return null;
    }

    @Override
    public boolean delete(long id, long userId) {
        return false;
    }

    @Override
    public List<Task> getByInterval(LocalDateTime start, LocalDateTime end, long userId) {
        return null;
    }

    @Override
    public List<Task> getAllCreated(long userId) {
        return null;
    }

    @Override
    public List<Task> getAllAssigned(long userId) {
        return null;
    }
}
