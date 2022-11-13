package com.finalproject.final_project.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.finalproject.final_project.Models.User;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public User getAccountById(int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT id, name FROM accounts WHERE id =?",
					new BeanPropertyRowMapper<User>(User.class), new Object[] { id });
		} catch (Exception e) {
			return null;
		}
	}
}
