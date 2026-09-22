import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static Admin admin = new Admin("admin", "admin123");
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();

        System.out.println("=========================================");
        System.out.println(" Welcome to the Java ATM System");
        System.out.println(" (Admin test login -> Username: admin | Password: admin123)");
        System.out.println("=========================================");

        boolean running = true; 
        while (running) {
            System.out.println("\n===== ATM SYSTEM =====");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleUserLogin();
                    break;
                case "2":
                    handleAdminLogin();
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        sc.close();
    }
    private static void loadSampleData() {
        User u1 = new User("U001", "Van Hour", "012345678", "van@gmail.com", "01/01/2005", "1234");
        SavingsAccount a1 = new SavingsAccount("A001", 1000, 100, 0.02);
        CheckingAccount a2 = new CheckingAccount("A002", 1500, 500);
        u1.addAccount(a1);
        u1.addAccount(a2);
        users.add(u1);
    }
    private static void handleUserLogin() {
        User user;
        try {
            user = Login.userLogin(users, sc);
        } catch (AccountNotFoundException | AccountLockedException e) {
            System.out.println(e.getMessage());
            return;
        }
        userMenu(user);
    }

    private static void userMenu(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n===== USER MENU =====");
            System.out.println("1. View Profile");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. View Transactions");
            System.out.println("7. Change PIN");
            System.out.println("8. Check interest (savings)");
            System.out.println("9. Logout");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        user.viewProfile();
                        break;
                    case "2": {
                        Account account = selectAccount(user);
                        user.viewBalance(account);
                        break;
                    }
                    case "3": {
                        Account account = selectAccount(user);
                        System.out.print("Enter deposit amount: ");
                        double depAmt = Double.parseDouble(sc.nextLine().trim());
                        user.deposit(account, depAmt);
                        transactions.add(new Transaction("Deposit", depAmt, account.getAccId()));
                        break;
                    }
                    case "4": {
                        Account account = selectAccount(user);
                        System.out.print("Enter withdrawal amount: ");
                        double wAmt = Double.parseDouble(sc.nextLine().trim());
                        user.withdraw(account, wAmt);
                        transactions.add(new Transaction("Withdraw", wAmt, account.getAccId()));
                        break;
                    }
                    case "5": {
                        System.out.println("Which of your accounts is the money coming FROM?");
                        Account account = selectAccount(user);
                        System.out.print("Enter destination Account ID: ");
                        String destId = sc.nextLine().trim();
                        Account dest = findAccount(destId);
                        if (dest == null) {
                            System.out.println("Destination account not found.");
                            break;
                        }
                        System.out.print("Enter transfer amount: ");
                        double tAmt = Double.parseDouble(sc.nextLine().trim());
                        user.transfer(account, dest, tAmt);
                        transactions.add(new Transaction("Transfer Out", tAmt, account.getAccId()));
                        transactions.add(new Transaction("Transfer In", tAmt, dest.getAccId()));
                        break;
                    }
                    case "6": {
                        Account account = selectAccount(user);
                        user.viewTransactions(transactions, account);
                        break;
                    }
                    case "7":
                        System.out.print("Enter current PIN: ");
                        String oldPin = sc.nextLine().trim();
                        System.out.print("Enter new PIN: ");
                        String newPin = sc.nextLine().trim();
                        user.changePin(oldPin, newPin);
                        break;
                    case "8": {
                        Account account = selectAccount(user);
                        if (!(account instanceof SavingsAccount)) {
                            System.out.println("Interest only applies to savings accounts.");
                            break;
                        }
                        SavingsAccount savings = (SavingsAccount) account;
                        double interest = savings.calculateInterest();

                        System.out.println("Account ID: " + savings.getAccId());
                        System.out.println("Interest rate: " + (savings.getInterestRate() * 100) + "%");
                        System.out.println("Interest amount: $" + String.format("%.2f", interest));

                        savings.applyInterest();
                        transactions.add(new Transaction("Interest Applied", interest, savings.getAccId()));
                        System.out.println("New balance: $" + String.format("%.2f", savings.checkBalance()));
                        break;
                    }
                    case "9":
                        user.logout();
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (InsufficientFundsException | InvalidPinException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    private static Account selectAccount(User user) {
        ArrayList<Account> accounts = user.getAccounts();
        if (accounts.size() == 1) {
            return accounts.get(0);
        }

        System.out.println("Select an account:");
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            System.out.println((i + 1) + ". " + a.getAccId() + " (" + a.getAccountType()
                    + ") - Balance: $" + String.format("%.2f", a.checkBalance()));
        }
        System.out.print("Enter choice: ");

        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 1;
        }
        if (choice < 1 || choice > accounts.size()) {
            System.out.println("Invalid choice, defaulting to the first account.");
            choice = 1;
        }
        return accounts.get(choice - 1);
    }
    private static void handleAdminLogin() {
        System.out.print("Enter admin username: ");
        String username = sc.nextLine().trim();
        System.out.print("Enter admin password: ");
        String password = sc.nextLine().trim();

        if (!admin.login(username, password)) {
            System.out.println("Invalid admin credentials.");
            return;
        }
        System.out.println("Admin login successful.");
        adminMenu();
    }

    private static void adminMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View User Accounts");
            System.out.println("2. Lock Account");
            System.out.println("3. Unlock Account");
            System.out.println("4. Create Account");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    admin.viewUserAccounts(users);
                    break;
                case "2": {
                    System.out.print("Enter Account ID to lock: ");
                    Account acc = findAccount(sc.nextLine().trim());
                    if (acc == null) System.out.println("Account not found.");
                    else admin.lockAccount(acc);
                    break;
                }
                case "3": {
                    System.out.print("Enter Account ID to unlock: ");
                    Account acc = findAccount(sc.nextLine().trim());
                    if (acc == null) System.out.println("Account not found.");
                    else admin.unlockAccount(acc);
                    break;
                }
                case "4":
                    admin.createAccount(users, sc);
                    break;
                case "5":
                    admin.logout();
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static Account findAccount(String accId) {
        for (User u : users) {
            for (Account acc : u.getAccounts()) {
                if (acc.getAccId().equals(accId)) {
                    return acc;
                }
            }
        }
        return null;
    }
}
