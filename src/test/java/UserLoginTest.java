import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest extends BaseTest {

    String token;

    UserClient userClient = new UserClient();

    @Before
    public void createUser() {
        Response response = userClient.createUser(new User(email, password, name));
        token = response.getBody().path("accessToken");
    }

    @Test
    @DisplayName("Login user")
    public void loginUser() {
        Response response = userClient.loginUser(new User(email, password, name));
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Login with wrong password user")
    public void loginUserWithWrongPassword() {
        Response response = userClient.loginUser(new User(email, "348753", name));
        response.then().assertThat().statusCode(401);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login with wrong email user")
    public void loginUserWithWrongEmail() {
        Response response = userClient.loginUser(new User("dhbced", password, name));
        response.then().assertThat().statusCode(401);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(token);
    }
}
