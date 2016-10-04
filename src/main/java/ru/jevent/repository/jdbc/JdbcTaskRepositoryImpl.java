package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;
import ru.jevent.model.*;
import ru.jevent.model.Enums.CurrentTaskStatus;
import ru.jevent.repository.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTaskRepositoryImpl implements TaskRepository {

    private static final BeanPropertyRowMapper<Task> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Task.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertTask;
    private SimpleJdbcInsert insertTaskStatus;
    private InsertTaskEvents insertTaskEvents;
    private InsertTaskVisitors insertTaskVisitors;
    private InsertTaskPartners insertTaskPartners;
    private InsertTaskUserTarget insertTaskUserTarget;
    private InsertTasksComments insertTasksComments;

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private VisitorRepository visitorRepository;
    private PartnerRepository partnerRepository;
    private CommentRepository commentRepository;
    private JdbcHelper helper;
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
        this.insertTaskStatus = new SimpleJdbcInsert(dataSource)
                .withTableName("task_statuses")
                .usingGeneratedKeyColumns("id");
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.visitorRepository = visitorRepository;
        this.partnerRepository = partnerRepository;
        this.commentRepository = commentRepository;
        this.insertTaskEvents = new InsertTaskEvents(dataSource);
        this.insertTaskVisitors = new InsertTaskVisitors(dataSource);
        this.insertTaskPartners = new InsertTaskPartners(dataSource);
        this.insertTaskUserTarget = new InsertTaskUserTarget(dataSource);
        this.insertTasksComments = new InsertTasksComments(dataSource);
        this.helper = new JdbcHelper(dataSource);
    }

    @Override
    public Task save(Task task) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", task.getId());
        map.addValue("name", task.getName());
        if(task.getAuthor() != null) {
            map.addValue("user_id", task.getAuthor().getId());
        } else {
            map.addValue("user_id", null);
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

        if(!task.getAttachSet().isEmpty()) {
            Map<String, Object> visitorsMap = new HashMap<>();
            visitorsMap.put("task_id", task.getId());
            Map<String, Object> partnersMap = new HashMap<>();
            partnersMap.put("task_id", task.getId());
            Map<String, Object> eventsMap = new HashMap<>();
            eventsMap.put("task_id", task.getId());
            for(Attachable att : task.getAttachSet()) {
                if(att instanceof Visitor){
                    Visitor v = (Visitor) att;
                    visitorRepository.save(v);
                    visitorsMap.put("visitor_id", v.getId());
                    if(insertTaskVisitors.updateByNamedParam(visitorsMap) == 0) {
                        return null;
                    }
                }

                if(att instanceof Partner) {
                    Partner p = (Partner) att;
                    partnerRepository.save(p);
                    partnersMap.put("partner_id", p.getId());
                    if(insertTaskPartners.updateByNamedParam(partnersMap) == 0) {
                        return null;
                    }
                }

                if(att instanceof Event) {
                    Event e = (Event) att;
                    eventRepository.save(e);
                    eventsMap.put("event_id", e.getId());
                    if(insertTaskEvents.updateByNamedParam(eventsMap) == 0) {
                        return null;
                    }
                }
            }
            insertTaskVisitors.flush();
            insertTaskPartners.flush();
            insertTaskPartners.flush();
        }

        if(!task.getTarget().isEmpty()) {
            Map<String, Object> userTargetMap = new HashMap<>();
            userTargetMap.put("task_id", task.getId());
            for(User u : task.getTarget()) {
                if(u.isNew()) {
                    return null;
                }
                userTargetMap.put("user_id", u.getId());
                if(insertTaskUserTarget.updateByNamedParam(userTargetMap) == 0) {
                    return null;
                }
            }
        }
        insertTaskUserTarget.flush();

        if(!task.getStatusLog().isEmpty()) {
            MapSqlParameterSource taskStatusParamMap = new MapSqlParameterSource();
            taskStatusParamMap.addValue("task_id", task.getId());
            Map<CurrentTaskStatus, Long> currentTaskStatusMap= helper.getCurrentTaskStatusMap();
            for(TaskStatus status : task.getStatusLog()) {
                taskStatusParamMap.addValue("id", status.getId());
                if(status.getAuthor() == null) {
                    return null;
                }
                taskStatusParamMap.addValue("user_id", status.getAuthor().getId());
                taskStatusParamMap.addValue("creation_time", Timestamp.valueOf(status.getCreationTime()));
                taskStatusParamMap.addValue("current_task_status_id", currentTaskStatusMap.get(status.getStatus()));
                taskStatusParamMap.addValue("description", status.getDescription());
                if(status.isNew()) {
                    Number newKey = insertTaskStatus.executeAndReturnKey(taskStatusParamMap);
                    status.setId(newKey.longValue());
                } else {
                    if(namedParameterJdbcTemplate.update("UPDATE task_statuses SET user_id = :user_id, task_id = :task_id, " +
                            "creation_time  = :creation_time, current_task_status_id = :current_task_status_id, " +
                            "description = :description  WHERE id = :id", map) == 0) {
                        return null;
                    }
                }
            }
        }

        if(!task.getCommentList().isEmpty()) {
            Map<String, Object> commentsMap = new HashMap<>();
            commentsMap.put("task_id", task.getId());
            for (Comment c : task.getCommentList()) {
                Comment insertedComment = commentRepository.save(c);
                commentsMap.put("comment_id", insertedComment.getId());
                if(insertTasksComments.updateByNamedParam(commentsMap) == 0) {
                    return null;
                }
            }
            insertTasksComments.flush();
        }
        return task;
    }

    @Override
    public Task get(long id) {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description, t.active FROM tasks t WHERE t.id = ?";
        return jdbcTemplate.queryForObject(sql, taskMapper, id);
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM tasks WHERE id = ?", id) != 0;
    }

    @Override
    public List<Task> getAssignedByInterval(LocalDateTime start, LocalDateTime end, long userId) {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description, t.active " +
                "FROM task_user_target ut LEFT JOIN tasks t ON ut.task_id = t.id " +
                "WHERE  t.deadline >= ? AND t.deadline < ? AND ut.user_id = ? ORDER BY t.deadline";
        return jdbcTemplate.query(sql, taskMapper, Timestamp.valueOf(start), Timestamp.valueOf(end), userId);
    }

    @Override
    public List<Task> getAllCreated(long userId) {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description, t.active FROM tasks t " +
                "WHERE t.user_id = ? ORDER BY t.deadline";
        return jdbcTemplate.query(sql, taskMapper, userId);
    }

    @Override
    public List<Task> getAllAssigned(long userId) {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description, t.active " +
                "FROM task_user_target ut LEFT JOIN tasks t ON ut.task_id = t.id WHERE  ut.user_id = ? ORDER BY t.deadline";
        return jdbcTemplate.query(sql, taskMapper, userId);
    }

    private List<Task> getAll() {
        String sql = "SELECT t.id, t.name, t.user_id, t.start, t.deadline, t.description, t.active FROM tasks t ORDER BY t.deadline";
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
            task.setAttachSet(fillAttachList(task.getId()));
            task.setCommentList(helper.getAllByTaskId(task.getId()));
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
            ts.setAuthor(userRepository.get(rs.getLong("user_id")));
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
        set.addAll(jdbcTemplate.query(eventSql, (rs, i) -> eventRepository.get(rs.getLong("event_id")), id));
        set.addAll(jdbcTemplate.query(visitorSql, (rs, i) -> visitorRepository.get(rs.getLong("visitor_id")), id));
        set.addAll(jdbcTemplate.query(partnerSql, (rs, i) -> partnerRepository.get(rs.getLong("partner_id")), id));

        return set;
    }

    private final class InsertTaskEvents extends BatchSqlUpdate {
        private static final String SQL_INSERT_TASK_ATTACH_EVENTS = "INSERT INTO task_attach_events(task_id, event_id) " +
                "VALUES (:task_id, :event_id) ON CONFLICT(task_id, event_id) DO UPDATE " +
                "SET task_id = :task_id, event_id = :event_id";
        private static final int BATCH_SIZE = 10;

        InsertTaskEvents(DataSource ds) {
            super(ds, SQL_INSERT_TASK_ATTACH_EVENTS);
            super.declareParameter(new SqlParameter("task_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("event_id", Types.BIGINT));
            super.setBatchSize(BATCH_SIZE);
        }
    }

    private final class InsertTaskPartners extends BatchSqlUpdate {
        private static final String SQL_INSERT_TASK_ATTACH_PARTNERS = "INSERT INTO task_attach_partners(task_id, partner_id) " +
                "VALUES (:task_id, :partner_id) ON CONFLICT(task_id, partner_id) DO UPDATE " +
                "SET task_id = :task_id, partner_id = :partner_id";
        private static final int BATCH_SIZE = 10;

        InsertTaskPartners(DataSource ds) {
            super(ds, SQL_INSERT_TASK_ATTACH_PARTNERS);
            super.declareParameter(new SqlParameter("task_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("partner_id", Types.BIGINT));
            super.setBatchSize(BATCH_SIZE);
        }
    }

    private final class InsertTaskVisitors extends BatchSqlUpdate {
        private static final String SQL_INSERT_TASK_ATTACH_VISITORS = "INSERT INTO task_attach_visitors(task_id, visitor_id) " +
                "VALUES (:task_id, :visitor_id) ON CONFLICT(task_id, visitor_id) DO UPDATE " +
                "SET task_id = :task_id, visitor_id = :visitor_id";
        private static final int BATCH_SIZE = 10;

        InsertTaskVisitors(DataSource ds) {
            super(ds, SQL_INSERT_TASK_ATTACH_VISITORS);
            super.declareParameter(new SqlParameter("task_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("visitor_id", Types.BIGINT));
            super.setBatchSize(BATCH_SIZE);
        }
    }

    private final class InsertTaskUserTarget extends BatchSqlUpdate {
        private static final String SQL_INSERT_USER_TARGET = "INSERT INTO task_user_target(task_id, user_id) VALUES " +
                "(:task_id, :user_id) ON CONFLICT(task_id, user_id) DO UPDATE SET task_id = :task_id, user_id = :user_id";
        private static final int BATCH_SIZE = 10;
        InsertTaskUserTarget(DataSource ds) {
            super(ds, SQL_INSERT_USER_TARGET);
            super.declareParameter(new SqlParameter("task_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("user_id", Types.BIGINT));
            super.setBatchSize(BATCH_SIZE);
        }
    }

    private final class InsertTasksComments extends BatchSqlUpdate {
        private static final String SQL_INSERT_TASKS_COMMENTS = "INSERT INTO tasks_comments(task_id, comment_id) " +
                "VALUES (:task_id, :comment_id) ON CONFLICT (task_id, comment_id) DO UPDATE SET " +
                "task_id = :task_id, comment_id = :comment_id";
        private static final int BATCH_SIZE = 10;

        public InsertTasksComments(DataSource ds) {
            super(ds, SQL_INSERT_TASKS_COMMENTS);
            super.declareParameter(new SqlParameter("task_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("comment_id", Types.BIGINT));
            super.setBatchSize(BATCH_SIZE);
        }
    }
}
