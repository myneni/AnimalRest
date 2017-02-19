package com.cognizant.animalsearchapp.rest.animaldao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;
import com.google.common.base.Joiner;

@Component
public class AnimalDAOImpl implements AnimalDao {
	private final Logger logger = LoggerFactory.getLogger(AnimalDAOImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public AnimalDAOImpl(DataSource dataSource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Animals findRegionByName(List<String> names) {
		String sql = "SELECT * FROM Animals where name in (:names)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("names", names);
		logger.debug("findRegionByName SQL: {}", sql);
		List<Animal> animalList = namedParameterJdbcTemplate.query(sql, params, new RowMapper<Animal>() {

			@Override
			public Animal mapRow(ResultSet rs, int rowNum) throws SQLException {
				Animal animal = new Animal();

				animal.setId(rs.getInt("id"));
				animal.setName(rs.getString("name"));
				animal.setRegion(rs.getString("region"));
				animal.setAccessTimeStamp(rs.getTimestamp("accesstime"));
				return animal;
			}

		});
		Animals animals = new Animals();
		animals.addAnimals(animalList);
		return animals;

	}

	@Override
	public int logAccessRequest(List<String> names) {
		String animalNames = Joiner.on(",").join(names);
		logger.debug("Request AnimalNames : {}", animalNames);
		String sql = "INSERT INTO Animal_Access_log (names) VALUES(:name)";
		logger.debug("Insert Access Timestamp : {}", sql);
		int rowsUpdated = namedParameterJdbcTemplate.update(sql,
				new MapSqlParameterSource().addValue("name", sort(animalNames)));

		return rowsUpdated;
	}

	private String sort(String str) {
		char[] c = str.toCharArray();
		Arrays.sort(c);
		return new String(c);
	}

	@Override
	public List<AnimalAccessLog> getAccessLog(List<String> names) {
		String animalNames = Joiner.on(",").join(names);
		logger.debug("getAccessLog - Animal names : {}", animalNames);
		String sql = "SELECT * FROM Animal_Access_log where names like (:name) and TIMESTAMPDIFF(HOUR, accessTimestamp, NOW()) < 24";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", sort(animalNames));
		logger.debug("getAccessLog SQL: {}", sql);
		List<AnimalAccessLog> accessLogs = namedParameterJdbcTemplate.query(sql, params,
				new RowMapper<AnimalAccessLog>() {

					@Override
					public AnimalAccessLog mapRow(ResultSet rs, int rowNum) throws SQLException {
						AnimalAccessLog animalAccessLog = new AnimalAccessLog();

						animalAccessLog.setRequestId(rs.getInt("RequestId"));
						animalAccessLog.setName(rs.getString("names"));
						animalAccessLog.setAccessTime(rs.getTimestamp("accessTimestamp"));
						return animalAccessLog;
					}

				});
		logger.debug("Access Logs : {}", accessLogs);
		return accessLogs;
	}

}
