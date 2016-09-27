package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.*;
import ru.jevent.model.Enums.CurrentTaskStatus;
import ru.jevent.repository.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class JdbcTaskRepositoryImpl implements TaskRepository {

    private static final BeanPropertyRowMapper<Task> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Task.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertTask;

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private VisitorRepository visitorRepository;
    private PartnerRepository partnerRepository;
    private CommentRepository commentRepository;

    private TaskMapper taskMapper = new TaskMapper();

    @Autowired
    public JdbcTaskRepositoryImpl(JdbcTemplate jdbcTemplate,
                                  NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                  DataSource dataSource,
                                  UserRepository userRepository,
                                  VisitorRepository visitorRepository,
                                  PartnerRepository partnerRepository,
                                  EventRepository eventRepository,
                                  CommentRepository commentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertTask = new SimpleJdbcInsert(dataSource)
                .withTableName("tasks")
                .usingGeneratedKeyColumns("id");
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.visitorRepository = visitorRepository;
        this.partnerRepository = partnerRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Task save(Task task) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", task.getId());
        map.addValue("name", task.getName());
        if(task.getAuthor() != null) {
            map.addValue("author_id", task.getAuthor().getId());
        } else {
            map.addValue("author_id", null);
        }
        map.addValue("start", Timestamp.valueOf(task.getStart()));
        map.addValue("deadline", Timestamp.valueOf(task.getDeadline()));
        map.addValue("description", task.getDescription());
        map.addValue("active", task.isActive());

        if(task.isNew()) {
            Number newKey = insertTask.executeAndReturnKey(map);
            task.setId(newKey.longValue());
        } else {
            if(namedParameterJdbcTemplate.update("UPDATE tasks SET name = :name, user_id = :author_id, start = :start, " +
                    "deadline = :deadline, description = :description, active = :active WHERE id = :id", map) == 0) {
                return null;
            }
        }

        if(!task.getAttachList().isEmpty()) {
            for(Attachable att : task.getAttachList()) {
                if(att instanceof Visitor){
                    Visitor v = (Visitor) att;
                    visitorRepository.save(v);
                }

                if(att instanceof Partner) {
                    Partner p = (Partner) att;
                    partnerRepository.save(p);

                }

                if(att instanceof Event) {
                    Event e = (Event) att;
                    eventRepository.save(e);
                }
            }
        }

        return null;
    }

    @Override
    public Task get(long id) {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description, t.active FROM tasks t WHERE t.id = ?";
        return jdbcTemplate.queryForObject(sql, taskMapper, id);
    }

    @Override
    public boolean delete(long id) {
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

    private List<Task> getAll() {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description, t.active FROM tasks t ORDER BY t.start";
        return jdbcTemplate.query(sql, taskMapper);
    }

    private final class TaskMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int i) throws SQLException {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setName(rs.getString("name"));
            task.setAuthor(userRepository.get(rs.getLong("user_id")));
            task.setStart(rs.getTimestamp("start").toLocalDateTime());
            task.setDeadline(rs.getTimestamp("deadline").toLocalDateTime());
            task.setDescription(rs.getString("description"));
            task.setActive(rs.getBoolean("active"));
            task.setStatusLog(fillStatuses(task.getId()));
            task.setTarget(fillTargetUsers(task.getId()));
            task.setAttachList(fillAttachList(task.getId()));
            task.setCommentList(commentRepository.getAllByTaskId(task.getId()));
            return task;
        }
    }

    private List<TaskStatus> fillStatuses(Long id) {
        String sql = "SELECT ts.id , ts.user_id, ts.task_id, ts.creation_time, ts.description, cts.status from task_statuses ts " +
                "LEFT JOIN  current_task_status cts ON ts.current_task_status_id = cts.id " +
                "WHERE ts.task_id = ? ORDER BY  ts.creation_time";
        return jdbcTemplate.query(sql, (rs, i) -> {
            TaskStatus ts = new TaskStatus();
            ts.setId(rs.getLong("id"));
            ts.setAutor(userRepository.get(rs.getLong("user_id")));
            ts.setCreationTime(rs.getTimestamp("creation_time").toLocalDateTime());
            ts.setDescription(rs.getString("description"));
            ts.setStatus(CurrentTaskStatus.valueOf(rs.getString("status")));
            return ts;
        }, id);
    }

    private Set<User> fillTargetUsers(Long id) {
        String sql = "SELECT user_id from task_user_target WHERE task_id = ?";
        return jdbcTemplate.query(sql, new Object[] {id}, (ResultSet rs) -> {
            HashSet<User> set = new HashSet<User>();
            while (rs.next()) {
                User user = userRepository.get(rs.getLong("user_id"));
                set.add(user);
            }
            return set;
        });
    }

    private Set<Attachable> fillAttachList(Long id) {
        String eventSql = "SELECT event_id from task_attach_events WHERE task_id = ?";
        String visitorSql = "SELECT visitor_id FROM task_attach_visitors WHERE task_id = ?";
        String partnerSql = "SELECT partner_id FROM task_attach_partners WHERE task_id = ?";
        HashSet<Attachable> set = new HashSet<>();
        set.addAll(jdbcTemplate.query(eventSql, (rs, i) -> {
            return eventRepository.get(rs.getLong("event_id"));
        }, id));
        set.addAll(jdbcTemplate.query(visitorSql, (rs, i) -> {
            return visitorRepository.get(rs.getLong("visitor_id"));
        }, id));
        set.addAll(jdbcTemplate.query(partnerSql, (rs, i) -> {
            return partnerRepository.get(rs.getLong("partner_id"));
        }, id));

        return set;
    }


}
