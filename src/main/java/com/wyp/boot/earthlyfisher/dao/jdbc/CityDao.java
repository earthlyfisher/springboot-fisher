package com.wyp.boot.earthlyfisher.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wyp.boot.earthlyfisher.pojo.City;

@Repository
public class CityDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveCity(City city){
		String sql="INSERT INTO T_CITY(name,state) VALUES (?,?)";
		jdbcTemplate.update(sql, city.getName(),city.getState());
	}
}
