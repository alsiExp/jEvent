package ru.jevent.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Enums.Sex;
import ru.jevent.model.User;
import ru.jevent.model.Visitor;
import ru.jevent.repository.CommentRepository;
import ru.jevent.repository.VisitorRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcVisitorRepositoryImpl implements VisitorRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertComment;

    private CommentRepository commentRepository;

    @Autowired
    public JdbcVisitorRepositoryImpl(JdbcTemplate jdbcTemplate,
                                     NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                     DataSource dataSource,
                                     CommentRepository commentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertComment = new SimpleJdbcInsert(dataSource)
                .withTableName("visitors")
                .usingGeneratedKeyColumns("id");
        this.commentRepository = commentRepository;
    }

    @Override
    public Visitor save(Visitor visitor) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Visitor get(long id) {
        String sql = "SELECT v.id, v.first_name, v.last_name, s.sex, v.enabled, v.photo_url, v.birthday, " +
                "v.registered_date, v.email, v.phone, v.github_account, v.linkedin_account, v.twitter_account, " +
                "v.employer, v.birthday, v.description, v.cost " +
                "from visitors v LEFT JOIN person_sex s  on v.sex =s.id WHERE v.id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, i) -> {
            Visitor v = new Visitor();
            long visitor_id = rs.getLong("id");
            v.setId(visitor_id);
            v.setFirstName(rs.getString("first_name"));
            v.setLastName(rs.getString("last_name"));
            v.setSex(Sex.valueOf(rs.getString("sex")));
            v.setEnabled(rs.getBoolean("enabled"));
            v.setPhotoURL(rs.getString("photo_url"));
            Timestamp birthday =  rs.getTimestamp("birthday");
            if(birthday != null) {
                v.setBirthDay(rs.getTimestamp("birthday").toLocalDateTime());
            }
            Timestamp registeredDate =  rs.getTimestamp("registered_date");
            if(registeredDate != null) {
                v.setRegistered(registeredDate.toLocalDateTime().toLocalDate());
            }
            v.setEmail(rs.getString("email"));
            v.setPhone(rs.getString("phone"));
            v.setGitHubAccount(rs.getString("github_account"));
            v.setLinkedInAccount(rs.getString("linkedin_account"));
            v.setTwitterAccount(rs.getString("twitter_account"));
            v.setEmployer(rs.getString("employer"));
            v.setBiography(rs.getString("birthday"));
            v.setDescription(rs.getString("description"));
            String stringCost = rs.getString("cost");
            if(!stringCost.isEmpty()) {
                v.setCost(Double.parseDouble(stringCost));
            }
            v.setCommentList(commentRepository.getAllByVisitorId(visitor_id));
            return v;
        }, id);
    }

    @Override
    public Visitor getByEmail(String email) {
        return null;
    }

    @Override
    public List<Visitor> getAll() {
        return null;
    }

}
