package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String CREATE = "/api/auth/register";

    private static final String LOGIN = "/api/auth/login";

    private static final String EDIT = "/api/auth/user";

    private static final String DELETE = "/api/auth/user";

    @Step("Create user")
    public Response createUser(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(CREATE);
    }

    @Step("Login user")
    public Response loginUser(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(LOGIN);
    }

    @Step("Edit user")
    public Response editUser(String token, Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", token)
                .body(body)
                .when()
                .patch(EDIT);
    }

    @Step("Delete user")
    public Response deleteUser(String token) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", token)
                .when()
                .delete(DELETE);
    }
}
