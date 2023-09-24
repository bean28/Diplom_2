import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreateExistedTest extends BaseTest {

    UserClient userClient = new UserClient();

    @Test
    @DisplayName("Create user without email")
    public void createUserWithoutEmailTest() {
        Response response = userClient.createUser(new User("", password, name));
        response.then().assertThat().statusCode(403);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user without password")
    public void createUserWithoutPasswordTest() {
        Response response = userClient.createUser(new User(email, "", name));
        response.then().assertThat().statusCode(403);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}
