import org.example.Order;
import org.example.OrdersClient;
import org.example.OrdersGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class OrdersCreateTest {
    private final OrdersGenerator ordersGenerator = new OrdersGenerator();
    private final OrdersClient ordersClient = new OrdersClient();
    @Parameterized.Parameter
    public String[] color;

    public OrdersCreateTest()
    {

    }

    @Parameterized.Parameters
    public static Object[][] getColor()
    {
        return new Object[][] {
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{}}
        };
    }

    @Test
    public void successOrderCreateResponseContainsTrack()
    {
        Order order = ordersGenerator.randomOrderWithSpecificColor(color);
        ordersClient.create(order)
                .assertThat().statusCode(SC_CREATED).and()
                .assertThat().body("track", greaterThan(0));
    }
}