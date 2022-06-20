package activities;

import activites.GetOrderActivity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import converters.ModelConverter;

import daos.OrderDao;
import data.requests.GetOrderRequest;
import data.responses.GetOrderResponse;
import data.types.MenuItem;
import data.types.Order;
import data.types.models.MenuItemModel;

import exceptions.OrderDoesNotExistException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import utilities.OrderUtilities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetOrderActivityTest {
    private GetOrderActivity getOrderActivity;

    @Mock
    private OrderDao orderDao;

    @Mock
    private Context context;

    @Mock
    private LambdaLogger logger;

    @BeforeEach
    void setup() {
        openMocks(this);
        getOrderActivity = new GetOrderActivity(orderDao);
    }

    @Test
    void handleRequest_withRequestOrderIdWithinDatabase_returnsGetOrderResponse() {
        // GIVEN
        GetOrderRequest getOrderRequest = GetOrderRequest.builder()
                .withOrderId("TestOrderId")
                .build();

        Map<MenuItem, Integer> orderMenuItems = Map.of(
                MenuItem.builder()
                        .withName("MenuItem1")
                        .withPrice(25)
                        .withDescription("MenuItem1Description")
                        .build(),
                2,
                MenuItem.builder()
                        .withName("MenuItem2")
                        .withPrice(75)
                        .withDescription("MenuItem2Description")
                        .build(),
                2
        );

        List<MenuItemModel> menuItemModelList = ModelConverter.orderMenuItemMapConverter(orderMenuItems);

        LocalDateTime placedDateTime = LocalDateTime.now();

        Order order = Order.builder()
                .withOrderId("TestOrderId")
                .withPlacedDate(placedDateTime.toLocalDate())
                .withPlacedTime(placedDateTime.toLocalTime())
                .withOrderMenuItems(orderMenuItems)
                .withTotalPrice(OrderUtilities.calculateTotalPrice(orderMenuItems))
                .build();

        when(context.getLogger()).thenReturn(logger);
        when(orderDao.getOrder("TestOrderId")).thenReturn(order);

        // WHEN
        GetOrderResponse getOrderResponse = getOrderActivity.handleRequest(getOrderRequest, context);

        // THEN
        assertEquals(getOrderRequest.getOrderId(), getOrderResponse.getOrderModel().getOrderId());
        assertEquals(order.getPlacedDate().toString(), getOrderResponse.getOrderModel().getPlacedDate());
        assertEquals(order.getPlacedTime().toString(), getOrderResponse.getOrderModel().getPlacedTime());
        assertEquals(menuItemModelList, getOrderResponse.getOrderModel().getOrderMenuItemsList());
        assertEquals(order.getTotalPrice(), getOrderResponse.getOrderModel().getTotalPrice());
    }

    @Test
    void handleRequest_withRequestOrderIdNotInDatabase_throwsOrderDoesNotExistException() {
        // GIVEN
        GetOrderRequest getOrderRequest = GetOrderRequest.builder()
                .withOrderId("TestOrderId")
                .build();

        when(context.getLogger()).thenReturn(logger);
        when(orderDao.getOrder("TestOrderId")).thenThrow(OrderDoesNotExistException.class);

        // WHEN - THEN
        assertThrows(OrderDoesNotExistException.class, () -> getOrderActivity.handleRequest(getOrderRequest, context));
    }
}
