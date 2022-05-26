package com.finalproject.final_project.Repository;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.finalproject.final_project.Models.Current;

@Repository
public class CurrentRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public double getCurrentBalanceById(int id) {
		try {
			Current existingCurrent = jdbcTemplate.queryForObject("SELECT * FROM current WHERE account_id=?",
					new BeanPropertyRowMapper<Current>(Current.class), new Object[] { id });
			return existingCurrent.getBalance();
		} catch (Exception e) {
			return -1;
		}
	}

	public double withdrawInCurrentBalance(int id, Current current) {
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
			String typeOfAccount = "Current";
			
			// check if there are transaction records.
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions WHERE account_id =? AND type_of_account = ? "
					+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate }) != 0) {
				
				totalAmountWithdrewOnADay = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE account_id =? AND type_of_account = ? "
						+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate });
			}		
			
			Current existingCurrent = jdbcTemplate.queryForObject("SELECT * FROM current WHERE account_id=?",
					new BeanPropertyRowMapper<Current>(Current.class), new Object[] { id });
			
			int result = existingCurrent.withdraw(current.getBalance(), totalAmountWithdrewOnADay);
			
			if(result == 2) {
				return 2;
			} else if(result == 1) {
				jdbcTemplate.update("update current" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingCurrent.getBalance(), id });
				return current.getBalance();
			} else {
				return 0;
			}	

		} catch (Exception e) {
			return -1;
		}

	}
	
	public double overrideWithdrawInCurrentBalance(int id, Current current) {
		try {
			
			Current existingCurrent = jdbcTemplate.queryForObject("SELECT * FROM current WHERE account_id=?",
					new BeanPropertyRowMapper<Current>(Current.class), new Object[] { id });
			
			int result = existingCurrent.overrideWithdraw(current.getBalance());
			
			if(result == 1) {
				jdbcTemplate.update("update current" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingCurrent.getBalance(), id });
				return current.getBalance();
			} else {
				return 0;
			}	

		} catch (Exception e) {
			return -1;
		}

	}
	
	public double depositInCurrentBalance(int id, Current current) {
		try {
			
			Current existingCurrent = jdbcTemplate.queryForObject("SELECT * FROM current WHERE account_id=?",
					new BeanPropertyRowMapper<Current>(Current.class), new Object[] { id });
			
				double updatedBalance = existingCurrent.deposit(current.getBalance());
				
				jdbcTemplate.update("update current" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingCurrent.getBalance(), id });
				
				return updatedBalance;
		} catch (Exception e) {
			return -1;
		}

	}
	
	public int transferFundsToOtherAccount(int id, int id_2, Current current) {
		
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
			
			Current anotherCurrent;
			double totalAmountWithdrewOnADay = 0;
			String typeOfAccount = "Current";
			
			// check if there are transaction records
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM accounts WHERE id=?",
					Long.class, new Object[] { id_2 }) != 0) {
				anotherCurrent = jdbcTemplate.queryForObject("SELECT * FROM current WHERE account_id=?",
						new BeanPropertyRowMapper<Current>(Current.class), new Object[] { id_2 });
			} else {
				//invalid account number
				return 0;
			}
			
			if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions WHERE account_id =? AND type_of_account =? "
					+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, typeOfAccount, startDate, endDate  }) != 0) {
				
				totalAmountWithdrewOnADay = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE account_id =? AND type_of_account =? " 
						+ "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id,typeOfAccount, startDate, endDate });
			}		
			
			Current existingCurrent = jdbcTemplate.queryForObject("SELECT * FROM current WHERE account_id=?",
					new BeanPropertyRowMapper<Current>(Current.class), new Object[] { id });
			
			int result = existingCurrent.transferToAnotherAccount(anotherCurrent, current.getBalance(), totalAmountWithdrewOnADay);
			
			if(result == 2) {
				// on its limit of maximum withdrawal on a single day
				return 2;
			} else if(result == 1) {
				jdbcTemplate.update("update current" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingCurrent.getBalance(), id });
				
				jdbcTemplate.update("update current" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { anotherCurrent.getBalance(), anotherCurrent.getAccountId() });
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
	
	public int overrideTransferOfFundsToOtherAccount(int id, int id_2, Current current) {
		try {
			
			Current existingCurrent = jdbcTemplate.queryForObject("SELECT * FROM current WHERE account_id=?",
					new BeanPropertyRowMapper<Current>(Current.class), new Object[] { id });
			
			Current anotherCurrent = jdbcTemplate.queryForObject("SELECT * FROM current WHERE account_id=?",
					new BeanPropertyRowMapper<Current>(Current.class), new Object[] { id_2 });
			
			int result = existingCurrent.overrideTransferOfFundsToOtherAccount(anotherCurrent, current.getBalance());
			
			if(result == 1) {
				jdbcTemplate.update("update current" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { existingCurrent.getBalance(), id });
				
				jdbcTemplate.update("update current" + " SET balance = ?" + " WHERE account_id = ?",
						new Object[] { anotherCurrent.getBalance(), anotherCurrent.getAccountId() });
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
