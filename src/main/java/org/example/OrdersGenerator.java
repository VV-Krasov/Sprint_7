package org.example;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class OrdersGenerator {

    public Order randomOrderWithSpecificColor(String[] color)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, new Random().nextInt(1095));
        String randomFutureDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        return new Order(
                RandomStringUtils.randomAlphabetic(1,20),
                RandomStringUtils.randomAlphabetic(1,20),
                RandomStringUtils.randomAlphanumeric(1,20),
                RandomStringUtils.randomAlphanumeric(1,20),
                RandomStringUtils.randomAlphanumeric(1,20),
                new Random().nextInt(999999999),
                randomFutureDate,
                RandomStringUtils.randomAlphanumeric(1,20),
                color
        );
    }
}
