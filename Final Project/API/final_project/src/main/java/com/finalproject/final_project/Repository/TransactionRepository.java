package com.finalproject.final_project.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.finalproject.final_project.Models.Receipt;
import com.finalproject.final_project.Models.Transaction;

@Repository
public class TransactionRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean insertNewTransaction(Transaction transaction) {
		try {
			jdbcTemplate.update(
					"INSERT INTO transactions (transaction_type, type_of_account, amount, account_id) "
							+ "VALUES(? ,? ,? ,?)",
					new Object[] { transaction.getTransactionType(),
							transaction.getTypeOfAccount(), transaction.getAmount(), transaction.getAccountId() });
			
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public List<Transaction> getAllTransactionsForEachAccountById(int id) {
		try {
			return jdbcTemplate.query("SELECT * FROM transactions WHERE account_id = ? ORDER BY modified_date DESC",
					new BeanPropertyRowMapper<Transaction>(Transaction.class), new Object[] { id });
		} catch (Exception e) {
			return null;
		}

	}
	
	public List<Receipt> getTransactionForReceipt(int id, int id_2) {
		try {
			String typeOfAccount;
			if(id_2 == 1) {
				typeOfAccount = "savings";
			} else if(id_2 == 2) {
				typeOfAccount = "current";
			} else {
				typeOfAccount = "checking";
			}
			
			return jdbcTemplate.query("SELECT t.id, t.modified_date, t.transaction_type, t.type_of_account, t.amount, s.balance FROM transactions t\n"
					+ "JOIN accounts a ON t.account_id = a.id\n"
					+ "JOIN " + typeOfAccount + " s ON s.account_id = a.id\n"
					+ "WHERE a.id = ?\n"
					+ "ORDER BY t.modified_date desc\n"
					+ "LIMIT 1",
					new BeanPropertyRowMapper<Receipt>(Receipt.class), new Object[] {  id });
		} catch (Exception e) {
			return null;
		}
	}
	
}
