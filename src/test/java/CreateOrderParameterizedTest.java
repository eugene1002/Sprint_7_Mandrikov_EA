import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.example.sprint_7_mandrikov_ea.JsonFiles.*;
import static com.example.sprint_7_mandrikov_ea.OrderController.executeOrder;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {

    private final String pathToOrderRequestBody;
    public CreateOrderParameterizedTest(String pathToOrderRequestBody) {
        this.pathToOrderRequestBody = pathToOrderRequestBody;
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Parameterized.Parameters
    public static Object[][] colorForOrder() {
        return new Object[][] {
                {ORDER_WITH_BLACK},
                {ORDER_WITH_GREY},
                {ORDER_WITH_BLACK_AND_GREY},
                {ORDER_WITHOUT_COLOR}
        };
    }
    @Test
    public void successCreateOrderTest() {
        Response response = executeOrder(pathToOrderRequestBody);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}
