package activities;

import activites.UpdateOrderActivity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import daos.MenuItemDao;
import daos.OrderDao;
import data.requests.UpdateOrderRequest;
import data.responses.UpdateOrderResponse;
import data.types.MenuItem;
import data.types.Order;
import data.types.models.MenuItemModel;
import exceptions.InvalidOrderException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import utilities.OrderUtilities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

// TODO: This class needs to be implemented
public class UpdateOrderActivityTest {
    private UpdateOrderActivity updateOrderActivity;

    @Mock
    private OrderDao orderDao;

    @Mock
    private MenuItemDao menuItemDao;

    @Mock
    private Context context;

    @Mock
    private LambdaLogger lambdaLogger;

    @BeforeEach
    void setup() {
        initMocks(this);
        updateOrderActivity = new UpdateOrderActivity(orderDao, menuItemDao);
    }

    @Test
    void handleRequest_withUpdateOrderRequest_returnsAPlaceOrderResponse() {
        // GIVEN
        MenuItem menuItem1 = MenuItem.builder()
                .withName("MenuItem1")
                .withPrice(350)
                .withDescription("MenuItemDescription1")
                .build();

        MenuItem menuItem2 = MenuItem.builder()
                .withName("MenuItem2")
                .withPrice(250)
                .withDescription("MenuItemDescription2")
                .build();

        Map<String, MenuItem> menuItemMap = Map.of(menuItem1.getName(), menuItem1, menuItem2.getName(), menuItem2);

        Map<MenuItem, Integer> mapOfOrderMenuItems = Map.of(menuItem1, 1, menuItem2, 2);

        LocalDateTime placedDateTime = LocalDateTime.now();

        Order order = Order.builder()
                .withOrderId(OrderUtilities.generateOrderId())
                .withPlacedDate(placedDateTime.toLocalDate())
                .withPlacedTime(placedDateTime.toLocalTime())
                .withOrderMenuItems(mapOfOrderMenuItems)
                .withTotalPrice(OrderUtilities.calculateTotalPrice(mapOfOrderMenuItems))
                .build();

        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        one.put("itemNme", menuItem1.getName());
        one.put("itemQty", 9);
        two.put("itemNme", menuItem2.getName());
        two.put("itemQty", 10);

        JSONArray orderDescription = new JSONArray();
        orderDescription.add(one);
        orderDescription.add(two);

        UpdateOrderRequest updateOrderRequest = UpdateOrderRequest.builder()
                .withOrderId(order.getOrderId())
                .withOrderItems(orderDescription)
                .build();

        Map<MenuItem, Integer> updatedMapOfOrderMenuItems = Map.of(menuItem1, 9, menuItem2, 10);

        List<MenuItemModel> updatedListOfMenuItemModels = List.of(
                MenuItemModel.builder()
                        .withName(menuItem1.getName())
                        .withPrice(menuItem1.getPrice())
                        .withDescription(menuItem1.getDescription())
                        .withQuantity(9)
                        .build(),
                MenuItemModel.builder()
                        .withName(menuItem2.getName())
                        .withPrice(menuItem2.getPrice())
                        .withDescription(menuItem2.getDescription())
                        .withQuantity(10)
                        .build()
        );

        Order updatedOrder = Order.builder()
                .withOrderId(order.getOrderId())
                .withPlacedDate(order.getPlacedDate())
                .withPlacedTime(order.getPlacedTime())
                .withOrderMenuItems(updatedMapOfOrderMenuItems)
                .withTotalPrice(OrderUtilities.calculateTotalPrice(updatedMapOfOrderMenuItems))
                .build();


        when(menuItemDao.getMapOfMenuItems()).thenReturn(menuItemMap);
        when(orderDao.getOrder(order.getOrderId())).thenReturn(order);
        when(orderDao.saveOrder(updatedOrder)).thenReturn(updatedOrder);
        when(context.getLogger()).thenReturn(lambdaLogger);

        // WHEN
        UpdateOrderResponse updateOrderResponse = updateOrderActivity.handleRequest(updateOrderRequest, context);

        // THEN
        assertEquals(updateOrderResponse.getOrderModel().getOrderId(), order.getOrderId());
        assertEquals(updateOrderResponse.getOrderModel().getPlacedDate(), placedDateTime.toLocalDate().toString());
        assertEquals(updateOrderResponse.getOrderModel().getPlacedTime(), placedDateTime.toLocalTime().toString());
        assertEquals(updateOrderResponse.getOrderModel().getOrderMenuItemsList(), updatedListOfMenuItemModels);
        assertEquals(updateOrderResponse.getOrderModel().getTotalPrice(), updatedOrder.getTotalPrice());
    }

    @Test
    void handleRequest_withInvalidUpdateOrderRequest_throwsInvalidOrderException() {
        // GIVEN
        MenuItem menuItem1 = MenuItem.builder()
                .withName("MenuItem1")
                .withPrice(350)
                .withDescription("MenuItemDescription1")
                .build();

        MenuItem menuItem2 = MenuItem.builder()
                .withName("MenuItem2")
                .withPrice(250)
                .withDescription("MenuItemDescription2")
                .build();

        Map<String, MenuItem> menuItemMap = Map.of(menuItem1.getName(), menuItem1, menuItem2.getName(), menuItem2);

        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        one.put("itemNme", menuItem1.getName());
        one.put("itemQty", 0);
        two.put("itemNme", menuItem2.getName());
        two.put("itemQty", 2);

        JSONArray orderDescription = new JSONArray();
        orderDescription.add(one);
        orderDescription.add(two);

        UpdateOrderRequest updateOrderRequest = UpdateOrderRequest.builder()
                .withOrderId(OrderUtilities.generateOrderId())
                .withOrderItems(orderDescription)
                .build();

        when(menuItemDao.getMapOfMenuItems()).thenReturn(menuItemMap);
        when(context.getLogger()).thenReturn(lambdaLogger);

        // WHEN - THEN
        assertThrows(InvalidOrderException.class, () -> updateOrderActivity.handleRequest(updateOrderRequest, context));
    }
}
