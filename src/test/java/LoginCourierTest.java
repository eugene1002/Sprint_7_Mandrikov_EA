import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static com.example.sprint_7_mandrikov_ea.CourierController.executeCreate;
import static com.example.sprint_7_mandrikov_ea.CourierController.executeLogin;
import static com.example.sprint_7_mandrikov_ea.JsonFiles.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Test
    public void successCourierLoginTest() {
        executeCreate(CREATE_COURIER);
        Response response = executeLogin(LOGIN_COURIER);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    public void failLoginNonExistCourierTest() {
        Response response = executeLogin(LOGIN_NON_EXIST_COURIER);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void failLoginWithWrongLoginTest() {
        Response response = executeLogin(LOGIN_COURIER_WITH_WRONG_LOGIN);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void failLoginWithWrongPasswordTest() {
        Response response = executeLogin(LOGIN_COURIER_WITH_WRONG_PASSWORD);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
