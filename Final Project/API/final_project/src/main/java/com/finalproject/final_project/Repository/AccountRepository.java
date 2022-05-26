package com.finalproject.final_project.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.finalproject.final_project.Models.Account;

@Repository
public class AccountRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Account getAccountById(int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT id, name FROM accounts WHERE id =?",
					new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id });
		} catch (Exception e) {
			return null;
		}
	}
}
