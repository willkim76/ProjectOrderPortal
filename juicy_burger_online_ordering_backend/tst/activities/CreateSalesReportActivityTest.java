package activities;

import activites.CreateSalesReportActivity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import converters.ModelConverter;

import daos.OrderDao;
import data.requests.CreateSalesReportRequest;
import data.responses.CreateSalesReportResponse;
import data.types.MenuItem;
import data.types.Order;
import data.types.models.OrderModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import utilities.OrderUtilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;

public class CreateSalesReportActivityTest {
    private CreateSalesReportActivity createSalesReportActivity;

    @Mock
    OrderDao orderDao;

    @Mock
    Context context;

    @Mock
    LambdaLogger lambdaLogger;

    @BeforeEach
    void setup() {
        openMocks(this);
        createSalesReportActivity = new CreateSalesReportActivity(orderDao);
    }

    @Test
    void handleRequest_withRequestDatePopulatedWithOrders_returnsCreateSalesReportResponse() {
        // GIVEN
        LocalDateTime placedDateTime = LocalDateTime.now();

        Map<MenuItem, Integer> menuItemIntegerMap = Map.of(
                MenuItem.builder()
                        .withName("TestMenuItem1")
                        .withPrice(10)
                        .withDescription("TestMenuItem1Description")
                        .build(),
                1,
                MenuItem.builder()
                        .withName("TestMenuItem2")
                        .withPrice(5)
                        .withDescription("TestMenuItem2Description")
                        .build(),
                2
        );

        List<Order> ordersList = List.of(
                Order.builder()
                        .withOrderId("TestOrderId1")
                        .withPlacedDate(placedDateTime.toLocalDate())
                        .withPlacedTime(placedDateTime.toLocalTime())
                        .withOrderMenuItems(menuItemIntegerMap)
                        .withTotalPrice(OrderUtilities.calculateTotalPrice(menuItemIntegerMap))
                        .build()
                ,
                Order.builder()
                        .withOrderId("TestOrderID2")
                        .withPlacedDate(placedDateTime.toLocalDate())
                        .withPlacedTime(placedDateTime.toLocalTime())
                        .withOrderMenuItems(menuItemIntegerMap)
                        .withTotalPrice(OrderUtilities.calculateTotalPrice(menuItemIntegerMap))
                        .build()
        );

        List<OrderModel> orderModelList = List.of(
                OrderModel.builder()
                        .withOrderId(ordersList.get(0).getOrderId())
                        .withPlacedDate(ordersList.get(0).getPlacedDate().toString())
                        .withPlacedTime(ordersList.get(0).getPlacedTime().toString())
                        .withOrderMenuItemsList(ModelConverter.orderMenuItemMapConverter(menuItemIntegerMap))
                        .withTotalPrice(OrderUtilities.calculateTotalPrice(menuItemIntegerMap))
                        .build(),
                OrderModel.builder()
                        .withOrderId(ordersList.get(1).getOrderId())
                        .withPlacedDate(ordersList.get(1).getPlacedDate().toString())
                        .withPlacedTime(ordersList.get(1).getPlacedTime().toString())
                        .withOrderMenuItemsList(ModelConverter.orderMenuItemMapConverter(menuItemIntegerMap))
                        .withTotalPrice(OrderUtilities.calculateTotalPrice(menuItemIntegerMap))
                        .build()
        );

        CreateSalesReportRequest createSalesReportRequest = CreateSalesReportRequest.builder()
                .withSalesForDate(placedDateTime.toLocalDate().toString())
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(orderDao.getOrdersByPlacedDate(placedDateTime.toLocalDate().toString())).thenReturn(ordersList);

        // WHEN
        CreateSalesReportResponse createSalesReportResponse =
                createSalesReportActivity.handleRequest(createSalesReportRequest, context);

        // THEN
        assertEquals(orderModelList, createSalesReportResponse.getDateSales());

    }

    @Test
    void handleRequest_withRequestDateUnpopulatedWithOrders_returnsAnEmptyListOfOrderModels() {
        // GIVEN
        LocalDateTime placedDateTime = LocalDateTime.now();

        CreateSalesReportRequest createSalesReportRequest = CreateSalesReportRequest.builder()
                .withSalesForDate(placedDateTime.toLocalDate().toString())
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(orderDao.getOrdersByPlacedDate(placedDateTime.toLocalDate().toString())).thenReturn(new ArrayList<>());

        // WHEN
        CreateSalesReportResponse createSalesReportResponse =
                createSalesReportActivity.handleRequest(createSalesReportRequest, context);

        // THEN
        assertTrue(createSalesReportResponse.getDateSales().isEmpty());
    }

    @Test
    void handleRequest_withRequestInvalidDateFormat_throwsIllegalArgumentException() {
        // GIVEN
        String illegalDateFormat = "2022-14-69";

        CreateSalesReportRequest createSalesReportRequest = CreateSalesReportRequest.builder()
                .withSalesForDate(illegalDateFormat)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(orderDao.getOrdersByPlacedDate(illegalDateFormat)).thenThrow(IllegalArgumentException.class);

        // WHEN - THEN
        assertThrows(IllegalArgumentException.class,
                () -> createSalesReportActivity.handleRequest(createSalesReportRequest, context)
        );
    }
}
