package ru.jevent.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Event;
import ru.jevent.model.Rate;
import ru.jevent.model.Track;
import ru.jevent.model.Visitor;
import ru.jevent.repository.CommentRepository;
import ru.jevent.repository.EventRepository;
import ru.jevent.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

@Repository
public class JdbcEventRepositoryImpl implements EventRepository {

    private static final BeanPropertyRowMapper<Event> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Event.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Autowired
    public JdbcEventRepositoryImpl(JdbcTemplate jdbcTemplate,
                                   NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                   DataSource dataSource,
                                   UserRepository userRepository,
                                   CommentRepository commentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertComment = new SimpleJdbcInsert(dataSource)
                .withTableName("events")
                .usingGeneratedKeyColumns("id");
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Event save(Event event) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Event get(long id) {
        String sql = "SELECT id, name, author_id, tag_name, description, logo_url FROM events WHERE id = ?";
        Event event = jdbcTemplate.queryForObject(sql, (rs, i) -> {
            Event e = new Event();
            e.setId(rs.getLong("id"));
            e.setName(rs.getString("name"));
            e.setAuthor(userRepository.get(rs.getLong("author_id")));
            e.setTagName(rs.getString("tag_name"));
            e.setDescription(rs.getString("description"));
            e.setLogoURL(rs.getString("logo_url"));
            return e;
        }, id);

        event.setRates(this.getRatesList(event.getId()));
        event.setTracks(this.getTracksList(event.getId()));
        event.setProbableSpeakers(this.getSpeakersSet(event.getId()));
        event.setConfirmedVisitors(this.getConfirmedVisitorsSet(event.getId()));
//        event.setCommentList(commentRepository.getAllByEventId(event.getId()));

        return event;
    }

    @Override
    public List<Event> getAll() {
        String sqlAllEventsWithRates = "select e.id, e.name, e.author_id, e.tag_name, e.description, e.logo_url, r.id, r.name, rt.type, r.start_date, r.end_date, r.cost from events e LEFT JOIN rates r on e.id = r.event_id LEFT JOIN rate_type rt on r.rate_type = rt.id";
        return null;
    }

    private List<Rate> getRatesList(long eventId) {
        return null;
    }

    private List<Track> getTracksList(long eventId) {
        return null;
    }

    private Set<Visitor> getConfirmedVisitorsSet(long eventId) {
        return null;
    }

    private Set<Visitor> getSpeakersSet(long eventId) {
        return null;
    }

}
