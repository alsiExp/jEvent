package ru.jevent.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Comment;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.Visitor;
import ru.jevent.repository.CommentRepository;
import ru.jevent.repository.VisitorRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcVisitorRepositoryImpl implements VisitorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertVisitor;

    private JdbcHelper helper;
    private VisitorMapper mapper = new VisitorMapper();
    private InsertVisitorComments insertVisitorComments;
    private CommentRepository commentRepository;


    @Autowired
    public JdbcVisitorRepositoryImpl(JdbcTemplate jdbcTemplate,
                                     NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                     DataSource dataSource,
                                     CommentRepository commentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertVisitor = new SimpleJdbcInsert(dataSource)
                .withTableName("visitors")
                .usingGeneratedKeyColumns("id");
        this.commentRepository = commentRepository;
        this.helper = new JdbcHelper(dataSource);
        this.insertVisitorComments = new InsertVisitorComments(dataSource);
    }

    @Override
    public Visitor save(Visitor visitor) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", visitor.getId());
        map.addValue("first_name", visitor.getFirstName());
        map.addValue("last_name", visitor.getLastName());
        map.addValue("sex", helper.getSexMap().get(visitor.getSex()));
        map.addValue("enabled", visitor.isEnabled());
        map.addValue("photo_url", visitor.getPhotoURL());
        map.addValue("birthday", Timestamp.valueOf(visitor.getBirthDay()));
        map.addValue("registered_date", Timestamp.valueOf(visitor.getRegistered().atTime(0, 0)));
        map.addValue("email", visitor.getEmail());
        map.addValue("phone", visitor.getPhone());
        map.addValue("github_account", visitor.getGitHubAccount());
        map.addValue("linkedin_account", visitor.getLinkedInAccount());
        map.addValue("twitter_account", visitor.getTwitterAccount());
        map.addValue("employer", visitor.getEmployer());
        map.addValue("biography", visitor.getBiography());
        map.addValue("description", visitor.getDescription());
        map.addValue("cost", visitor.getCost());
        if(visitor.isNew()) {
            Number newKey = insertVisitor.executeAndReturnKey(map);
            visitor.setId(newKey.longValue());
        } else {
            if(namedParameterJdbcTemplate.update("UPDATE visitors SET first_name = :first_name, last_name = :last_name, " +
                    "sex = :sex, enabled = :enabled, photo_url = :photo_url, birthday = :birthday, " +
                    "registered_date = :registered_date, email = :email, phone = :phone, " +
                    "github_account = :github_account, linkedin_account = :linkedin_account, twitter_account = :twitter_account, " +
                    "employer = :employer, biography = :biography, description = :description, cost = :cost WHERE id= :id", map) == 0) {
                return null;
            }
        }
        if(!visitor.getCommentList().isEmpty()) {
            Map<String, Object> commentsMap = new HashMap<>();
            for (Comment c : visitor.getCommentList()) {
                Comment insertedComment = commentRepository.save(c);
                commentsMap.put("visitor_id", visitor.getId());
                commentsMap.put("comment_id", insertedComment.getId());
                if (insertVisitorComments.updateByNamedParam(commentsMap) == 0) {
                    return null;
                }
            }
            insertVisitorComments.flush();
        }
        return visitor;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM visitors WHERE id = ?", id) != 0;
    }

    @Override
    public Visitor get(long id) {
        String sql = "SELECT v.id, v.first_name, v.last_name, s.sex, v.enabled, v.photo_url, v.birthday, " +
                "v.registered_date, v.email, v.phone, v.github_account, v.linkedin_account, v.twitter_account, " +
                "v.employer, v.biography, v.description, v.cost " +
                "from visitors v LEFT JOIN person_sex s  on v.sex =s.id WHERE v.id = ?";
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public Visitor getByEmail(String email) {
        String sql = "SELECT v.id, v.first_name, v.last_name, s.sex, v.enabled, v.photo_url, v.birthday, " +
                "v.registered_date, v.email, v.phone, v.github_account, v.linkedin_account, v.twitter_account, " +
                "v.employer, v.biography, v.description, v.cost " +
                "from visitors v LEFT JOIN person_sex s  on v.sex =s.id WHERE v.email = ?";
        return jdbcTemplate.queryForObject(sql, mapper, email);
    }

    @Override
    public List<Visitor> getAll() {
        String sql = "SELECT v.id, v.first_name, v.last_name, s.sex, v.enabled, v.photo_url, v.birthday, " +
                "v.registered_date, v.email, v.phone, v.github_account, v.linkedin_account, v.twitter_account, " +
                "v.employer, v.biography, v.description, v.cost " +
                "from visitors v LEFT JOIN person_sex s  on v.sex =s.id";
        return jdbcTemplate.query(sql, mapper);
    }


    private final class VisitorMapper implements RowMapper<Visitor> {
        @Override
        public Visitor mapRow(ResultSet rs, int i) throws SQLException {
            Visitor visitor = new Visitor();
            long visitor_id = rs.getLong("id");
            visitor.setId(visitor_id);
            visitor.setFirstName(rs.getString("first_name"));
            visitor.setLastName(rs.getString("last_name"));
            visitor.setSex(Sex.valueOf(rs.getString("sex")));
            visitor.setEnabled(rs.getBoolean("enabled"));
            visitor.setPhotoURL(rs.getString("photo_url"));
            Timestamp birthday =  rs.getTimestamp("birthday");
            if(birthday != null) {
                visitor.setBirthDay(rs.getTimestamp("birthday").toLocalDateTime());
            }
            Timestamp registeredDate =  rs.getTimestamp("registered_date");
            if(registeredDate != null) {
                visitor.setRegistered(registeredDate.toLocalDateTime().toLocalDate());
            }
            visitor.setEmail(rs.getString("email"));
            visitor.setPhone(rs.getString("phone"));
            visitor.setGitHubAccount(rs.getString("github_account"));
            visitor.setLinkedInAccount(rs.getString("linkedin_account"));
            visitor.setTwitterAccount(rs.getString("twitter_account"));
            visitor.setEmployer(rs.getString("employer"));
            visitor.setBiography(rs.getString("biography"));
            visitor.setDescription(rs.getString("description"));
            String stringCost = rs.getString("cost");
            if(!stringCost.isEmpty()) {
                visitor.setCost(Double.parseDouble(stringCost));
            }
            visitor.setCommentList(commentRepository.getAllByVisitorId(visitor_id));
            return visitor;
        }
    }

    private final class InsertVisitorComments extends BatchSqlUpdate {
        private static final String SQL_INSERT_VISITORS_COMMENTS = "INSERT INTO visitors_comments (visitor_id, comment_id) values " +
                "(:visitor_id, :comment_id) ON CONFLICT (visitor_id, comment_id) DO NOTHING";
        private static final int BATCH_SIZE = 10;

        public InsertVisitorComments(DataSource ds) {
            super(ds, SQL_INSERT_VISITORS_COMMENTS);
            super.declareParameter(new SqlParameter("visitor_id", Types.BIGINT));
            super.declareParameter(new SqlParameter("comment_id", Types.BIGINT));
            super.setBatchSize(BATCH_SIZE);
        }
    }



}
