package data.requests;

import org.json.simple.JSONArray;

public class UpdateOrderRequest {
    private String orderId;
    private JSONArray orderDescription;

    public UpdateOrderRequest() {}

    private UpdateOrderRequest(Builder builder) {
        this.orderId = builder.orderId;
        this.orderDescription = builder.orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public JSONArray getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(JSONArray orderDescription) {
        this.orderDescription = orderDescription;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "UpdateOrderRequest{" +
                "orderId='" + orderId + '\'' +
                ", orderDescription=" + orderDescription +
                '}';
    }

    public static class Builder {
        private String orderId;
        private JSONArray orderItems;

        public Builder withOrderId(String withOrderId) {
            this.orderId = withOrderId;
            return this;
        }

        public Builder withOrderItems(JSONArray withOrderDescription) {
            this.orderItems = withOrderDescription;
            return this;
        }

        public UpdateOrderRequest build() {
            return new UpdateOrderRequest(this);
        }
    }
}
