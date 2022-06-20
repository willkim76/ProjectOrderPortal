package integreation;

import data.requests.*;
import data.responses.*;
import data.types.models.OrderModel;
import exceptions.OrderDoesNotExistException;
import integreation.providers.TestDataProvider;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.MethodOrderer.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * PhaseBeta tests runs through all the base case scenarios
 * for all the endpoints. Test must be run all at once!
 *
 * String menuItem names in the orderDescription must be
 * current with the menuItems.json file!
 */
@TestMethodOrder(OrderAnnotation.class)
public class PhaseBeta {
    private TestDataProvider testDataProvider;
    private static String generatedOrderId = null;
    private static String generatedDate = null;

    @BeforeEach
    void setup() {
        testDataProvider = new TestDataProvider();
    }

    @AfterEach
    void teardown() {
        assertNotNull(generatedOrderId);
        assertNotNull(generatedDate);
    }

    @Test
    @Order(1)
    void placeOrderActivity_withPlaceOrderRequest_returnsPlaceOrderResponse() {
        // GIVEN
        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        JSONObject three = new JSONObject();
        JSONObject four = new JSONObject();

        one.put("itemNme", "Juicy Cheeseburger");
        one.put("itemQty", 12);
        two.put("itemNme", "Juicy Bacon Cheeseburger");
        two.put("itemQty", 34);
        three.put("itemNme", "3 Piece Chicken Nuggets");
        three.put("itemQty", 56);
        four.put("itemNme", "Sprite");
        four.put("itemQty", 78);

        JSONArray orderDescription = new JSONArray();
        orderDescription.add(one);
        orderDescription.add(two);
        orderDescription.add(three);
        orderDescription.add(four);

        PlaceOrderRequest request = testDataProvider.createPlaceOrderRequest(orderDescription);

        // WHEN
        PlaceOrderResponse response = testDataProvider.placeOrder(request);
        generatedOrderId = response.getOrderModel().getOrderId();
        generatedDate = response.getOrderModel().getPlacedDate();

        // THEN
        assertEquals("3 Piece Chicken Nuggets", response.getOrderModel().getOrderMenuItemsList().get(0).getName());
        assertEquals(56, response.getOrderModel().getOrderMenuItemsList().get(0).getQuantity());
        assertEquals("Juicy Bacon Cheeseburger", response.getOrderModel().getOrderMenuItemsList().get(1).getName());
        assertEquals(34, response.getOrderModel().getOrderMenuItemsList().get(1).getQuantity());
        assertEquals("Juicy Cheeseburger", response.getOrderModel().getOrderMenuItemsList().get(2).getName());
        assertEquals(12, response.getOrderModel().getOrderMenuItemsList().get(2).getQuantity());
        assertEquals("Sprite", response.getOrderModel().getOrderMenuItemsList().get(3).getName());
        assertEquals(78, response.getOrderModel().getOrderMenuItemsList().get(3).getQuantity());
    }

    @Test
    @Order(2)
    void updateOrderActivity_withUpdateOrderRequest_returnsUpdateOrderResponse() {
        // GIVEN
        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        JSONObject three = new JSONObject();
        JSONObject four = new JSONObject();

        one.put("itemNme", "Juicy Cheeseburger");
        one.put("itemQty", 1);
        two.put("itemNme", "Juicy Bacon Cheeseburger");
        two.put("itemQty", 3);
        three.put("itemNme", "3 Piece Chicken Nuggets");
        three.put("itemQty", 5);
        four.put("itemNme", "Sprite");
        four.put("itemQty", 7);

        JSONArray orderDescription = new JSONArray();
        orderDescription.add(one);
        orderDescription.add(two);
        orderDescription.add(three);
        orderDescription.add(four);

        UpdateOrderRequest updateOrderRequest =
                testDataProvider.createUpdateOrderRequest(generatedOrderId, orderDescription);

        // WHEN
        UpdateOrderResponse updateOrderResponse = testDataProvider.updateOrder(updateOrderRequest);

        // THEN
        assertEquals("3 Piece Chicken Nuggets", updateOrderResponse.getOrderModel().getOrderMenuItemsList().get(0).getName());
        assertEquals(5, updateOrderResponse.getOrderModel().getOrderMenuItemsList().get(0).getQuantity());
        assertEquals("Juicy Bacon Cheeseburger", updateOrderResponse.getOrderModel().getOrderMenuItemsList().get(1).getName());
        assertEquals(3, updateOrderResponse.getOrderModel().getOrderMenuItemsList().get(1).getQuantity());
        assertEquals("Juicy Cheeseburger", updateOrderResponse.getOrderModel().getOrderMenuItemsList().get(2).getName());
        assertEquals(1, updateOrderResponse.getOrderModel().getOrderMenuItemsList().get(2).getQuantity());
        assertEquals("Sprite", updateOrderResponse.getOrderModel().getOrderMenuItemsList().get(3).getName());
        assertEquals(7, updateOrderResponse.getOrderModel().getOrderMenuItemsList().get(3).getQuantity());
    }

    @Test
    @Order(3)
    void getOrderActivity_withGetOrderRequest_returnsGetOrderResponse() {
        // GIVEN
        GetOrderRequest getOrderRequest = testDataProvider.createGetOrderRequest(generatedOrderId);

        // WHEN
        GetOrderResponse getOrderResponse = testDataProvider.getOrder(getOrderRequest);

        // THEN
        assertEquals("3 Piece Chicken Nuggets", getOrderResponse.getOrderModel().getOrderMenuItemsList().get(0).getName());
        assertEquals(5, getOrderResponse.getOrderModel().getOrderMenuItemsList().get(0).getQuantity());
        assertEquals("Juicy Bacon Cheeseburger", getOrderResponse.getOrderModel().getOrderMenuItemsList().get(1).getName());
        assertEquals(3, getOrderResponse.getOrderModel().getOrderMenuItemsList().get(1).getQuantity());
        assertEquals("Juicy Cheeseburger", getOrderResponse.getOrderModel().getOrderMenuItemsList().get(2).getName());
        assertEquals(1, getOrderResponse.getOrderModel().getOrderMenuItemsList().get(2).getQuantity());
        assertEquals("Sprite", getOrderResponse.getOrderModel().getOrderMenuItemsList().get(3).getName());
        assertEquals(7, getOrderResponse.getOrderModel().getOrderMenuItemsList().get(3).getQuantity());
    }

    @Test
    @Order(4)
    void createSalesReportActivity_withCreateSalesReportRequest_returnsCreateSalesReportResponse() {
        // GIVEN
        CreateSalesReportRequest createSalesReportRequest = testDataProvider.createSalesReportRequest(generatedDate);

        // WHEN
        CreateSalesReportResponse createSalesReportResponse =
                testDataProvider.createSalesReport(createSalesReportRequest);

        // THEN
        OrderModel actual = createSalesReportResponse
                .getDateSales()
                .get(0);

        assertEquals("3 Piece Chicken Nuggets", actual.getOrderMenuItemsList().get(0).getName());
        assertEquals(5, actual.getOrderMenuItemsList().get(0).getQuantity());
        assertEquals("Juicy Bacon Cheeseburger", actual.getOrderMenuItemsList().get(1).getName());
        assertEquals(3, actual.getOrderMenuItemsList().get(1).getQuantity());
        assertEquals("Juicy Cheeseburger", actual.getOrderMenuItemsList().get(2).getName());
        assertEquals(1, actual.getOrderMenuItemsList().get(2).getQuantity());
        assertEquals("Sprite", actual.getOrderMenuItemsList().get(3).getName());
        assertEquals(7, actual.getOrderMenuItemsList().get(3).getQuantity());
    }

    @Test
    @Order(5)
    void deleteOrderActivity_withDeleteOrderRequest_returnsDeleteOrderResponse() {
        // GIVEN
        DeleteOrderRequest deleteOrderRequest = testDataProvider.createDeleteOrderRequest(generatedOrderId);

        // WHEN
        DeleteOrderResponse deleteOrderResponse = testDataProvider.deleteOrder(deleteOrderRequest);

        // THEN
        assertTrue(deleteOrderResponse.isDidItWork());
    }

    @Test
    @Order(6)
    void getOrderActivity_withGetOrderRequest_throwsOrderDoesNotExistException() {
        // GIVEN
        GetOrderRequest getOrderRequest = testDataProvider.createGetOrderRequest(generatedOrderId);

        // WHEN - THEN
        assertThrows(OrderDoesNotExistException.class, () -> testDataProvider.getOrder(getOrderRequest));
    }
}
