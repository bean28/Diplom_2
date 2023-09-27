import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserEditTest extends BaseTest {

    String token;

    UserClient userClient = new UserClient();

    @Before
    public void createUser() {
        Response response = userClient.createUser(new User(email, password, name));
        token = response.getBody().path("accessToken");
    }

    @Test
    @DisplayName("Edit user email with token")
    public void editUserEmailWithToken() {
        Response response = userClient.editUser(token, new User("BabaCapa@yandex.ru", password, name));
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Edit user password with token")
    public void editUserPasswordWithToken() {
        Response response = userClient.editUser(token, new User(email, "098766", name));
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Edit user name with token")
    public void editUserNameWithToken() {
        Response response = userClient.editUser(token, new User(email, password, "TetyaMotya"));
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Edit user email without token")
    public void editUserEmailWithoutToken() {
        Response response = userClient.editUser("", new User("PaykShnuk@yandex.ru", password, name));
        response.then().assertThat().statusCode(401);
        response.then().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Edit user password without token")
    public void editUserPasswordWithoutToken() {
        Response response = userClient.editUser("", new User(email, "065457632", name));
        response.then().assertThat().statusCode(401);
        response.then().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Edit user name without token")
    public void editUserNameWithoutToken() {
        Response response = userClient.editUser("", new User(email, password, "Mila"));
        response.then().assertThat().statusCode(401);
        response.then().assertThat().body("success", equalTo(false));
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(token);
    }
}
