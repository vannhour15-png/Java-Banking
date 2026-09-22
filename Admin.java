import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    private String adminUsername;
    private String adminPassword;

    public Admin(String adminUsername, String adminPassword) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    public boolean login(String username, String password) {
        return this.adminUsername.equals(username) && this.adminPassword.equals(password);
    }
    
    public void viewUserAccounts(ArrayList<User> users) {
        System.out.println("===== ALL USER ACCOUNTS =====");
        for (User u : users) {
            for (Account acc : u.getAccounts()) {
                System.out.println("User ID: " + u.getUserId());
                System.out.println("Name: " + u.getFullName());
                System.out.println("Phone: " + u.getPhoneNumber());
                System.out.println("Email: " + u.getEmail());
                System.out.println("DOB: " + u.getDob());
                System.out.println("Account ID: " + acc.getAccId());
                System.out.println("Account Type: " + acc.getAccountType());
                System.out.println("Balance: $" + String.format("%.2f", acc.checkBalance()));
                System.out.println("Status: " + (acc.isLocked() ? "Locked" : "Unlocked"));
                System.out.println("Failed PIN Attempts: " + acc.getFailedPin());
                System.out.println("-----------------------------");
            }
        }
    }

    public void lockAccount(Account account) {
        account.lockAccount();
        System.out.println("Account " + account.getAccId() + " has been locked.");
    }

    public void unlockAccount(Account account) {
        account.unlockAccount();
        System.out.println("Account " + account.getAccId() + " has been unlocked.");
    }

    public void createAccount(ArrayList<User> users, Scanner sc) {
        System.out.print("Enter the new customer's full name: ");
        String fullName = sc.nextLine().trim();

        System.out.print("Account type (1 = Savings, 2 = Checking): ");
        String typeChoice = sc.nextLine().trim();

        System.out.print("Set a PIN for the new account: ");
        String pin = sc.nextLine().trim();

        String newUserId = "U" + String.format("%03d", users.size() + 1);
        User newUser = new User(newUserId, fullName, "N/A", "N/A", "N/A", pin);

        String newAccId = "A" + String.format("%03d", countAllAccounts(users) + 1);
        Account newAccount;
        if (typeChoice.equals("2")) {
            newAccount = new CheckingAccount(newAccId, 0, 500);
        } else {
            newAccount = new SavingsAccount(newAccId, 0, 50, 0.02);
        }

        newUser.addAccount(newAccount);
        users.add(newUser);

        System.out.println("Account created successfully.");
        System.out.println("New User ID: " + newUserId);
        System.out.println("New Account ID: " + newAccId + " (" + newAccount.getAccountType() + ")");
    }

    private int countAllAccounts(ArrayList<User> users) {
        int count = 0;
        for (User u : users) {
            count += u.getAccounts().size();
        }
        return count;
    }

    public void logout() {
        System.out.println("Admin logged out.");
    }
}
