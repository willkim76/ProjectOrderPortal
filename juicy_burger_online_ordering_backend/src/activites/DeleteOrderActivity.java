package activites;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import daos.OrderDao;
import data.requests.DeleteOrderRequest;
import data.responses.DeleteOrderResponse;
import data.types.Order;
import exceptions.OrderDoesNotExistException;

import javax.inject.Inject;

/**
 * Defines the behavior of removing an Order. Accepts a DeleteOrderRequest and returns
 * a DeleteOrderResponse. Updates the persistent layer by removing an existing Order.
 */
public class DeleteOrderActivity implements RequestHandler<DeleteOrderRequest, DeleteOrderResponse> {
    private OrderDao orderDao;

    @Inject
    public DeleteOrderActivity(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public DeleteOrderResponse handleRequest(DeleteOrderRequest request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log(request.toString());

        try {
            orderDao.deleteOrder(request.getOrderId());
        } catch (OrderDoesNotExistException e) {
            return DeleteOrderResponse.builder().willItWork(false).build();
        }
        return DeleteOrderResponse.builder().willItWork(true).build();
    }
}
