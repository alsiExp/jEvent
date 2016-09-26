package ru.jevent.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.jevent.model.*;
import ru.jevent.model.Enums.RateType;
import ru.jevent.model.Enums.SlotType;
import ru.jevent.repository.CommentRepository;
import ru.jevent.repository.EventRepository;
import ru.jevent.repository.UserRepository;
import ru.jevent.repository.VisitorRepository;
import ru.jevent.util.exception.NotFoundException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcEventRepositoryImpl implements EventRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertEvent;
    private InsertProbableSpeakers insertProbableSpeaker;
    private InsertConfirmedVisitors insertConfirmedVisitor;
    private InsertVisitorsEventsSpeakers insertVisitorsEventsSpeakers;
    private InsertEventsComments insertEventsComments;
    private SimpleJdbcInsert insertRate;
    private SimpleJdbcInsert insertTrack;
    private SimpleJdbcInsert insertSlot;

    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private VisitorRepository visitorRepository;
    private JdbcHelper helper;

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
        this.insertRate = new SimpleJdbcInsert(dataSource)
                .withTableName("rates")
                .usingGeneratedKeyColumns("id");
        this.insertTrack = new SimpleJdbcInsert(dataSource)
                .withTableName("tracks")
                .usingGeneratedKeyColumns("id");
        this.insertSlot = new SimpleJdbcInsert(dataSource)
                .withTableName("slots")
                .usingGeneratedKeyColumns("id");

        this.insertProbableSpeaker = new InsertProbableSpeakers(dataSource);
        this.insertConfirmedVisitor = new InsertConfirmedVisitors(dataSource);
        this.insertVisitorsEventsSpeakers = new InsertVisitorsEventsSpeakers(dataSource);
        this.insertEventsComments = new InsertEventsComments(dataSource);

        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.visitorRepository = visitorRepository;
        this.helper = new JdbcHelper(dataSource);
    }

    @Override
    public Event save(Event event) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", event.getId());
        map.addValue("name", event.getName());
        map.addValue("author_id", event.getAuthor().getId());
        map.addValue("tag_name", event.getTagName());
        map.addValue("address", event.getAddress());
        map.addValue("description", event.getDescription());
        map.addValue("logo_url", event.getLogoURL());
        if (event.isNew()) {
            Number newKey = insertEvent.executeAndReturnKey(map);
            event.setId(newKey.longValue());
        } else {
            if (namedParameterJdbcTemplate.update("UPDATE events SET name = :name, author_id = :author_id, tag_name = :tag_name, " +
                    "address = :address, description = :description, logo_url = :logo_url WHERE id = :id", map) == 0) {
                return null;
            }
        }
        if (!event.getProbableSpeakers().isEmpty()) {
            Map<String, Object> psMap = new HashMap<>();
            for (Map.Entry<Visitor, OfferDetails> entry : event.getProbableSpeakers().entrySet()) {
                if(entry.getKey().isNew()) {
                    visitorRepository.save(entry.getKey());
                }
                psMap.put("visitor_id", entry.getKey().getId());
                psMap.put("event_id", event.getId());
                psMap.put("send_date", Timestamp.valueOf(entry.getValue().getSendDate()));
                psMap.put("speech_name", entry.getValue().getSpeechName());
                psMap.put("speech_description", entry.getValue().getSpeechDescription());
                psMap.put("wish_price", entry.getValue().getWishPrice());
                if (insertProbableSpeaker.updateByNamedParam(psMap) == 0) {
                    return null;
                }
            }
            insertProbableSpeaker.flush();
        }

        if (!event.getRates().isEmpty()) {
            MapSqlParameterSource ratesParamMap = new MapSqlParameterSource();
            for (Rate rate : event.getRates()) {
                ratesParamMap.addValue("id", rate.getId());
                ratesParamMap.addValue("name", rate.getName());
                ratesParamMap.addValue("event_id", event.getId());
                ratesParamMap.addValue("rate_type", helper.getRateTypeMap().get(rate.getRateType()));
                ratesParamMap.addValue("start_date", Timestamp.valueOf(rate.getStart()));
                ratesParamMap.addValue("end_date", Timestamp.valueOf(rate.getEnd()));
                ratesParamMap.addValue("cost", rate.getCost());
                if (rate.isNew()) {
                    Number newKey = insertRate.executeAndReturnKey(ratesParamMap);
                    rate.setId(newKey.longValue());
                } else {
                    if (namedParameterJdbcTemplate.update("UPDATE rates SET name = :name, event_id = :event_id, " +
                            "rate_type = :rate_type, start_date = :start_date, end_date = :end_date, cost = :cost " +
                            "WHERE id = :id", ratesParamMap) == 0) {
                        return null;
                    }
                }
            }
        }

        if (!event.getConfirmedVisitors().isEmpty()) {
            Map<String, Object> cvMap = new HashMap<>();
            for (Map.Entry<Visitor, PayDetails> entry : event.getConfirmedVisitors().entrySet()) {
                if(entry.getKey().isNew()) {
                    visitorRepository.save(entry.getKey());
                }
                cvMap.put("visitor_id", entry.getKey().getId());
                cvMap.put("buy_date", Timestamp.valueOf(entry.getValue().getDate()));
                cvMap.put("rate_id", entry.getValue().getRate().getId());
                if (insertConfirmedVisitor.updateByNamedParam(cvMap) == 0) {
                    return null;
                }
            }
            insertConfirmedVisitor.flush();
        }

        if (!event.getTracks().isEmpty()) {
            MapSqlParameterSource trackMap = new MapSqlParameterSource();
            MapSqlParameterSource slotMap = new MapSqlParameterSource();
            Map<String, Object> visitorEventSpeakerMap = new HashMap<>();

            for(Track track : event.getTracks()) {
                trackMap.addValue("id", track.getId());
                trackMap.addValue("name", track.getName());
                trackMap.addValue("event_id", event.getId());
                trackMap.addValue("description", track.getDescription());
                if(track.isNew()) {
                    Number newKey = insertTrack.executeAndReturnKey(trackMap);
                    track.setId(newKey.longValue());
                } else {
                    if(namedParameterJdbcTemplate.update("UPDATE tracks SET name = :name, event_id = :event_id, " +
                            "description = :description WHERE id = :id", trackMap) == 0) {
                        return null;
                    }
                }
                if(!track.getSlotOrder().isEmpty()) {
                    for(Slot slot : track.getSlotOrder()) {
                        slotMap.addValue("id", slot.getId());
                        slotMap.addValue("name", slot.getName());
                        slotMap.addValue("track_id", track.getId());
                        slotMap.addValue("start", Timestamp.valueOf(slot.getStart()));
                        slotMap.addValue("slot_type", helper.getSlotTypeMap().get(slot.getSlotType()));
                        slotMap.addValue("slot_description", slot.getSlotDescription());
                        slotMap.addValue("grade", slot.getGrade());
                        slotMap.addValue("visitors_events_speaker_id", null);
                        if(slot.getApprovedSpeaker() != null) {
                            Visitor speaker = slot.getApprovedSpeaker();
                            if(speaker.isNew()) {
                                visitorRepository.save(speaker);
                            }
                            visitorEventSpeakerMap.put("visitor_id", speaker.getId());
                            visitorEventSpeakerMap.put("event_id", event.getId());
                            visitorEventSpeakerMap.put("price", slot.getPrice());
                            KeyHolder vesKeyHolder = new GeneratedKeyHolder();
                            insertVisitorsEventsSpeakers.updateByNamedParam(visitorEventSpeakerMap, vesKeyHolder);
                            insertVisitorsEventsSpeakers.flush();
                            if (vesKeyHolder.getKey() != null) {
                                slotMap.addValue("visitors_events_speaker_id", vesKeyHolder.getKey().longValue());
                            }
                        }
                        if(slot.isNew()) {
                            Number newKey = insertSlot.executeAndReturnKey(slotMap);
                            slot.setId(newKey.longValue());
                        } else {
                            if(namedParameterJdbcTemplate.update("UPDATE slots SET name = :name, track_id = :track_id, " +
                                    "start = :start, visitors_events_speaker_id = :visitors_events_speaker_id, " +
                                    "slot_description = :slot_description, slot_type = :slot_type, grade = :grade " +
                                    "WHERE id = :id", slotMap) == 0) {
                                return null;
                            }
                        }
                    }
                }
            }
        }

        if (!event.getCommentList().isEmpty()) {
            Map<String, Object> commentsMap = new HashMap<>();
            for (Comment c : event.getCommentList()) {
                Comment insertedComment = commentRepository.save(c);
                commentsMap.put("event_id", event.getId());
                commentsMap.put("comment_id", insertedComment.getId());
                if(insertEventsComments.updateByNamedParam(commentsMap) == 0) {
                    return null;
                }
            }
            insertEventsComments.flush();
        }

        return event;
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
        String sqlAllEventsWithRates = "select e.id, e.name, e.author_id, e.tag_name, e.description, e.logo_url, r.id, " +
                "r.name, rt.type, r.start_date, r.end_date, r.cost from events e LEFT JOIN rates r on e.id = r.event_id LEFT JOIN rate_type rt on r.rate_type = rt.id";
        return null;
    }

    private List<Rate> fillRatesList(long eventId) {
        String sql = "SELECT r.id, r.name, rt.type, r.start_date, r.end_date, r.cost  FROM rates r " +
                "LEFT JOIN rate_type rt ON r.rate_type = rt.id WHERE r.event_id = ? ORDER BY r.start_date, cost";
        List<Rate> listRate = jdbcTemplate.query(sql, (rs, i) -> {
            Rate r = new Rate();
            r.setId(rs.getLong("id"));
            r.setName(rs.getString("name"));
            r.setRateType(RateType.valueOf(rs.getString("type")));
            r.setStart(rs.getTimestamp("start_date").toLocalDateTime());
            r.setEnd(rs.getTimestamp("end_date").toLocalDateTime());
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
                "ves.visitor_id AS speaker_id, ves.price AS speaker_price, s.slot_description, st.type, s.grade FROM tracks t " +
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
                    slot.setSlotDescription(rs.getString("slot_description"));
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
                m.put(v, od);
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


    private final class InsertProbableSpeakers extends BatchSqlUpdate {
        private static final String SQL_INSERT_PROBABLE_SPEAKERS = "INSERT INTO events_probable_speakers " +
                "(visitor_id, event_id, send_date, speech_name, speech_description, wish_price) " +
                "VALUES (:visitor_id, :event_id, :send_date, :speech_name, :speech_description, :wish_price) " +
                "ON CONFLICT (visitor_id, event_id, speech_name) DO UPDATE SET " +
                "send_date = :send_date, speech_description = :speech_description, wish_price = :wish_price ";
        private static final int BATCH_SIZE = 10;

        public InsertProbableSpeakers(DataSource ds) {
            super(ds, SQL_INSERT_PROBABLE_SPEAKERS);
            super.declareParameter(new SqlParameter("visitor_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("event_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("send_date", Types.TIMESTAMP));
            super.declareParameter(new SqlParameter("speech_name", Types.VARCHAR));
            super.declareParameter(new SqlParameter("speech_description", Types.VARCHAR));
            super.declareParameter(new SqlParameter("wish_price", Types.NUMERIC));
            super.setBatchSize(BATCH_SIZE);
        }
    }

    private final class InsertConfirmedVisitors extends BatchSqlUpdate {
        private static final String SQL_INSERT_CONFIRMED_VISITORS = "INSERT INTO events_by_rate_confirmed_visitors " +
                "(visitor_id, buy_date, rate_id) VALUES (:visitor_id, :buy_date, :rate_id) " +
                "ON CONFLICT (visitor_id, buy_date, rate_id) DO UPDATE SET " +
                "visitor_id = :visitor_id, buy_date = :buy_date, rate_id = :rate_id";
        private static final int BATCH_SIZE = 10;

        public InsertConfirmedVisitors(DataSource ds) {
            super(ds, SQL_INSERT_CONFIRMED_VISITORS);
            super.declareParameter(new SqlParameter("visitor_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("buy_date", Types.TIMESTAMP));
            super.declareParameter(new SqlParameter("rate_id", Types.BIGINT));
            super.setBatchSize(BATCH_SIZE);
        }
    }

    private final class InsertVisitorsEventsSpeakers extends BatchSqlUpdate {
        private static final String SQL_INSERT_VES = "INSERT INTO visitors_events_speakers (visitor_id, event_id, price) " +
                "VALUES (:visitor_id, :event_id, :price)";
        private static final int BATCH_SIZE = 5;
        public InsertVisitorsEventsSpeakers(DataSource ds) {
            super(ds, SQL_INSERT_VES);
            super.declareParameter(new SqlParameter("visitor_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("event_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("price", Types.NUMERIC));
            super.setBatchSize(BATCH_SIZE);
        }
    }

    private final class InsertEventsComments extends BatchSqlUpdate {
        private static final String SQL_INSERT_EVENTS_COMMENTS = "INSERT INTO events_comments(event_id, comment_id) " +
                "VALUES (:event_id, :comment_id) ON CONFLICT (event_id, comment_id) DO UPDATE SET " +
                "event_id = :event_id, comment_id = :comment_id";
        private static final int BATCH_SIZE = 10;

        public InsertEventsComments(DataSource ds) {
            super(ds, SQL_INSERT_EVENTS_COMMENTS);
            super.declareParameter(new SqlParameter("event_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("comment_id", Types.BIGINT));
            super.setBatchSize(BATCH_SIZE);
        }
    }
}
