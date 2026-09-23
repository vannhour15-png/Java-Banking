public class SavingsAccount extends Account {

    private double minimumBalance;
    private double interestRate;

    public SavingsAccount(String accId, double balance,
                          double minimumBalance, double interestRate) {
        super(accId, balance);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (checkBalance() - amount < minimumBalance) {
            throw new InsufficientFundsException(
                "Withdrawal denied: balance cannot fall below the minimum balance of $"
                + String.format("%.2f", minimumBalance) + "."
            );
        }

        decreaseBalance(amount);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
public double calculateInterest() {
    return checkBalance() * interestRate;
}

public void applyInterest() {
    increaseBalance(calculateInterest());
}

public double getInterestRate() {
    return interestRate;
}
}