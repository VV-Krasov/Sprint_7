import org.apache.commons.lang3.RandomStringUtils;
import org.example.*;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class CourierLoginTest {

    private final CourierGenerator courierGenerator = new CourierGenerator();
    private final CourierClient courierClient = new CourierClient();
    private List<CourierId> courierIds = new ArrayList<>();

    @After
    public void cleanUp() {
        for (CourierId courierId : courierIds
        ) {
            if (courierId.getId() > 0)
                courierClient.delete(courierId);
        }
    }

    @Test
    public void loginWithCorrectData() {
        Courier courier = courierGenerator.random();
        courierClient.createCourier(courier);

        CourierCredentials courierCredentials = CourierCredentials.from(courier);
        courierIds.add(new CourierId(
                courierClient.login(courierCredentials)
                .assertThat().statusCode(SC_OK).and()
                        .assertThat().body("id", greaterThan(0))
                        .extract().path("id")));
    }

    @Test
    public void loginWithoutAllRequiredFieldsExist()
    {
        Date date = new Date();
        CourierCredentials courierCredentials = new CourierCredentials("", date.toString());
        courierClient.login(courierCredentials)
                .assertThat().statusCode(SC_BAD_REQUEST).and()
                    .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithWrongPassword()
    {
        Courier courier = courierGenerator.random();
        courierClient.createCourier(courier);

        CourierCredentials courierCredentials = CourierCredentials.from(courier);
        courierIds.add(new CourierId(
                courierClient.login(courierCredentials)
                        .extract().path("id")));

        courierCredentials.setPassword(RandomStringUtils.randomAlphanumeric(1,99));
        courierClient.login(courierCredentials)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithUserThatDoesntExist()
    {
        Courier courier = courierGenerator.random();
        CourierCredentials courierCredentials = CourierCredentials.from(courier);
        courierClient.login(courierCredentials)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}
