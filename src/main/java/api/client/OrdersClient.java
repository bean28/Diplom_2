package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static final String ORDER = "/api/orders";

    private static final String INGREDIENTS = "/api/ingredients";

    @Step("Create order")
    public Response createOrder(String token, Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", token)
                .body(body)
                .when()
                .post(ORDER);
    }

    @Step("Create order")
    public Response getIngredients() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(INGREDIENTS);
    }

    @Step("Create order")
    public Response getUserOrders(String token) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", token)
                .when()
                .get(ORDER);
    }
}
