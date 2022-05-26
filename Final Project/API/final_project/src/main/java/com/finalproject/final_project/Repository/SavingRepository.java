package com.finalproject.final_project.Repository;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.finalproject.final_project.Models.Saving;

@Repository
public class SavingRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public double getSavingsBalanceById(int id) {
		try {
			Saving existingSaving = jdbcTemplate.queryForObject("SELECT * FROM savings WHERE account_id=?",
					new BeanPropertyRowMapper<Saving>(Saving.class), new Object[] { id });
			return existingSaving.getBalance();
		} catch (Exception e) {
			return -1;
		}
	}

	public double withdrawInSavingBalance(int id, Saving saving) {
		try {
			Calendar startDate = Calendar.getInstance();
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			
			Calendar endDate = Calendar.getInstance();
			endDate.set(Calendar.HOUR_OF_DAY, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			
			double totalAmountWithdrewOnADay = 0;
			String typeOfAccount = "Withdraw";
			
			// check if there are transaction records.
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions WHERE account_id =? AND type_of_account = ? "
					+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate }) != 0) {
				
				totalAmountWithdrewOnADay = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE account_id =? AND type_of_account = ? "
						+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate });
			}		
			
			Saving existingSavings = jdbcTemplate.queryForObject("SELECT * FROM savings WHERE account_id=?",
					new BeanPropertyRowMapper<Saving>(Saving.class), new Object[] { id });
			
			int result = existingSavings.withdraw(saving.getBalance(), totalAmountWithdrewOnADay);
			
			if(result == 2) {
				return 2;
			} else if(result == 1) {
				jdbcTemplate.update("update savings" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingSavings.getBalance(), id });
				return saving.getBalance();
			} else {
				return 0;
			}	

		} catch (Exception e) {
			return -1;
		}

	}
	
	public double overrideWithdrawInSavingBalance(int id, Saving saving) {
		try {
			
			Saving existingSavings = jdbcTemplate.queryForObject("SELECT * FROM savings WHERE account_id=?",
					new BeanPropertyRowMapper<Saving>(Saving.class), new Object[] { id });
			
			int result = existingSavings.overrideWithdraw(saving.getBalance());
			
			if(result == 1) {
				jdbcTemplate.update("update savings" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingSavings.getBalance(), id });
				return saving.getBalance();
			} else {
				return 0;
			}	

		} catch (Exception e) {
			return -1;
		}

	}
	
	public double depositInSavingBalance(int id, Saving saving) {
		try {
			
			Saving existingSavings = jdbcTemplate.queryForObject("SELECT * FROM savings WHERE account_id=?",
					new BeanPropertyRowMapper<Saving>(Saving.class), new Object[] { id });
			
				double updatedBalance = existingSavings.deposit(saving.getBalance());
				
				jdbcTemplate.update("update savings" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingSavings.getBalance(), id });
				
				return updatedBalance;
		} catch (Exception e) {
			return -1;
		}

	}
	
	public int transferFundsToOtherAccount(int id, int id_2, Saving saving) {
		
		try {
			Calendar startDate = Calendar.getInstance();
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			
			Calendar endDate = Calendar.getInstance();
			endDate.set(Calendar.HOUR_OF_DAY, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			
			Saving anotherSavings;
			double totalAmountWithdrewOnADay = 0;
			String typeOfAccount = "Withdraw";
			
			// check if there are transaction records
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM accounts WHERE id=?",
					Long.class, new Object[] { id_2 }) != 0) {
				anotherSavings = jdbcTemplate.queryForObject("SELECT * FROM savings WHERE account_id=?",
						new BeanPropertyRowMapper<Saving>(Saving.class), new Object[] { id_2 });
			} else {
				//invalid account number
				return 0;
			}
			
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions WHERE account_id =? AND type_of_account =? "
					+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate  }) != 0) {
				
				totalAmountWithdrewOnADay = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE account_id =? AND type_of_account =? " 
						+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id,typeOfAccount, startDate, endDate });
			}		
			
			Saving existingSavings = jdbcTemplate.queryForObject("SELECT * FROM savings WHERE account_id=?",
					new BeanPropertyRowMapper<Saving>(Saving.class), new Object[] { id });
			
			int result = existingSavings.transferToAnotherAccount(anotherSavings, saving.getBalance(), totalAmountWithdrewOnADay);
			
			if(result == 2) {
				// on its limit of maximum withdrawal on a single day
				return 2;
			} else if(result == 1) {
				jdbcTemplate.update("update savings" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingSavings.getBalance(), id });
				
				jdbcTemplate.update("update savings" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { anotherSavings.getBalance(), anotherSavings.getAccountId() });
				// success
				return 1;
			} else {
				// balance will be less than 2000. confirm to the client
				return 3;
			}
			
		} catch (Exception e) {
			return -1;
		}
	
	}
	
	public int overrideTransferOfFundsToOtherAccount(int id, int id_2, Saving saving) {
		try {
			
			Saving existingSavings = jdbcTemplate.queryForObject("SELECT * FROM savings WHERE account_id=?",
					new BeanPropertyRowMapper<Saving>(Saving.class), new Object[] { id });
			
			Saving anotherSavings = jdbcTemplate.queryForObject("SELECT * FROM savings WHERE account_id=?",
					new BeanPropertyRowMapper<Saving>(Saving.class), new Object[] { id_2 });
			
			int result = existingSavings.overrideTransferOfFundsToOtherAccount(anotherSavings, saving.getBalance());
			
			if(result == 1) {
				jdbcTemplate.update("update savings" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingSavings.getBalance(), id });
				
				jdbcTemplate.update("update savings" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { anotherSavings.getBalance(), anotherSavings.getAccountId() });
				// override success
				return 1;
			} else {
				// insufficient balance
				return 0;
			}	

		} catch (Exception e) {
			return -1;
		}

	}
	
}
