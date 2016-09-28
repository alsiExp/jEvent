package ru.jevent.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.jevent.model.Enums.CurrentTaskStatus;
import ru.jevent.model.Enums.RateType;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.Enums.SlotType;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

class JdbcHelper {

    private final JdbcTemplate jdbcTemplate;

    JdbcHelper(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Map<Sex, Long> getSexMap() {
        String sql = "SELECT sex, id FROM person_sex";

        return jdbcTemplate.query(sql, (ResultSet rs) -> {
            Map<Sex, Long> map = new HashMap<>();
            while(rs.next()) {
                map.put(Sex.valueOf(rs.getString("sex")), rs.getLong("id"));
            }
            return map;
        });
    }

    Map<RateType, Long> getRateTypeMap() {
        String sql = "SELECT type, id FROM rate_type";

        return jdbcTemplate.query(sql, (ResultSet rs) -> {
            Map<RateType, Long> map = new HashMap<>();
            while(rs.next()) {
                map.put(RateType.valueOf(rs.getString("type")), rs.getLong("id"));
            }
            return map;
        });
    }

    Map<SlotType, Long> getSlotTypeMap() {
        String sql = "SELECT type, id FROM slot_type";

        return jdbcTemplate.query(sql, (ResultSet rs) -> {
            Map<SlotType, Long> map = new HashMap<>();
            while(rs.next()) {
                map.put(SlotType.valueOf(rs.getString("type")), rs.getLong("id"));
            }
            return map;
        });
    }

    Map<CurrentTaskStatus, Long> getCurrentTaskStatusMap() {
        String sql = "SELECT status, id FROM current_task_status";

        return jdbcTemplate.query(sql, (ResultSet rs) -> {
            Map<CurrentTaskStatus, Long> map = new HashMap<>();
            while(rs.next()) {
                map.put(CurrentTaskStatus.valueOf(rs.getString("status")), rs.getLong("id"));
            }
            return map;
        });
    }

}
