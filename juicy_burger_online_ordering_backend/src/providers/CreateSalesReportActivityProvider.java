package providers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import data.requests.CreateSalesReportRequest;
import data.responses.CreateSalesReportResponse;
import dependencies.DaggerServices;
import dependencies.Services;

public class CreateSalesReportActivityProvider implements RequestHandler<CreateSalesReportRequest, CreateSalesReportResponse> {
    private Services services;

    public CreateSalesReportActivityProvider() {}

    @Override
    public CreateSalesReportResponse handleRequest(CreateSalesReportRequest request, Context context) {
        return getServices().providesCreateSalesReportActivity().handleRequest(request, context);
    }

    private Services getServices() {
        if (services == null) {
            services = DaggerServices.create();
        }
        return services;
    }

}
