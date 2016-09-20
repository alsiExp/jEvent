package ru.jevent.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.jevent.model.Enums.Sex;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class JdbcHelper {

    private final JdbcTemplate jdbcTemplate;

    public JdbcHelper(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Map<Sex, Long> getSexMap() {
        String sql = "SELECT sex, id FROM person_sex";

        return jdbcTemplate.query(sql, (ResultSet rs) -> {
            Map<Sex, Long> map = new HashMap<>();
            while(rs.next()) {
                map.put(Sex.valueOf(rs.getString("sex")), rs.getLong("id"));
            }
            return map;
        });
    }
}
