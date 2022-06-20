package integreation.providers;

import activites.*;
import dependencies.DaggerServices;
import dependencies.Services;

public class TestServiceProvider {
    private static final Services DAGGER = DaggerServices.create();

    private TestServiceProvider() {}

    public static PlaceOrderActivity providePlaceOrderActivity() {
        return DAGGER.providesPlaceOrderActivity();
    }

    public static DeleteOrderActivity provideDeleteOrderActivity() {
        return DAGGER.providesDeleteOrderActivity();
    }

    public static UpdateOrderActivity provideUpdateOrderActivity() {
        return DAGGER.providesUpdateOrderActivity();
    }

    public static GetOrderActivity provideGetOrderActivity() { return DAGGER.providesGetOrderActivity(); }

    public static CreateSalesReportActivity provideCreateSalesReportActivity() {
        return DAGGER.providesCreateSalesReportActivity();
    }
}
