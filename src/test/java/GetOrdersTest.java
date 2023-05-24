import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;


import static com.example.sprint_7_mandrikov_ea.CourierController.*;
import static com.example.sprint_7_mandrikov_ea.JsonFiles.*;
import static com.example.sprint_7_mandrikov_ea.OrderController.*;
import static org.hamcrest.Matchers.*;

public class GetOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void successGetAllOrdersTest() {
        executeOrder(ORDER_WITHOUT_COLOR);
        Response response = executeListOrder(new HashMap<String,String>());
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("orders", hasSize(greaterThan(0)));
    }

    @Test
    public void successGetOrdersWithCourierTest() {
        executeCreate(CREATE_COURIER);
        int courierId = getCourierId(LOGIN_COURIER);
        int orderId = parseOrderIdFromResponse(executeOrder(ORDER_WITH_BLACK));
        Response response = executeAcceptOrder(courierId, orderId);
        response.then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(200);
        response = executeListOrder(courierId);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("orders", hasItem(hasEntry("id",orderId)));
        executeDelete(LOGIN_COURIER);
    }

    @Test
    public void failGetOrdersTest() {
        executeCreate(CREATE_COURIER);
        int courierId = getCourierId(LOGIN_COURIER);
        executeDelete(LOGIN_COURIER);
        Response response = executeListOrder(courierId);
        response.then().assertThat().body("message", equalTo("Курьер с идентификатором " + courierId + " не найден"))
                .and()
                .statusCode(404);
    }
}
