import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.example.sprint_7_mandrikov_ea.CourierController.executeCreate;
import static com.example.sprint_7_mandrikov_ea.CourierController.executeLogin;
import static com.example.sprint_7_mandrikov_ea.JsonFiles.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginCourierParameterizedTest {
    private final String pathToLogin; // pathToLogin - путь к JSON с учётными данными для логина курьера

    public LoginCourierParameterizedTest(String pathToLogin) {
        this.pathToLogin = pathToLogin;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getJsonLoginVariable() {
        return new Object[][] {
                {LOGIN_COURIER_WITHOUT_LOGIN},
                {LOGIN_COURIER_WITHOUT_PASSWORD}
        };
    }
    @Test
    public void failLoginTestWithoutRequiredFields() {
        executeCreate(CREATE_COURIER);
        Response response = executeLogin(pathToLogin);
        // Проверить наличие ошибки при отправлении неверных учётных данных (отсутствие логина или пароля)
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
}
