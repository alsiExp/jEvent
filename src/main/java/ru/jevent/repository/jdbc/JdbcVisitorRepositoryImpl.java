package ru.jevent.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Comment;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.User;
import ru.jevent.model.Visitor;
import ru.jevent.repository.CommentRepository;
import ru.jevent.repository.VisitorRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcVisitorRepositoryImpl implements VisitorRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertVisitor;

    private JdbcHelper helper;

    private VisitorMapper mapper = new VisitorMapper();

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
        map.addValue("registered_date", visitor.getRegistered());
        map.addValue("employer", visitor.getEmployer());
        map.addValue("biography", visitor.getBiography());
        map.addValue("description", visitor.getDescription());
        map.addValue("cost", visitor.getCost());
        if(visitor.isNew()) {
            Number newKey = insertVisitor.executeAndReturnKey(map);
            visitor.setId(newKey.longValue());
        } else {
            if(namedParameterJdbcTemplate.update("UPDATE visitors SET first_name = :first_name, last_name = :last_name, " +
                    "sex = :sex, enabled = :enabled, photo_url = :photo_url, birthday = :birthday, employer = :employer, " +
                    "biography = :biography, description = :description, cost = :cost", map) == 0) {
                return null;
            }
        }
        MapSqlParameterSource commentsMap = new MapSqlParameterSource();
        for(Comment c : visitor.getCommentList()) {
            Comment insertedComment = commentRepository.save(c, c.getAuthor().getId());
            commentsMap.addValue("visitor_id", visitor.getId());
            commentsMap.addValue("comment_id", insertedComment.getId());
            if(namedParameterJdbcTemplate.update("UPDATE visitors_comments SET visitor_id = :visitor_id, comment_id = :comment_id", commentsMap) == 0) {
                return null;
            }
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



}
