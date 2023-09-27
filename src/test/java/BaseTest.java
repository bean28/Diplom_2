import io.restassured.RestAssured;
import org.junit.Before;

public abstract class BaseTest {

    String email = "Luntik7@yandex.ru";
    String password = "374682";
    String name = "Luntik7";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }
}
