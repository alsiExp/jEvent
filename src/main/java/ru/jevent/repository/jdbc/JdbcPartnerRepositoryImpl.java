package ru.jevent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jevent.model.Partner;
import ru.jevent.repository.PartnerRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcPartnerRepositoryImpl implements PartnerRepository {

    private static final BeanPropertyRowMapper<Partner> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Partner.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertPartner;

    private PartnerMapper mapper = new PartnerMapper();

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
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", partner.getId());
        map.addValue("name", partner.getName());
        map.addValue("email", partner.getEmail());
        map.addValue("phone", partner.getPhone());
        map.addValue("description", partner.getDescription());
        map.addValue("logo_url", partner.getLogoURL());
        if(partner.isNew()) {
            Number newKey = insertPartner.executeAndReturnKey(map);
            partner.setId(newKey.longValue());
        } else {
            if(namedParameterJdbcTemplate.update("UPDATE partners SET name = :name, email = :email, phone = :phone, " +
                    "description = :description, logo_url = :logo_url WHERE id = :id", map) == 0) {
                return null;
            }
        }
        return partner;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM partners WHERE id = ?", id) != 0;
    }

    @Override
    public Partner get(long id) {
        String sql = "SELECT id, name, email, phone, description, logo_url FROM partners WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {id}, mapper);
    }

    @Override
    public List<Partner> getAll() {
        String sql = "SELECT id, name, email, phone, description, logo_url FROM partners";
        return jdbcTemplate.query(sql, mapper);
    }

    private final class PartnerMapper implements RowMapper<Partner> {
        @Override
        public Partner mapRow(ResultSet rs, int i) throws SQLException {
            Partner p = new Partner();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            p.setEmail(rs.getString("email"));
            p.setPhone(rs.getString("phone"));
            p.setDescription(rs.getString("description"));
            p.setLogoURL(rs.getString("logo_url"));
            return p;
        }
    }
}
