import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static com.example.sprint_7_mandrikov_ea.CourierController.*;
import static com.example.sprint_7_mandrikov_ea.JsonFiles.*;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void successSingleTest() {
        executeDelete(LOGIN_COURIER);
        Response response = executeCreate(CREATE_COURIER);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
        executeDelete(LOGIN_COURIER);
    }

    @Test
    public void failDoubleTest() {
        executeCreate(CREATE_COURIER);
        Response response = executeCreate(CREATE_COURIER);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
        executeDelete(LOGIN_COURIER);
    }
}
