package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Enums.Role;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.User;
import ru.jevent.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

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
        String sql = "select u.id, u.first_name, u.last_name, s.sex, u.enabled, u.photo_url, u.login, u.password, ur.role " +
                "from users u LEFT JOIN person_sex s on u.sex =s.id LEFT JOIN user_roles ur on u.role = ur.id WHERE u.id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, i) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setSex(Sex.valueOf(rs.getString("sex")));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setPhotoURL(rs.getString("photo_url"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.addRoles(Role.valueOf(rs.getString("role")));

            return user;
        }, id);
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
