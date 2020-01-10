package app.bo.user.service;

import app.bo.api.user.UserStatusAJAXView;
import app.user.api.user.UserStatusView;
import org.junit.jupiter.api.Test;

/**
 * @author Ethan
 */
public class UserStatusTest {
    @Test
    void Test() {
        for (UserStatusView userStatusView : UserStatusView.values()) {
            System.out.println(UserStatusAJAXView.valueOf(userStatusView.name()));
        }
        for (UserStatusAJAXView userStatusAJAXView : UserStatusAJAXView.values()) {
            System.out.println(UserStatusView.valueOf(userStatusAJAXView.name()));
        }
    }
}
