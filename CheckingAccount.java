public class CheckingAccount extends Account {

    private double withdrawLimit;

    public CheckingAccount(String accId, double balance, double withdrawLimit) {
        super(accId, balance);
        this.withdrawLimit = withdrawLimit;
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }
}