package data.responses;

import data.types.models.OrderModel;

public class PlaceOrderResponse {
    private OrderModel orderModel;

    public PlaceOrderResponse() {}

    private PlaceOrderResponse(Builder builder) {
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
        return "PlaceOrderResponse{" +
                "orderModel=" + orderModel +
                '}';
    }

    public static class Builder {
        private OrderModel orderModel;

        public Builder withOrderModel(OrderModel withOrderModel) {
            this.orderModel = withOrderModel;
            return this;
        }

        public PlaceOrderResponse build() {
            return new PlaceOrderResponse(this);
        }
    }


}
