package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Comment;
import ru.jevent.model.Enums.CurrentTaskStatus;
import ru.jevent.model.Enums.RateType;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.Enums.SlotType;
import ru.jevent.model.User;
import ru.jevent.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
class JdbcHelper {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;

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



    public List<Comment> getAllByVisitorId(long id) {
        String sql = "select c.id, c.content, c.date, c.user_id from visitors_comments vc " +
                "LEFT JOIN comments c on vc.comment_id = c.id WHERE vc.visitor_id = ? ORDER BY c.date";
        return getAllById(sql, id);
    }


    public List<Comment> getAllByEventId(long id) {
        String sql = "SELECT c.id, c.content, c.date, c.user_id FROM events_comments ec " +
                "LEFT JOIN comments c on ec.comment_id = c.id WHERE ec.event_id = ? ORDER BY c.date";
        return getAllById(sql, id);
    }


    public List<Comment> getAllByTaskId(long id) {
        String sql = "SELECT c.id, c.content, c.date, c.user_id FROM tasks_comments tc " +
                "LEFT JOIN comments c on tc.comment_id = c.id WHERE tc.task_id = ? ORDER BY c.date";
        return getAllById(sql, id);
    }

    List<Comment> getAllById(String sql, long id) {
        return jdbcTemplate.query(sql, new Object[]{id}, (ResultSet rs) -> {
            Map<Long, User> userMap = new HashMap<>();
            List<Comment> commentList = new ArrayList<>();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getLong("id"));
                comment.setContent(rs.getString("content"));
                comment.setDate(rs.getTimestamp("date").toLocalDateTime());
                Long userId = rs.getLong("user_id");
                User author = null;
                if (userMap.containsKey(userId)) {
                    author = userMap.get(userId);
                } else {
                    author = userRepository.get(userId);
                    userMap.put(userId, author);
                }
                comment.setAuthor(author);
                commentList.add(comment);
            }
            return commentList;
        });
    }

}
