import java.util.ArrayList;

public class User {

    private String userId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String dob;
    private String pin; 
    
    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }
    private ArrayList<Account> accounts;

    public User(String userId, String fullName, String phoneNumber, String email, String dob, String pin) {
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dob = dob;
        this.pin = pin;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public boolean checkPin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    public void changePin(String oldPin, String newPin) throws InvalidPinException {
        if (!checkPin(oldPin)) {
            throw new InvalidPinException("Incorrect current PIN. PIN was not changed.");
        }
        this.pin = newPin;
        System.out.println("PIN changed successfully.");
    }

    public void viewProfile() {
        System.out.println("===== PROFILE =====");
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + fullName);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("DOB: " + dob);
    }

    public void viewBalance(Account account) {
        System.out.println("Account " + account.getAccId() + " balance: $"
                + String.format("%.2f", account.checkBalance()));
    }

    public void deposit(Account account, double amount) {
        account.deposit(amount);
        System.out.println("Deposit successful. New balance: $" + String.format("%.2f", account.checkBalance()));
    }

    public void withdraw(Account account, double amount) throws InsufficientFundsException {
        account.withdraw(amount); 
        System.out.println("Withdrawal successful. New balance: $" + String.format("%.2f", account.checkBalance()));
    }

    public void transfer(Account from, Account to, double amount) throws InsufficientFundsException {
        from.transfer(to, amount);
        System.out.println("Transfer of $" + String.format("%.2f", amount) + " successful.");
    }

    public void viewTransactions(ArrayList<Transaction> allTransactions, Account account) {
        System.out.println("===== TRANSACTION HISTORY =====");
        System.out.println("Account ID: " + account.getAccId());
        boolean found = false;
        for (Transaction t : allTransactions) {
            if (t.getAccId().equals(account.getAccId())) {
                t.viewTransaction();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions yet.");
        }
    }

    public void logout() {
        System.out.println("User " + fullName + " logged out.");
    }

}
