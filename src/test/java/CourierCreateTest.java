import org.example.*;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierCreateTest {

    private final CourierGenerator courierGenerator = new CourierGenerator();
    private final CourierClient courierClient = new CourierClient();
    private List<CourierId> courierIds = new ArrayList<>();
    @After
    public void cleanUp()
    {
        for (CourierId courierId: courierIds
             ) {
                if (courierId.getId() > 0)
                    courierClient.delete(courierId);
        }
    }

    @Test
    public void courierCreatingTest()
    {
        Courier courier = courierGenerator.random();

        courierClient.createCourier(courier)
        .assertThat().statusCode(SC_CREATED)
                .body("ok", is(true));


        courierIds.add(new CourierId(
                courierClient.login(CourierCredentials.from(courier))
                        .extract().path("id")));
    }

    @Test
    public void existsSameCourierCreating()
    {
        Courier courier = new Courier("1","1234","saske");
        courierClient.createCourier(courier)
                .assertThat().statusCode(SC_CONFLICT)
                .and().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void courierCreatingAllRequiredFieldMustBeFIlled()
    {
        Courier courier = courierGenerator.random();

        courier.setLogin(null);
        courierClient.createCourier(courier)
                .assertThat().statusCode(SC_BAD_REQUEST)
                .and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void someFieldDoesntExistsReturnsError()
    {
        Courier courier = new Courier("1",null,null);

        courierClient.createCourier(courier)
                .assertThat().statusCode(SC_BAD_REQUEST)
                .and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
