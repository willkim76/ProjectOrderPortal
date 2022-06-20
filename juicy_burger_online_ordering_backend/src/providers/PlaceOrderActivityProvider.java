package providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import data.requests.PlaceOrderRequest;
import data.responses.PlaceOrderResponse;

import dependencies.DaggerServices;
import dependencies.Services;

public class PlaceOrderActivityProvider implements RequestHandler<PlaceOrderRequest, PlaceOrderResponse> {
    private Services services;

    public PlaceOrderActivityProvider() {}

    @Override
    public PlaceOrderResponse handleRequest(PlaceOrderRequest request, Context context) {
        return getServices().providesPlaceOrderActivity().handleRequest(request, context);
    }

    private Services getServices() {
        if (services == null) {
            services = DaggerServices.create();
        }
        return services;
    }
}
