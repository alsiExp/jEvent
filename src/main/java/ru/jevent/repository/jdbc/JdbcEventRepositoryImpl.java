package ru.jevent.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Event;
import ru.jevent.repository.EventRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcEventRepositoryImpl implements EventRepository {

    private static final BeanPropertyRowMapper<Event> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Event.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    @Autowired
    public JdbcEventRepositoryImpl(JdbcTemplate jdbcTemplate,
                                   NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                   DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertComment = new SimpleJdbcInsert(dataSource)
                .withTableName("events")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Event save(Event event) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Event get(long id) {
        return null;
    }

    @Override
    public List<Event> getAll() {
        return null;
    }
}
