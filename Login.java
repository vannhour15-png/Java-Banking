import java.util.ArrayList;
import java.util.Scanner;

public class Login {

    private static User findUser(ArrayList<User> users, String userId) {
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                return u;
            }
        }
        return null;
    }
public static User userLogin(ArrayList<User> users, Scanner sc)
        throws AccountNotFoundException, AccountLockedException {

    System.out.print("Enter User ID: ");
    String userId = sc.nextLine().trim();

    User user = findUser(users, userId);

    if (user == null) {
        throw new AccountNotFoundException(
            "No user found with ID " + userId + "."
        );
    }

    Account account = user.getAccounts().get(0);

    if (account.isLocked()) {
        throw new AccountLockedException(
            "This account is locked. Please contact an administrator."
        );
    }

    for (int attempt = 1; attempt <= 3; attempt++) {
        System.out.print("Enter PIN: ");
        String enteredPin = sc.nextLine().trim();

        if (user.checkPin(enteredPin)) {
            account.resetFailedPin();

            System.out.println(
                "Login successful. Welcome, "
                + user.getFullName() + "!"
            );

            return user;
        } else {
            account.incrementFailedPin();

            if (account.isLocked()) {
                throw new AccountLockedException(
                    "Account has been locked after 3 failed PIN attempts."
                );
            }

            int remaining = 3 - account.getFailedPin();

            System.out.println(
                "Incorrect PIN. Attempts remaining: " + remaining
            );
        }
    }

    throw new AccountLockedException(
        "Account has been locked after 3 failed PIN attempts."
    );
}
}