package daos;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import data.types.MenuItem;
import data.types.Order;
import exceptions.OrderDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderDaoTest {
    private OrderDao orderDao;

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    void setup() {
        initMocks(this);
        orderDao = new OrderDao(dynamoDBMapper);
    }

    @Test
    void saveOrder_withOrder_dynamoDBSaveMethodIsCalled() {
        // GIVEN
        Order expected = Order.builder()
                .withOrderId("Test1")
                .withPlacedDate(LocalDate.now())
                .withPlacedTime(LocalTime.now())
                .withOrderMenuItems(
                        Map.of(
                                MenuItem.builder()
                                        .withName("TestMenuItem")
                                        .withDescription("TestMenuItemDescription")
                                        .withPrice(-99)
                                        .build(),
                                -2
                        )
                )
                .withTotalPrice(-99)
                .build();

        // WHEN
        Order actual = orderDao.saveOrder(expected);

        // THEN
        assertEquals(expected, actual);
        verify(dynamoDBMapper, times(1)).save(expected);
    }

    @Test
    void getOrder_withExistingOrder_anOrderIsReturned() {
        // GIVEN
        Order expected = Order.builder()
                .withOrderId("Test1")
                .withPlacedDate(LocalDate.now())
                .withPlacedTime(LocalTime.now())
                .withOrderMenuItems(
                        Map.of(
                                MenuItem.builder()
                                        .withName("TestMenuItem")
                                        .withDescription("TestMenuItemDescription")
                                        .withPrice(-99)
                                        .build(),
                                -2
                        )
                )
                .withTotalPrice(-99)
                .build();

        when(dynamoDBMapper.load(Order.class, expected.getOrderId())).thenReturn(expected);

        // WHEN
        Order actual = orderDao.getOrder(expected.getOrderId());

        // THEN
        assertEquals(expected, actual);
        verify(dynamoDBMapper, times(1)).load(Order.class, expected.getOrderId());
    }

    @Test
    void getOrder_withNonExistingOrder_OrderDoesNotExistExceptionIsThrown() {
        // GIVEN
        String nonExistingOrderId = "DNE";

        when(dynamoDBMapper.load(Order.class, nonExistingOrderId)).thenReturn(null);

        // WHEN - THEN
        assertThrows(OrderDoesNotExistException.class,
                () -> orderDao.getOrder(nonExistingOrderId)
        );
    }

    @Test
    void deleteOrder_withExistingOrder_dynamoDBDeleteMethodIsCalled() {
        // GIVEN
        Order expected = Order.builder()
                .withOrderId("Test1")
                .withPlacedDate(LocalDate.now())
                .withPlacedTime(LocalTime.now())
                .withOrderMenuItems(
                        Map.of(
                                MenuItem.builder()
                                        .withName("TestMenuItem")
                                        .withDescription("TestMenuItemDescription")
                                        .withPrice(-99)
                                        .build(),
                                -2
                        )
                )
                .withTotalPrice(-99)
                .build();

        when(dynamoDBMapper.load(Order.class, expected.getOrderId())).thenReturn(expected);

        // WHEN
        orderDao.deleteOrder(expected.getOrderId());

        // THEN
        verify(dynamoDBMapper, times(1)).delete(expected);
    }

    @Test
    void deleteOrder_withNonExistingOrder_OrderDoesNotExistExceptionIsThrown() {
        // GIVEN
        String nonExistingOrderId = "DNE";

        when(dynamoDBMapper.load(Order.class, nonExistingOrderId)).thenReturn(null);

        // WHEN - THEN
        assertThrows(OrderDoesNotExistException.class,
                () -> orderDao.deleteOrder(nonExistingOrderId)
        );
    }
}
