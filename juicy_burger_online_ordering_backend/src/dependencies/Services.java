package dependencies;

import activites.*;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Services is the interface for Dagger dependency injection
 */
@Singleton
@Component(modules = {DynamoDBModule.class, JSONParserModule.class})
public interface Services {

    PlaceOrderActivity providesPlaceOrderActivity();

    UpdateOrderActivity providesUpdateOrderActivity();

    DeleteOrderActivity providesDeleteOrderActivity();

    GetOrderActivity providesGetOrderActivity();

    CreateSalesReportActivity providesCreateSalesReportActivity();
}
