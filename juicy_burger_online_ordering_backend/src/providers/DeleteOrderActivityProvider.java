package providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import data.requests.DeleteOrderRequest;
import data.responses.DeleteOrderResponse;

import dependencies.DaggerServices;
import dependencies.Services;

public class DeleteOrderActivityProvider implements RequestHandler<DeleteOrderRequest, DeleteOrderResponse> {
    private Services services;

    public DeleteOrderActivityProvider() {}

    @Override
    public DeleteOrderResponse handleRequest(DeleteOrderRequest request, Context context) {
        return getServices().providesDeleteOrderActivity().handleRequest(request, context);
    }

    private Services getServices() {
        if (services == null) {
            services = DaggerServices.create();
        }
        return services;
    }
}
