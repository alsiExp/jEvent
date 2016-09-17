package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Attachable;
import ru.jevent.model.Enums.CurrentTaskStatus;
import ru.jevent.model.Task;
import ru.jevent.model.TaskStatus;
import ru.jevent.model.User;
import ru.jevent.repository.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.*;

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
        return null;
    }

    @Override
    public Task get(long id) {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description FROM tasks t WHERE t.id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, i) -> {
            Task t = new Task();
            t.setId(rs.getLong("id"));
            t.setName(rs.getString("name"));
            t.setAuthor(userRepository.get(rs.getLong("user_id")));
            t.setStart(rs.getTimestamp("start").toLocalDateTime());
            t.setDeadline(rs.getTimestamp("deadline").toLocalDateTime());
            t.setDescription(rs.getString("description"));
            t.setStatusLog(fillStatuses(t.getId()));
            t.setTarget(fillTargetUsers(t.getId()));
            t.setAttachList(fillAttachList(t.getId()));
            t.setCommentList(commentRepository.getAllByTaskId(t.getId()));
            return t;
        }, id);
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

    private List<Task> getAll(long id) {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description, ts.id AS task_status_id, " +
                "ts.user_id AS status_author, ts.creation_time, ts.description AS task_status_description, cts.status FROM tasks t " +
                "LEFT JOIN task_statuses_tasks tst ON t.id = tst.task_id " +
                "LEFT JOIN task_statuses ts ON tst.task_status_id = ts.id " +
                "LEFT JOIN  current_task_status cts ON ts.current_task_status_id = cts.id " +
                "ORDER BY  ts.creation_time";

        return jdbcTemplate.query(sql, new Object[]{id}, (ResultSet rs) -> {
            HashMap<Long, Task> map = new HashMap<>();
            Task t = null;
            while (rs.next()) {
                Long taskId = rs.getLong("id");
                t = map.get(taskId);
                if (t == null) {
                    t = new Task();
                    t.setId(taskId);
                    t.setName(rs.getString("name"));
                    t.setAuthor(userRepository.get(rs.getLong("user_id")));
                    t.setStart(rs.getTimestamp("start").toLocalDateTime());
                    t.setDeadline(rs.getTimestamp("deadline").toLocalDateTime());
                    t.setDescription(rs.getString("description"));
                    map.put(taskId, t);
                }
                Long statusId = rs.getLong("task_status_id");
                if (statusId > 0) {
                    TaskStatus ts = new TaskStatus();
                    ts.setId(statusId);
                    ts.setAutor(userRepository.get(rs.getLong("status_author")));
                    ts.setCreationTime(rs.getTimestamp("creation_time").toLocalDateTime());
                    ts.setDescription(rs.getString("task_status_description"));
                    ts.setStatus(CurrentTaskStatus.valueOf(rs.getString("status")));
                    t.getStatusLog().add(ts);
                }
            }
            return new ArrayList<Task>(map.values());
        });
    }

    private List<TaskStatus> fillStatuses(Long id) {
        String sql = "SELECT ts.id , ts.user_id, ts.creation_time, ts.description, cts.status from task_statuses_tasks tst " +
                "LEFT JOIN task_statuses ts ON tst.task_status_id = ts.id " +
                "LEFT JOIN  current_task_status cts ON ts.current_task_status_id = cts.id " +
                "WHERE tst.task_id = ? ORDER BY  ts.creation_time";
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
