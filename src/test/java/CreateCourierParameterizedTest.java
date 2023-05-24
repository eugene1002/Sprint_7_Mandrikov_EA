import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.example.sprint_7_mandrikov_ea.CourierController.*;
import static com.example.sprint_7_mandrikov_ea.JsonFiles.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierParameterizedTest {
    private final String credential; // credential - путь к JSON с учётными данными для создания курьера

    public CreateCourierParameterizedTest(String credential) {
        this.credential = credential;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getJsonLoginVariable() {
        return new Object[][] {
                {CREATE_COURIER_WITHOUT_LOGIN},
                {CREATE_COURIER_WITHOUT_PASSWORD}
        };
    }
    @Test
    public void failCreateTest() {
        Response response = executeCreate(credential);
        // Проверить наличие ошибки при отправлении неверных учётных данных (отсутствие логина или пароля)
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
