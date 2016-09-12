package ru.jevent.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.User;
import ru.jevent.model.Visitor;
import ru.jevent.repository.VisitorRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcVisitorRepositoryImpl implements VisitorRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    @Autowired
    public JdbcVisitorRepositoryImpl(JdbcTemplate jdbcTemplate,
                                  NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                  DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertComment = new SimpleJdbcInsert(dataSource)
                .withTableName("visitors")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Visitor save(Visitor visitor) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Visitor get(long id) {
        return null;
    }

    @Override
    public Visitor getByEmail(String email) {
        return null;
    }

    @Override
    public List<Visitor> getAll() {
        return null;
    }
}
