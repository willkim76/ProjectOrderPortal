package integreation.providers;

import activites.*;

import com.amazonaws.services.lambda.runtime.Context;
import data.requests.*;
import data.responses.*;
import integreation.contexts.TestContext;
import org.json.simple.JSONArray;

import java.util.Map;

public class TestDataProvider {
    private final PlaceOrderActivity placeOrderActivity =
            TestServiceProvider.providePlaceOrderActivity();

    private final DeleteOrderActivity deleteOrderActivity =
            TestServiceProvider.provideDeleteOrderActivity();

    private final UpdateOrderActivity updateOrderActivity =
            TestServiceProvider.provideUpdateOrderActivity();

    private final GetOrderActivity getOrderActivity =
            TestServiceProvider.provideGetOrderActivity();

    private final CreateSalesReportActivity createSalesReportActivity =
            TestServiceProvider.provideCreateSalesReportActivity();

    private final Context testContext = new TestContext();

    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest) {
        return placeOrderActivity.handleRequest(placeOrderRequest, testContext);
    }

    public PlaceOrderRequest createPlaceOrderRequest(JSONArray orderDescription) {
        return PlaceOrderRequest.builder()
                .withOrderItems(orderDescription)
                .build();
    }

    public UpdateOrderResponse updateOrder(UpdateOrderRequest updateOrderRequest) {
        return updateOrderActivity.handleRequest(updateOrderRequest, testContext);
    }


    public UpdateOrderRequest createUpdateOrderRequest(String orderId, JSONArray orderDescription) {
        return UpdateOrderRequest.builder()
                .withOrderId(orderId)
                .withOrderItems(orderDescription)
                .build();
    }

    public GetOrderResponse getOrder(GetOrderRequest getOrderRequest) {
        return getOrderActivity.handleRequest(getOrderRequest, testContext);
    }

    public GetOrderRequest createGetOrderRequest(String orderId) {
        return GetOrderRequest.builder()
                .withOrderId(orderId)
                .build();
    }

    public DeleteOrderResponse deleteOrder(DeleteOrderRequest deleteOrderRequest) {
        return deleteOrderActivity.handleRequest(deleteOrderRequest, testContext);
    }

    public DeleteOrderRequest createDeleteOrderRequest(String orderId) {
        return DeleteOrderRequest.builder()
                .withOrderId(orderId)
                .build();
    }

    public CreateSalesReportResponse createSalesReport(CreateSalesReportRequest createSalesReportRequest) {
        return createSalesReportActivity.handleRequest(createSalesReportRequest, testContext);
    }

    public CreateSalesReportRequest createSalesReportRequest(String date) {
        return CreateSalesReportRequest.builder()
                .withSalesForDate(date)
                .build();
    }
}
