package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.User;
import ru.jevent.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    private UserMapper mapper = new UserMapper();

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
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", user.getId());
        map.addValue("first_name", user.getFirstName());
        map.addValue("last_name", user.getLastName());
        map.addValue("sex", getSexMap().get(user.getSex()));
        map.addValue("enabled", user.isEnabled());
        map.addValue("photo_url", user.getPhotoURL());
        map.addValue("login", user.getLogin());
        map.addValue("password", user.getPassword());
        if(user.isNew()) {
            Number newKey = insertComment.executeAndReturnKey(map);
            user.setId(newKey.longValue());
        } else {
            if(namedParameterJdbcTemplate.update(
                    "UPDATE users SET first_name = :first_name, last_name = :last_name, sex = :sex, enabled = :enabled, " +
                            "photo_url = :photo_url, login = :login, password = :password WHERE id= :id",map) == 0) {
                return null;
            }
        }
        return user;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?", id) != 0;
    }

    @Override
    public User get(long id) {
        String sql = "select u.id, u.first_name, u.last_name, s.sex, u.enabled, u.photo_url, u.login, u.password " +
                "from users u LEFT JOIN person_sex s on u.sex =s.id  WHERE u.id = ?";

        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT  u.id, u.first_name, u.last_name, s.sex, u.enabled, u.photo_url, u.login, u.password " +
                "FROM users u LEFT JOIN person_sex s on u.sex =s.id";
        return jdbcTemplate.query(sql, mapper);
    }


    private Map<Sex, Long> getSexMap() {
        String sql = "SELECT sex, id FROM person_sex";

        return jdbcTemplate.query(sql, (ResultSet rs) -> {
            Map<Sex, Long> map = new HashMap<>();
            while(rs.next()) {
                map.put(Sex.valueOf(rs.getString("sex")), rs.getLong("id"));
            }
            return map;
        });
    }

    private final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setSex(Sex.valueOf(rs.getString("sex")));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setPhotoURL(rs.getString("photo_url"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

}
