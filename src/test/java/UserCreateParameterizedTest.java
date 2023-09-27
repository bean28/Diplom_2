import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class UserCreateParameterizedTest extends BaseTest {

    String email;
    String password;
    String name;
    String token;

    UserClient userClient = new UserClient();

    public UserCreateParameterizedTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "Создание пользователя. Тестовые данные: {0} {1} {2}")
    public static Object[][] parametersOfUser() {
        return new Object[][]{
                {"Luntik8@yandex.ru", "374682", "Luntik8"},
                {"Kuzya@yandex.ru", "qwerty", "Kuzya"}
        };
    }

    @Test
    @DisplayName("Create user")
    public void createUserTest() {
        Response response = userClient.createUser(new User(email, password, name));
        token = response.getBody().path("accessToken");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(token);
    }
}
