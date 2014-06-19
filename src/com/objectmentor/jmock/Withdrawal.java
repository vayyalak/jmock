package com.objectmentor.jmock;

public class Withdrawal {
	
	private BankAccount account;
    private int amount;
    public boolean executed;
 
    public void setAmount(int amount) {
        this.amount = amount;
    }
 
    public void setAccount(BankAccount account) {
        this.account = account;
    }
 
    public void execute() {
        if (account.getBalance() < amount) {
            throw new InsufficientFunds();
        }
        account.deduct(amount);
        executed = true;
    }
 
    public boolean isExecuted() {
        return executed;
    }

}
