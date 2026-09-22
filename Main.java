import java.util.ArrayList;
import java.util.Scanner;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;

public class Main {
    private static ArrayList<User> users = new ArrayList();
   private static ArrayList<Transaction> transactions = new ArrayList();
   private static Admin admin = new Admin("admin", "admin123");
   private static Scanner sc;

   public Main() {
   }

   public static void main(String[] var0) {
      loadSampleData();
      System.out.println("=========================================");
      System.out.println(" Welcome to the Java ATM System");
      System.out.println(" (Admin test login -> Username: admin | Password: admin123)");
      System.out.println("=========================================");
      boolean var1 = true;

      while(var1) {
         System.out.println("\n===== ATM SYSTEM =====");
         System.out.println("1. User Login");
         System.out.println("2. Admin Login");
         System.out.println("3. Exit");
         System.out.print("Choose an option: ");
         switch (sc.nextLine().trim()) {
            case "1":
               handleUserLogin();
               break;
            case "2":
               handleAdminLogin();
               break;
            case "3":
               var1 = false;
               System.out.println("Goodbye!");
               break;
            default:
               System.out.println("Invalid option. Try again.");
         }
      }

      sc.close();
   }

   private static void loadSampleData() {
      User var0 = new User("U001", "Van Hour", "012345678", "van@example.com", "01/01/2005", "1234");
      SavingsAccount var1 = new SavingsAccount("A001", (double)1000.0F, (double)100.0F, 0.02);
      CheckingAccount var2 = new CheckingAccount("A002", (double)1500.0F, (double)500.0F);
      var0.addAccount(var1);
      var0.addAccount(var2);
      users.add(var0);
   }

   private static void handleUserLogin() {
      User var0;
      try {
         var0 = Login.userLogin(users, sc);
      } catch (AccountLockedException | AccountNotFoundException var2) {
         System.out.println(((Exception)var2).getMessage());
         return;
      }

      userMenu(var0);
   }

   private static void userMenu(User var0) {
      boolean var1 = true;

      while(var1) {
         System.out.println("\n===== USER MENU =====");
         System.out.println("1. View Profile");
         System.out.println("2. Check Balance");
         System.out.println("3. Deposit");
         System.out.println("4. Withdraw");
         System.out.println("5. Transfer");
         System.out.println("6. View Transactions");
         System.out.println("7. Change PIN");
         System.out.println("8. Logout");
         System.out.print("Choose an option: ");
         String var2 = sc.nextLine().trim();

         try {
            switch (var2) {
               case "1":
                  var0.viewProfile();
                  break;
               case "2":
                  Account var16 = selectAccount(var0);
                  var0.viewBalance(var16);
                  break;
               case "3":
                  Account var15 = selectAccount(var0);
                  System.out.print("Enter deposit amount: ");
                  double var19 = Double.parseDouble(sc.nextLine().trim());
                  var0.deposit(var15, var19);
                  transactions.add(new Transaction("Deposit", var19, var15.getAccId()));
                  break;
               case "4":
                  Account var14 = selectAccount(var0);
                  System.out.print("Enter withdrawal amount: ");
                  double var18 = Double.parseDouble(sc.nextLine().trim());
                  var0.withdraw(var14, var18);
                  transactions.add(new Transaction("Withdraw", var18, var14.getAccId()));
                  break;
               case "5":
                  System.out.println("Which of your accounts is the money coming FROM?");
                  Account var13 = selectAccount(var0);
                  System.out.print("Enter destination Account ID: ");
                  String var17 = sc.nextLine().trim();
                  Account var7 = findAccount(var17);
                  if (var7 == null) {
                     System.out.println("Destination account not found.");
                  } else {
                     System.out.print("Enter transfer amount: ");
                     double var8 = Double.parseDouble(sc.nextLine().trim());
                     var0.transfer(var13, var7, var8);
                     transactions.add(new Transaction("Transfer Out", var8, var13.getAccId()));
                     transactions.add(new Transaction("Transfer In", var8, var7.getAccId()));
                  }
                  break;
               case "6":
                  Account var12 = selectAccount(var0);
                  var0.viewTransactions(transactions, var12);
                  break;
               case "7":
                  System.out.print("Enter current PIN: ");
                  String var5 = sc.nextLine().trim();
                  System.out.print("Enter new PIN: ");
                  String var6 = sc.nextLine().trim();
                  var0.changePin(var5, var6);
                  break;
               case "8":
                  var0.logout();
                  var1 = false;
                  break;
               default:
                  System.out.println("Invalid option. Try again.");
            }
         } catch (InvalidPinException | InsufficientFundsException var10) {
            System.out.println(((Exception)var10).getMessage());
         } catch (NumberFormatException var11) {
            System.out.println("Please enter a valid number.");
         }
      }

   }

   private static Account selectAccount(User var0) {
      ArrayList var1 = var0.getAccounts();
      if (var1.size() == 1) {
         return (Account)var1.get(0);
      } else {
         System.out.println("Select an account:");

         for(int var2 = 0; var2 < var1.size(); ++var2) {
            Account var3 = (Account)var1.get(var2);
            System.out.println(var2 + 1 + ". " + var3.getAccId() + " (" + var3.getAccountType() + ") - Balance: $" + String.format("%.2f", var3.checkBalance()));
         }

         System.out.print("Enter choice: ");

         int var5;
         try {
            var5 = Integer.parseInt(sc.nextLine().trim());
         } catch (NumberFormatException var4) {
            var5 = 1;
         }

         if (var5 < 1 || var5 > var1.size()) {
            System.out.println("Invalid choice, defaulting to the first account.");
            var5 = 1;
         }

         return (Account)var1.get(var5 - 1);
      }
   }

   private static void handleAdminLogin() {
      System.out.print("Enter admin username: ");
      String var0 = sc.nextLine().trim();
      System.out.print("Enter admin password: ");
      String var1 = sc.nextLine().trim();
      if (!admin.login(var0, var1)) {
         System.out.println("Invalid admin credentials.");
      } else {
         System.out.println("Admin login successful.");
         adminMenu();
      }
   }

   private static void adminMenu() {
      boolean var0 = true;

      while(var0) {
         System.out.println("\n===== ADMIN MENU =====");
         System.out.println("1. View User Accounts");
         System.out.println("2. Lock Account");
         System.out.println("3. Unlock Account");
         System.out.println("4. Create Account");
         System.out.println("5. Logout");
         System.out.print("Choose an option: ");
         switch (sc.nextLine().trim()) {
            case "1":
               admin.viewUserAccounts(users);
               break;
            case "2":
               System.out.print("Enter Account ID to lock: ");
               Account var5 = findAccount(sc.nextLine().trim());
               if (var5 == null) {
                  System.out.println("Account not found.");
               } else {
                  admin.lockAccount(var5);
               }
               break;
            case "3":
               System.out.print("Enter Account ID to unlock: ");
               Account var4 = findAccount(sc.nextLine().trim());
               if (var4 == null) {
                  System.out.println("Account not found.");
               } else {
                  admin.unlockAccount(var4);
               }
               break;
            case "4":
               admin.createAccount(users, sc);
               break;
            case "5":
               admin.logout();
               var0 = false;
               break;
            default:
               System.out.println("Invalid option. Try again.");
         }
      }

   }

   private static Account findAccount(String var0) {
      for(User var2 : users) {
         for(Account var4 : var2.getAccounts()) {
            if (var4.getAccId().equals(var0)) {
               return var4;
            }
         }
      }

      return null;
   }

   static {
      sc = new Scanner(System.in);
   }
}
