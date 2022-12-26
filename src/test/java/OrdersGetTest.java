import org.order.OrdersClient;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersGetTest {
    private final OrdersClient ordersClient = new OrdersClient();

    @Test
    public void ordersSuccessfulGetsList()
    {
        ordersClient.getAll().assertThat().statusCode(200).and()
                .assertThat().body("orders", notNullValue());
    }
}
