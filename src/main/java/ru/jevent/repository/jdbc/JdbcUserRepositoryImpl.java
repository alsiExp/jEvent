package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.User;
import ru.jevent.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate,
                                  NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                  DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertComment = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
