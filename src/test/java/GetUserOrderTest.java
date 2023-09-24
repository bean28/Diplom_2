import api.client.OrdersClient;
import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetUserOrderTest extends BaseTest {

    OrdersClient ordersClient = new OrdersClient();
    UserClient userClient = new UserClient();
    String token;

    @Before
    public void prepare() {
        Response userResponse = userClient.createUser(new User(email, password, name));
        token = userResponse.getBody().path("accessToken");
    }

    @Test
    @DisplayName("Get order")
    public void getOrderTest() {
        Response response = ordersClient.getUserOrders(token);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Get order without auth")
    public void getOrderWithoutAuthTest() {
        Response response = ordersClient.getUserOrders("");
        response.then().assertThat().statusCode(401);
        response.then().assertThat().body("success", equalTo(false));
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(token);
    }
}
