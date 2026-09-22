public abstract class Account {

    private String accId;
    private double balance;
    private boolean lockedStatus;
    private int failedPin;

    public Account(String accId, double balance) {
        this.accId = accId;
        this.balance = balance;
        this.lockedStatus = false;
        this.failedPin = 0;
    }

    public double checkBalance() {
        return balance;
    }

    public boolean isLocked() {
        return lockedStatus;
    }

    public int getFailedPin() {
        return failedPin;
    }

    public String getAccId() {
        return accId;
    }
public void deposit(double amount) {
    this.balance += amount;
}

public abstract void withdraw(double amount)
        throws InsufficientFundsException;

public void transfer(Account destination, double amount)
        throws InsufficientFundsException {
    this.withdraw(amount);
    destination.deposit(amount);
}

protected void increaseBalance(double amount) {
    this.balance += amount;
}

protected void decreaseBalance(double amount) {
    this.balance -= amount;
}

public void incrementFailedPin() {
    this.failedPin++;
    if (this.failedPin >= 3) {
        this.lockedStatus = true;
    }
}

public void resetFailedPin() {
    this.failedPin = 0;
}

public void lockAccount() {
    this.lockedStatus = true;
}

public void unlockAccount() {
    this.lockedStatus = false;
    this.failedPin = 0;
}

public abstract String getAccountType();
}