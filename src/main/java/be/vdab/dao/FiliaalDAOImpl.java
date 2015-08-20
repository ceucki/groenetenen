package be.vdab.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.vdab.entities.Filiaal;
import be.vdab.valueobjects.Adres;
import be.vdab.valueobjects.PostcodeReeks;

@Repository
public class FiliaalDAOImpl implements FiliaalDAO {

	private final Map<Long, Filiaal> filialen = new ConcurrentHashMap<>();
	private final FiliaalRowMapper rowMapper = new FiliaalRowMapper();
	private static final String BEGIN_SQL = "select id, naam, hoofdFiliaal, straat, huisNr, postcode, gemeente,"
			+ "inGebruikName, waardeGebouw from filialen ";
	private static final String SQL_FIND_ALL = BEGIN_SQL + "order by naam";

	@Override
	public void create(Filiaal filiaal) {
		Map<String, Object> kolomWaarden = new HashMap<>();

		kolomWaarden.put("naam", filiaal.getNaam());
		kolomWaarden.put("hoofdFiliaal", filiaal.isHoofdFiliaal());
		kolomWaarden.put("straat", filiaal.getAdres().getStraat());
		kolomWaarden.put("huisNr", filiaal.getAdres().getHuisNr());
		kolomWaarden.put("postcode", filiaal.getAdres().getPostcode());
		kolomWaarden.put("gemeente", filiaal.getAdres().getGemeente());
		kolomWaarden.put("inGebruikName", filiaal.getInGebruikName());
		kolomWaarden.put("waardeGebouw", filiaal.getWaardeGebouw());
		Number id = simpleJdbcInsert.executeAndReturnKey(kolomWaarden);
		filiaal.setId(id.longValue());

	}

	private static final String SQL_READ = BEGIN_SQL + " where id = :id";

	@Override
	public Filiaal read(long id) {
		Map<String, Long> parameters = Collections.singletonMap("id", id);
		try {
			return namedParameterJdbcTemplate.queryForObject(SQL_READ, parameters, rowMapper);
		} catch (IncorrectResultSizeDataAccessException ex) {
			return null; // record niet gevonden
		}
	}

	private static final String SQL_UPDATE = "update filialen set naam=:naam,hoofdFiliaal=:hoofdFiliaal, straat=:straat,"
			+ "huisNr=:huisNr, postcode=:postcode, gemeente=:gemeente, "
			+ "inGebruikName=:inGebruikName, waardeGebouw=:waardeGebouw where id = :id";

	@Override
	public void update(Filiaal filiaal) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", filiaal.getId());
		parameters.put("naam", filiaal.getNaam());
		parameters.put("hoofdFiliaal", filiaal.isHoofdFiliaal());
		parameters.put("straat", filiaal.getAdres().getStraat());
		parameters.put("huisNr", filiaal.getAdres().getHuisNr());
		parameters.put("postcode", filiaal.getAdres().getPostcode());
		parameters.put("gemeente", filiaal.getAdres().getGemeente());
		parameters.put("inGebruikName", filiaal.getInGebruikName());
		parameters.put("waardeGebouw", filiaal.getWaardeGebouw());
		namedParameterJdbcTemplate.update(SQL_UPDATE, parameters);

	}

	private static final String SQL_DELETE = "delete from filialen where id = ?";

	@Override
	public void delete(long id) {
		jdbcTemplate.update(SQL_DELETE, id);
	}

	@Override
	public List<Filiaal> findAll() {

		return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
	}

	private static final String SQL_FIND_AANTAL_FILIALEN = "select count(*) from filialen";

	@Override
	public long findAantalFilialen() {
		return jdbcTemplate.queryForObject(SQL_FIND_AANTAL_FILIALEN, Long.class);
	}

	private static final String SQL_FIND_AANTAL_WERKNEMERS = "select count(*) from werknemers where filiaalId = ?";

	@Override
	public long findAantalWerknemers(long id) {
		return jdbcTemplate.queryForObject(SQL_FIND_AANTAL_WERKNEMERS, Long.class, id);
	}

	private static final String SQL_FIND_BY_POSTCODE = BEGIN_SQL + "where postcode between ? and ? order by naam";

	@Override
	public List<Filiaal> findByPostcodeReeks(PostcodeReeks reeks) {
		return jdbcTemplate.query(SQL_FIND_BY_POSTCODE, rowMapper, reeks.getVanpostcode(), reeks.getTotpostcode());
	}

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	FiliaalDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("filialen");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
	}

	private static class FiliaalRowMapper implements RowMapper<Filiaal> {
		@Override
		public Filiaal mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Filiaal(resultSet.getLong("id"), resultSet.getString("naam"),
					resultSet.getBoolean("hoofdFiliaal"), resultSet.getBigDecimal("waardeGebouw"),
					resultSet.getDate("inGebruikName"),
					new Adres(resultSet.getString("straat"), resultSet.getString("huisNr"),
							resultSet.getInt("postcode"), resultSet.getString("gemeente")));
		}
	}

}
