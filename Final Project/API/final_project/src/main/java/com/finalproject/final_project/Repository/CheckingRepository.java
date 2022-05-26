package com.finalproject.final_project.Repository;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.finalproject.final_project.Models.Checking;

@Repository
public class CheckingRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public double getCheckingBalanceById(int id) {
		try {
			Checking existingChecking = jdbcTemplate.queryForObject("SELECT * FROM checking WHERE account_id=?",
					new BeanPropertyRowMapper<Checking>(Checking.class), new Object[] { id });
			return existingChecking.getBalance();
		} catch (Exception e) {
			return -1;
		}
	}

	public double withdrawInCheckingBalance(int id, Checking checking) {
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
			String typeOfAccount = "Checking";
			
			// check if there are transaction records.
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions WHERE account_id =? AND type_of_account = ? "
					+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate }) != 0) {
				
				totalAmountWithdrewOnADay = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE account_id =? AND type_of_account = ? "
						+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate });
			}		
			
			Checking existingChecking = jdbcTemplate.queryForObject("SELECT * FROM checking WHERE account_id=?",
					new BeanPropertyRowMapper<Checking>(Checking.class), new Object[] { id });
			
			int result = existingChecking.withdraw(checking.getBalance(), totalAmountWithdrewOnADay);
			
			if(result == 2) {
				return 2;
			} else if(result == 1) {
				jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingChecking.getBalance(), id });
				return checking.getBalance();
			} else {
				return 0;
			}	

		} catch (Exception e) {
			return -1;
		}

	}
	
	public double overrideWithdrawInCheckingBalance(int id, Checking checking) {
		try {
			
			Checking existingChecking = jdbcTemplate.queryForObject("SELECT * FROM checking WHERE account_id=?",
					new BeanPropertyRowMapper<Checking>(Checking.class), new Object[] { id });
			
			int result = existingChecking.overrideWithdraw(checking.getBalance());
			
			if(result == 1) {
				jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingChecking.getBalance(), id });
				return checking.getBalance();
			} else {
				return 0;
			}	

		} catch (Exception e) {
			return -1;
		}

	}
	
	public double depositInCheckingBalance(int id, Checking checking) {
		try {
			
			Checking existingChecking = jdbcTemplate.queryForObject("SELECT * FROM checking WHERE account_id=?",
					new BeanPropertyRowMapper<Checking>(Checking.class), new Object[] { id });
			
				double updatedBalance = existingChecking.deposit(checking.getBalance());
				
				jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingChecking.getBalance(), id });
				
				return updatedBalance;
		} catch (Exception e) {
			return -1;
		}

	}
	
	public int transferFundsToOtherAccount(int id, int id_2, Checking checking) {
		
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
			
			Checking anotherChecking;
			double totalAmountWithdrewOnADay = 0;
			String typeOfAccount = "Checking";
			
			// check if there are transaction records
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM accounts WHERE id=?",
					Long.class, new Object[] { id_2 }) != 0) {
				anotherChecking = jdbcTemplate.queryForObject("SELECT * FROM checking WHERE account_id=?",
						new BeanPropertyRowMapper<Checking>(Checking.class), new Object[] { id_2 });
			} else {
				//invalid account number
				return 0;
			}
			
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions WHERE account_id =? AND type_of_account =? "
					+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate  }) != 0) {
				
				totalAmountWithdrewOnADay = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE account_id =? AND type_of_account =? " 
						+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id,typeOfAccount, startDate, endDate });
			}		
			
			Checking existingChecking = jdbcTemplate.queryForObject("SELECT * FROM checking WHERE account_id=?",
					new BeanPropertyRowMapper<Checking>(Checking.class), new Object[] { id });
			
			int result = existingChecking.transferToAnotherAccount(anotherChecking, checking.getBalance(), totalAmountWithdrewOnADay);
			
			if(result == 2) {
				// on its limit of maximum withdrawal on a single day
				return 2;
			} else if(result == 1) {
				jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingChecking.getBalance(), id });
				
				jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { anotherChecking.getBalance(), anotherChecking.getAccountId() });
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
	
	public int overrideTransferOfFundsToOtherAccount(int id, int id_2, Checking checking) {
		try {
			
			Checking existingChecking = jdbcTemplate.queryForObject("SELECT * FROM checking WHERE account_id=?",
					new BeanPropertyRowMapper<Checking>(Checking.class), new Object[] { id });
			
			Checking anotherChecking = jdbcTemplate.queryForObject("SELECT * FROM checking WHERE account_id=?",
					new BeanPropertyRowMapper<Checking>(Checking.class), new Object[] { id_2 });
			
			int result = existingChecking.overrideTransferOfFundsToOtherAccount(anotherChecking, checking.getBalance());
			
			if(result == 1) {
				jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingChecking.getBalance(), id });
				
				jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { anotherChecking.getBalance(), anotherChecking.getAccountId() });
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
