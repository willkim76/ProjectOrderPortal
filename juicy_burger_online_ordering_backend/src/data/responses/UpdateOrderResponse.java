package data.responses;

import data.types.Order;
import data.types.models.OrderModel;

public class UpdateOrderResponse {
    private OrderModel orderModel;

    public UpdateOrderResponse() { }

    private UpdateOrderResponse(Builder builder) {
        this.orderModel = builder.orderModel;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "UpdateOrderResponse{" +
                "orderModel=" + orderModel +
                '}';
    }

    public static class Builder {
        private OrderModel orderModel;

        public Builder withOrderModel(OrderModel withOrderModel) {
            this.orderModel = withOrderModel;
            return this;
        }

        public UpdateOrderResponse build() {
            return new UpdateOrderResponse(this);
        }
    }
}
