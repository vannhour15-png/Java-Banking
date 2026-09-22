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
@Override
public void withdraw(double amount) throws InsufficientFundsException {
    checkWithdrawLimit(amount);

    if (amount > checkBalance()) {
        throw new InsufficientFundsException("Insufficient funds.");
    }

    decreaseBalance(amount);
}

public void checkWithdrawLimit(double amount)
        throws InsufficientFundsException {

    if (amount > withdrawLimit) {
        throw new InsufficientFundsException(
            "Withdrawal denied: exceeds the per-transaction limit of $"
            + String.format("%.2f", withdrawLimit) + "."
        );
    }
}
}