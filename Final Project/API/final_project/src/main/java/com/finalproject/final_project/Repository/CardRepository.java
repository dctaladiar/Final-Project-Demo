package com.finalproject.final_project.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.finalproject.final_project.Models.Card;

@Repository
public class CardRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int checkCardById(int id) {
		try {
			Card existingCard = jdbcTemplate.queryForObject("SELECT * FROM cards WHERE id = ?", new BeanPropertyRowMapper<Card>(Card.class), new Object[] {id});
			if(existingCard != null) {
				return existingCard.getAccountId();
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	public boolean checkIfPinCodeIsCorrect(int id, Card card) {
		try {
			Card existingCard = jdbcTemplate.queryForObject("SELECT * FROM cards WHERE id = ?", new BeanPropertyRowMapper<Card>(Card.class), new Object[] {id});
			if(existingCard.getPinCode().equals(card.getPinCode())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return null != null;
		}
		
	}
	
	public boolean checkIfPinCodeIsCorrectForChangePinCode(int id, Card card) {
		try {
			Card existingCard = jdbcTemplate.queryForObject("SELECT * FROM cards WHERE account_id = ?", new BeanPropertyRowMapper<Card>(Card.class), new Object[] {id});
			if(existingCard.getPinCode().equals(card.getPinCode())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return null != null;
		}
		
	}
	
	
	
	public String changePinCode(int id, Card card) {
		try {
		int rowsAffected = jdbcTemplate.update("update cards" + " SET pin_code = ?" + " WHERE account_id = ?",
					new Object[] { card.getPinCode(), id });
			if(rowsAffected == 1) {
				return "Success";
			} else {
				return "Failed";
			}
		} catch (Exception e) {
			return "Failed to execute query";
		}
	}

	
	
}
