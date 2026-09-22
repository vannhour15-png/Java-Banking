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
}