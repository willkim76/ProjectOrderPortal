package comparators.types;

import comprators.types.OrderDateTimeComparator;
import data.types.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDateTimeComparatorTest {
    private OrderDateTimeComparator orderDateTimeComparator;

    @BeforeEach
    void setup() {
        orderDateTimeComparator = new OrderDateTimeComparator();
    }

    @Test
    void compare_orderWithIdenticalPlacedDatePlacedTime_returnsZero() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();

        Order order_1 = Order.builder()
                .withPlacedDate(now.toLocalDate())
                .withPlacedTime(now.toLocalTime())
                .build();

        Order order_2 = Order.builder()
                .withPlacedDate(now.toLocalDate())
                .withPlacedTime(now.toLocalTime())
                .build();

        // WHEN
        int actual = orderDateTimeComparator.compare(order_1, order_2);

        // THEN
        assertEquals(0, actual);
    }

    @Test
    void compare_orderWithIdenticalPlacedDatePriorPlacedTime_returnsIntLessThanZero() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();

        Order order_1 = Order.builder()
                .withPlacedDate(now.toLocalDate())
                .withPlacedTime(now.toLocalTime().minusMinutes(5))
                .build();

        Order order_2 = Order.builder()
                .withPlacedDate(now.toLocalDate())
                .withPlacedTime(now.toLocalTime())
                .build();

        int actual = orderDateTimeComparator.compare(order_1, order_2);

        assertTrue(actual < 0);
    }

    @Test
    void compare_orderWithIdenticalPlacedDateLaterPlacedTime_returnsIntLessThanZero() {
        LocalDateTime now = LocalDateTime.now();

        Order order_1 = Order.builder()
                .withPlacedDate(now.toLocalDate())
                .withPlacedTime(now.toLocalTime())
                .build();

        Order order_2 = Order.builder()
                .withPlacedDate(now.toLocalDate())
                .withPlacedTime(now.toLocalTime().minusMinutes(5))
                .build();

        int actual = orderDateTimeComparator.compare(order_1, order_2);

        assertTrue(actual > 0);
    }

    @Test
    void compare_orderWithPriorPlacedDateAnyPlacedTime_returnsIntLessThanZero() {
        LocalDateTime now = LocalDateTime.now();

        Order order_1 = Order.builder()
                .withPlacedDate(now.toLocalDate().minusDays(1))
                .build();

        Order order_2 = Order.builder()
                .withPlacedDate(now.toLocalDate())
                .build();

        int actual = orderDateTimeComparator.compare(order_1, order_2);

        assertTrue(actual < 0);
    }

    @Test
    void compare_orderWithLaterPlacedDateAnyPlacedTime_returnsIntGreaterThanZero() {
        LocalDateTime now = LocalDateTime.now();

        Order order_1 = Order.builder()
                .withPlacedDate(now.toLocalDate())
                .build();

        Order order_2 = Order.builder()
                .withPlacedDate(now.toLocalDate().minusDays(1))
                .build();

        int actual = orderDateTimeComparator.compare(order_1, order_2);

        assertTrue(actual > 0);
    }
}
