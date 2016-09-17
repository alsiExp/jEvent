package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Partner;
import ru.jevent.repository.PartnerRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcPartnerRepositoryImpl implements PartnerRepository {

    private static final BeanPropertyRowMapper<Partner> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Partner.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertPartner;

    @Autowired
    public JdbcPartnerRepositoryImpl(JdbcTemplate jdbcTemplate,
                                     NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                     DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertPartner = new SimpleJdbcInsert(dataSource)
                .withTableName("partners")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Partner save(Partner partner) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Partner get(long id) {
        String sql = "SELECT id, name, email, phone, description, logo_url FROM partners WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {id}, (rs, i) -> {
           Partner p = new Partner();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            p.setEmail(rs.getString("email"));
            p.setPhone(rs.getString("phone"));
            p.setDescription(rs.getString("description"));
            p.setLogoURL(rs.getString("logo_url"));
            return p;
        });
    }

    @Override
    public List<Partner> getAll() {
        String sql = "SELECT id, name, email, phone, description, logo_url FROM partners";
        return null;
    }
}
