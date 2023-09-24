import api.client.OrdersClient;
import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class OrderCreateTest extends BaseTest {

    OrdersClient ordersClient = new OrdersClient();
    UserClient userClient = new UserClient();
    List<Map<String, String>> ingredients;
    String token;

    @Before
    public void prepare() {
        Response userResponse = userClient.createUser(new User(email, password, name));
        token = userResponse.getBody().path("accessToken");
        Response response = ordersClient.getIngredients();
        ingredients = response.getBody().path("data");
    }

    @Test
    @DisplayName("Create order")
    public void createOrderTest() {
        Response response = ordersClient.createOrder(token,
                new Order(ingredients.get(0).get("_id"),
                        ingredients.get(2).get("_id"),
                        ingredients.get(4).get("_id")));
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Create order without authorization")
    public void createOrderWithoutAuthTest() {
        Response response = ordersClient.createOrder("",
                new Order(ingredients.get(0).get("_id"),
                        ingredients.get(2).get("_id"),
                        ingredients.get(4).get("_id")));
        response.then().assertThat().statusCode(401);
        response.then().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Create order without ingredients")
    public void createOrderWithoutIngredientsTest() {
        Response response = ordersClient.createOrder(token,
                new Order());
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Create order with wrong hash")
    public void createOrderWithWrongHashTest() {
        Response response = ordersClient.createOrder(token,
                new Order("61c0c5a71d1f82001bdaaa00"));
        response.then().assertThat().statusCode(500);
        response.then().assertThat().body("success", equalTo(false));
    }

    @After
    public void deleteUser() {
        userClient.deleteUser(token);
    }
}
