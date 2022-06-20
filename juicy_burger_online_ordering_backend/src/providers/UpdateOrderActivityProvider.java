package providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import data.requests.UpdateOrderRequest;
import data.responses.UpdateOrderResponse;

import dependencies.DaggerServices;
import dependencies.Services;

public class UpdateOrderActivityProvider implements RequestHandler<UpdateOrderRequest, UpdateOrderResponse> {
    private Services services;

    public UpdateOrderActivityProvider() {}

    @Override
    public UpdateOrderResponse handleRequest(UpdateOrderRequest request, Context context) {
        return getServices().providesUpdateOrderActivity().handleRequest(request, context);
    }

    private Services getServices() {
        if (services == null) {
            services = DaggerServices.create();
        }
        return services;
    }
}
