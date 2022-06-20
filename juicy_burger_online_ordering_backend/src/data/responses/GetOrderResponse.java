package data.responses;

import data.types.Order;
import data.types.models.OrderModel;

public class GetOrderResponse {
    private OrderModel orderModel;

    private GetOrderResponse(Builder builder) {
        this.orderModel = builder.orderModel;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderModel orderModel;

        public Builder withOrderModel(OrderModel orderModel) {
            this.orderModel = orderModel;
            return this;
        }
        public GetOrderResponse build() {return new GetOrderResponse(this);}
    }
}
