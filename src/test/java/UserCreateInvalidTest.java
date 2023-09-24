import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreateInvalidTest extends BaseTest {

    String token;

    UserClient userClient = new UserClient();

    @Before
    public void createUser() {
        Response response = userClient.createUser(new User(email, password, name));
        token = response.getBody().path("accessToken");
    }

    @Test
    @DisplayName("Create user")
    public void createUserTest() {
        Response response = userClient.createUser(new User(email, password, name));
        response.then().assertThat().statusCode(403);
        response.then().assertThat().body("message", equalTo("User already exists"));
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(token);
    }
}
