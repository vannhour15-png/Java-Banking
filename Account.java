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
}