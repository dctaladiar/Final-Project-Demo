package com.finalproject.final_project.Implementation;

import com.finalproject.final_project.Models.Account;

public class BusinessLogicImpl implements BusinessLogic {
    @Override
    public int overrideWithdraw(double amount, Account account) {
        if(amount <= account.getBalance() &&  (account.getBalance() - amount >= 0)) {
            account.setBalance(account.getBalance() - amount);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int withdraw(double amount, double totalAmountWithdrewOnADay, Account account) {
        if((totalAmountWithdrewOnADay + amount) > 50000 ) {
            return 2;
        } else if(amount <= account.getBalance() && (account.getBalance() - amount >= 2000.00)) {
            account.setBalance(account.getBalance() - amount);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public double deposit(double amount, Account account) {
        if(amount >= 300) {
            if(amount % 300 == 0) {
                account.setBalance(account.getBalance() + amount);
                return 0;
            } else {
                double remainder = amount % 300;
                double amountToBeProcessed = amount - remainder;
                account.setBalance(account.getBalance() + amountToBeProcessed);
                return remainder;
            }
        } else {
            return amount;
        }
    }

    @Override
    public int transferToAnotherAccount(Account anotherAccount, double amount, double totalAmountWithdrewOnADay, Account account) {
        if((totalAmountWithdrewOnADay + amount) > 50000 ) {
            return 2;
        } else if(account.getBalance() > amount && (account.getBalance() - amount >= 5000.00)) {
            account.setBalance(account.getBalance() - amount);
            anotherAccount.setBalance(anotherAccount.getBalance() + amount);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int overrideTransferOfFundsToOtherAccount(Account anotherAccount, double amount, Account account) {
        if(amount <= account.getBalance() &&  (account.getBalance() - amount >= 0)) {
            account.setBalance(account.getBalance() - amount);
            anotherAccount.setBalance(anotherAccount.getBalance() + amount);
            return 1;
        } else {
            return 0;
        }
    }
}
