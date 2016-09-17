package ru.jevent.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Enums.RateType;
import ru.jevent.model.Enums.SlotType;
import ru.jevent.model.*;
import ru.jevent.repository.CommentRepository;
import ru.jevent.repository.EventRepository;
import ru.jevent.repository.UserRepository;
import ru.jevent.repository.VisitorRepository;
import ru.jevent.util.exception.NotFoundException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcEventRepositoryImpl implements EventRepository {

    private static final BeanPropertyRowMapper<Event> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Event.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertEvent;

    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private VisitorRepository visitorRepository;

    private HashMap<Long, Rate> rateMap;
    private HashMap<Long, Visitor> visitorMap;

    @Autowired
    public JdbcEventRepositoryImpl(JdbcTemplate jdbcTemplate,
                                   NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                   DataSource dataSource,
                                   UserRepository userRepository,
                                   CommentRepository commentRepository,
                                   VisitorRepository visitorRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertEvent = new SimpleJdbcInsert(dataSource)
                .withTableName("events")
                .usingGeneratedKeyColumns("id");
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.visitorRepository = visitorRepository;
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
        String sql = "SELECT id, name, author_id, tag_name, address, description, logo_url FROM events WHERE id = ?";
        Event event = jdbcTemplate.queryForObject(sql, (rs, i) -> {
            Event e = new Event();
            e.setId(rs.getLong("id"));
            e.setName(rs.getString("name"));
            e.setAuthor(userRepository.get(rs.getLong("author_id")));
            e.setTagName(rs.getString("tag_name"));
            e.setAddress(rs.getString("address"));
            e.setDescription(rs.getString("description"));
            e.setLogoURL(rs.getString("logo_url"));
            return e;
        }, id);

        event.setRates(this.fillRatesList(event.getId()));
        event.setTracks(this.fillTracksList(event.getId()));
        event.setConfirmedVisitors(this.fillConfirmedVisitorsMap(event.getId()));
        event.setProbableSpeakers(this.fillProbableSpeakersMap(event.getId()));
        event.setCommentList(commentRepository.getAllByEventId(event.getId()));
        clearMaps();
        return event;
    }

    @Override
    public List<Event> getAll() {
        String sqlAllEventsWithRates = "select e.id, e.name, e.author_id, e.tag_name, e.description, e.logo_url, r.id, r.name, rt.type, r.start_date, r.end_date, r.cost from events e LEFT JOIN rates r on e.id = r.event_id LEFT JOIN rate_type rt on r.rate_type = rt.id";
        return null;
    }

    private List<Rate> fillRatesList(long eventId) {
        String sql = "SELECT r.id, r.name, rt.type, r.start_date, r.end_date, r.cost  FROM rates r " +
                "LEFT JOIN rate_type rt ON r.rate_type = rt.id WHERE r.event_id = ?";
        List<Rate> listRate = jdbcTemplate.query(sql, (rs, i) -> {
            Rate r = new Rate();
            r.setId(rs.getLong("id"));
            r.setName(rs.getString("name"));
            r.setRateType(RateType.valueOf(rs.getString("type")));
            r.setStart(rs.getTimestamp("start_date").toLocalDateTime().toLocalDate());
            r.setEnd(rs.getTimestamp("end_date").toLocalDateTime().toLocalDate());
            r.setCost(rs.getDouble("cost"));
            return r;
        }, eventId);
        for (Rate r : listRate) {
            getRateMap().put(r.getId(), r);
        }

        return listRate;

    }

    private List<Track> fillTracksList(long eventId) {
        String sql = "SELECT t.id AS track_id, t.name, t.description, s.id AS slot_id, s.name slot_name, s.start, " +
                "ves.visitor_id AS speaker_id, ves.price AS speaker_price, s.lecture_description, st.type, s.grade FROM tracks t " +
                "LEFT JOIN slots s ON t.id = s.track_id " +
                "LEFT JOIN slot_type st ON s.slot_type = st.id " +
                "LEFT JOIN visitors_events_speakers ves ON s.visitors_events_speaker_id = ves.id " +
                "WHERE t.event_id = ? ORDER BY  s.start";
        return jdbcTemplate.query(sql, new Object[]{eventId}, (ResultSet rs) -> {
            Map<Long, Track> map = new HashMap<>();
            Track track = null;
            while (rs.next()) {
                Long trackId = rs.getLong("track_id");
                track = map.get(trackId);
                if (track == null) {
                    track = new Track();
                    track.setId(trackId);
                    track.setName("name");
                    track.setDescription("description");
                    map.put(trackId, track);
                }
                Long slotId = rs.getLong("slot_id");
                if (slotId > 0) {
                    Slot slot = new Slot();
                    slot.setId(slotId);
                    slot.setName(rs.getString("slot_name"));
                    slot.setStart(rs.getTimestamp("start").toLocalDateTime());
                    long speakerId = rs.getLong("speaker_id");
                    if (speakerId > 0) {
                        Visitor v = visitorRepository.get(speakerId);
                        getVisitorMap().put(v.getId(), v);
                        slot.setApprovedSpeaker(v);
                    }
                    slot.setPrice(rs.getDouble("speaker_price"));
                    slot.setLectureDescription(rs.getString("lecture_description"));
                    slot.setSlotType(SlotType.valueOf(rs.getString("type")));
                    slot.setGrade(rs.getInt("grade"));
                    track.getSlotOrder().add(slot);
                }

            }

            return new ArrayList<>(map.values());
        });
    }

    private HashMap<Visitor, PayDetails> fillConfirmedVisitorsMap(long eventId) {
        String sql = "SELECT vev.visitor_id, vev.buy_date, r.id AS rate_id FROM events_by_rate_confirmed_visitors vev " +
                "LEFT JOIN rates r ON vev.rate_id = r.id WHERE r.event_id = ?";
        return jdbcTemplate.query(sql, new Object[]{eventId}, (ResultSet rs) -> {
            HashMap<Visitor, PayDetails> m = new HashMap<>();
            while (rs.next()) {
                Long visitorId = rs.getLong("visitor_id");
                Visitor v = getVisitorMap().get(visitorId);
                if (v == null) {
                    v = visitorRepository.get(visitorId);
                    getVisitorMap().put(visitorId, v);
                }
                PayDetails pd = new PayDetails();
                pd.setDate(rs.getTimestamp("buy_date").toLocalDateTime());
                Long rateId = rs.getLong("rate_id");
                Rate r = getRateMap().get(rateId);
                if (r == null) {
                    throw new NotFoundException("Wrong Event parse, Rate with id = " + rateId + " is not exist");
                }
                pd.setRate(r);
                m.put(v, pd);
            }
            return m;
        });
    }

    private HashMap<Visitor, OfferDetails> fillProbableSpeakersMap(long eventId) {
        String sql = "SELECT visitor_id, send_date, speech_name, speech_description, wish_price " +
                "FROM events_probable_speakers WHERE event_id = ?";

        return jdbcTemplate.query(sql, new Object[]{eventId}, (ResultSet rs) -> {
            HashMap<Visitor, OfferDetails> m = new HashMap<>();
            while (rs.next()) {
                Long visitorId = rs.getLong("visitor_id");
                Visitor v = getVisitorMap().get(visitorId);
                if (v == null) {
                    v = visitorRepository.get(visitorId);
                    getVisitorMap().put(visitorId, v);
                }
                OfferDetails od = new OfferDetails();
                od.setSendDate(rs.getTimestamp("send_date").toLocalDateTime());
                od.setSpeechName(rs.getString("speech_name"));
                od.setSpeechDescription(rs.getString("speech_description"));
                od.setWishPrice(rs.getDouble("wish_price"));
                m.put(v,od);
            }
            return m;
        });
    }

    private HashMap<Long, Rate> getRateMap() {
        if (rateMap == null) {
            rateMap = new HashMap<>();
        }
        return rateMap;
    }

    private HashMap<Long, Visitor> getVisitorMap() {
        if (visitorMap == null) {
            visitorMap = new HashMap<>();
        }
        return visitorMap;
    }

    private void clearMaps() {
        getRateMap().clear();
        getVisitorMap().clear();
    }
}
