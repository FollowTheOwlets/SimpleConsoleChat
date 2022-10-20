import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.Socket;

public class UserTest {
    User user;

    @Test
    void setClientSocketFirstTest() {
        System.out.println("/-- setClientSocketFirstTest begin ---");
        Socket socket = new Socket();
        User checkUser = new User().setClientSocket(socket);
        user = new User().setClientSocket(socket);
        Assertions.assertEquals(user, checkUser);
        System.out.println("--- setClientSocketFirstTest complete --/");
    }

    @Test
    void setClientSocketSecondTest() {
        System.out.println("/-- setClientSocketSecondTest begin ---");
        User checkUser = new User().setClientSocket(new Socket());
        user = new User().setClientSocket(new Socket());
        Assertions.assertNotEquals(user, checkUser);
        System.out.println("--- setClientSocketSecondTest complete --/");
    }
}
