import com.example.sprint_7_mandrikov_ea.CreateCourier;
import com.example.sprint_7_mandrikov_ea.CreateOrder;
import com.example.sprint_7_mandrikov_ea.LoginCourier;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

import static com.example.sprint_7_mandrikov_ea.CourierController.*;
import static com.example.sprint_7_mandrikov_ea.OrderController.*;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class GetOrdersTest {
    LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","12345");
    CreateOrder createOrder = new CreateOrder("Shikamaru","Nara","Konoha, 123 apt.",4,"+7-800-355-35-35", 5,"2020-06-06","Sasuke, come back to Konoha", new String[]{"BLACK"});
    private final int courierId = getCourierId(loginCourier);
    private final int orderId = parseOrderIdFromResponse(executeOrder(createOrder));
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void successGetAllOrdersTest() {
        executeOrder(createOrder);
        Response response = executeListOrder(new HashMap<String,String>());
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", hasSize(greaterThan(0)));
    }

    @Test
    public void successGetOrdersWithCourierTest() {
        CreateCourier createCourier = new CreateCourier("NarutoUzumakovich","12345","NARUTOOOOOOO");
        executeCreate(createCourier);
        Response response = executeAcceptOrder(courierId, orderId);
        response.then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(SC_OK);
        response = executeListOrder(courierId);
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", hasItem(hasEntry("id",orderId)));
    }

    @Test
    public void failGetOrdersTest() {
        CreateCourier createCourier = new CreateCourier("NarutoUzumakovich","12345","NARUTOOOOOOO");
        LoginCourier loginCourier = new LoginCourier("NarutoUzumakovich","12345");
        if (executeLogin(loginCourier).getStatusCode() == SC_OK) {
            executeDelete(loginCourier);
        } else {
            executeCreate(createCourier);
            executeDelete(loginCourier);
            Response response = executeListOrder(courierId);
            response.then().assertThat().body("message", equalTo("Курьер с идентификатором " + courierId + " не найден"))
                    .and()
                    .statusCode(SC_NOT_FOUND);
        }
    }

    @After
    public void deleteChanges() {
        executeDelete(loginCourier);
        executeDeleteOrder(orderId);
    }
}
