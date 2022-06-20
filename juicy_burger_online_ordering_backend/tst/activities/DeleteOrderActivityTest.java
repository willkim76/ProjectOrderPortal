package activities;

import activites.DeleteOrderActivity;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import daos.OrderDao;
import data.requests.DeleteOrderRequest;
import data.responses.DeleteOrderResponse;
import data.types.Order;

import exceptions.OrderDoesNotExistException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteOrderActivityTest {
    private DeleteOrderActivity deleteOrderActivity;

    @Mock
    private OrderDao orderDao;

    @Mock
    private Context context;

    @Mock
    private LambdaLogger logger;

    @BeforeEach
    void setup() {
        openMocks(this);
        deleteOrderActivity = new DeleteOrderActivity(orderDao);
    }

    @Test
    void handleRequest_withRequestOrderIdWithinDatabase_returnsDeleteOrderResponse() {
        // GIVEN
        DeleteOrderRequest deleteOrderRequest = DeleteOrderRequest.builder()
                .withOrderId("TestOrderId")
                .build();

        Order order = Order.builder()
                .withOrderId("TestOrderId")
                .build();

        when(context.getLogger()).thenReturn(logger);
        when(orderDao.getOrder(deleteOrderRequest.getOrderId())).thenReturn(order);

        // WHEN
        DeleteOrderResponse deleteOrderResponse = deleteOrderActivity.handleRequest(deleteOrderRequest, context);

        // THEN
        assertTrue(deleteOrderResponse.isDidItWork());
    }

    @Test
    void handleRequest_withRequestOrderIdNotInDatabase_returnsDeleteOrderResponse() {
        // GIVEN
        DeleteOrderRequest deleteOrderRequest = DeleteOrderRequest.builder()
                .withOrderId("DNEOrderId")
                .build();

        when(context.getLogger()).thenReturn(logger);
        doThrow(OrderDoesNotExistException.class).when(orderDao).deleteOrder(deleteOrderRequest.getOrderId());

        // WHEN
        DeleteOrderResponse deleteOrderResponse = deleteOrderActivity.handleRequest(deleteOrderRequest, context);

        // THEN
        assertFalse(deleteOrderResponse.isDidItWork());
    }
}
