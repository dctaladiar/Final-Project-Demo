package com.finalproject.final_project.Implementation;

import com.finalproject.final_project.Enum.TypeOfAccountEnumData;
import com.finalproject.final_project.Models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Calendar;

public class AccountRepositoryImpl implements Methods {
    @Autowired
    JdbcTemplate jdbcTemplate;
    TypeOfAccountEnumData typeOfAccountEnumData;

    public double getBalanceById(int id, String accountType) {
        try {
            Account existingAccount = jdbcTemplate.queryForObject("SELECT * FROM " + accountType + " WHERE account_id=?",
                    new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id });
            return existingAccount.getBalance();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public double withdraw(int id, Account account, String accountType) {
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

            // check if there are transaction records.
            if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions WHERE account_id =? AND type_of_account = ? "
                    + "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, accountType, startDate, endDate }) != 0) {

                totalAmountWithdrewOnADay = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE account_id =? AND type_of_account = ? "
                        + "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, accountType, startDate, endDate });
            }

            Account existingAccount = jdbcTemplate.queryForObject("SELECT * FROM " + accountType + " WHERE account_id=?",
                    new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id });

            int result = existingAccount.withdraw(account.getBalance(), totalAmountWithdrewOnADay, existingAccount);

            if(result == 2) {
                return 2;
            } else if(result == 1) {
                jdbcTemplate.update("update " + accountType +  " SET balance = ?" + " WHERE account_id = ?",
                        new Object[] { existingAccount.getBalance(), id });
                return account.getBalance();
            } else {
                return 0;
            }

        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public double overrideWithdraw(int id, Account account, String accountType) {
        try {

            Account existingAccount = jdbcTemplate.queryForObject("SELECT * FROM " + accountType + " WHERE account_id=?",
                    new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id });

            int result = existingAccount.overrideWithdraw(account.getBalance(), existingAccount);

            if(result == 1) {
                jdbcTemplate.update("update " + accountType + " SET balance = ?" + " WHERE account_id = ?",
                        new Object[] { existingAccount.getBalance(), id });
                return account.getBalance();
            } else {
                return 0;
            }

        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public double deposit(int id, Account account, String accountType) {
        try {

            Account existingAccount = jdbcTemplate.queryForObject("SELECT * FROM " + accountType + " WHERE account_id=?",
                    new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id });

            double updatedBalance = existingAccount.deposit(account.getBalance(), existingAccount);

            jdbcTemplate.update("update " + accountType + " SET balance = ?" + " WHERE account_id = ?",
                    new Object[] { existingAccount.getBalance(), id });

            return updatedBalance;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int transferFundsToOtherAccount(int id, int id2, Account account, String accountType) {
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

            Account anotherAccount;
            double totalAmountWithdrewOnADay = 0;

            // check if there are transaction records
            if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM accounts WHERE id=?",
                    Long.class, new Object[] { id2 }) != 0) {
                anotherAccount = jdbcTemplate.queryForObject("SELECT * FROM " + accountType + " WHERE account_id=?",
                        new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id2 });
            } else {
                //invalid account number
                return 0;
            }

            if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions WHERE account_id =? AND type_of_account =? "
                    + "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id, accountType, startDate, endDate  }) != 0) {

                totalAmountWithdrewOnADay = jdbcTemplate.queryForObject("SELECT SUM(amount) FROM transactions WHERE account_id =? AND type_of_account =? "
                        + "AND modified_date BETWEEN ? AND ?", Long.class,  new Object[] { id,accountType, startDate, endDate });
            }

            Account existingAccount = jdbcTemplate.queryForObject("SELECT * FROM " + accountType + " WHERE account_id=?",
                    new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id });

            int result = existingAccount.transferToAnotherAccount(anotherAccount, account.getBalance(), totalAmountWithdrewOnADay, existingAccount);

            if(result == 2) {
                // on its limit of maximum withdrawal on a single day
                return 2;
            } else if(result == 1) {
                jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
                        new Object[] { existingAccount.getBalance(), id });

                jdbcTemplate.update("update checking" + " SET balance = ?" + " WHERE account_id = ?",
                        new Object[] { anotherAccount.getBalance(), anotherAccount.getAccountId() });
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

    @Override
    public int overrideTransferOfFundsToOtherAccount(int id, int id2, Account account, String accountType) {
        try {

            Account existingAccount = jdbcTemplate.queryForObject("SELECT * FROM " + accountType +" WHERE account_id=?",
                    new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id });

            Account anotherChecking = jdbcTemplate.queryForObject("SELECT * FROM " + accountType + " WHERE account_id=?",
                    new BeanPropertyRowMapper<Account>(Account.class), new Object[] { id2 });

            int result = existingAccount.overrideTransferOfFundsToOtherAccount(anotherChecking, account.getBalance(), existingAccount);

            if(result == 1) {
                jdbcTemplate.update("update " + accountType + " SET balance = ?" + " WHERE account_id = ?",
                        new Object[] { existingAccount.getBalance(), id });

                jdbcTemplate.update("update " + accountType + " SET balance = ?" + " WHERE account_id = ?",
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
