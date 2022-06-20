package providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import data.requests.GetOrderRequest;
import data.responses.GetOrderResponse;
import dependencies.DaggerServices;
import dependencies.Services;

public class GetOrderActivityProvider implements RequestHandler<GetOrderRequest, GetOrderResponse> {
    private Services services;

    public GetOrderActivityProvider() { }

    @Override
    public GetOrderResponse handleRequest(GetOrderRequest request, Context context) {
        return getServices().providesGetOrderActivity().handleRequest(request, context);
    }

    private Services getServices() {
        if (services == null) {
            services = DaggerServices.create();
        }
        return services;
    }
}
