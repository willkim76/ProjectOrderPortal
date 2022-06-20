package data.requests;

import java.util.Objects;

public class GetOrderRequest {
    private String orderId;

    public GetOrderRequest() {

    }

    public GetOrderRequest(Builder builder) {
        this.orderId = builder.orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetOrderRequest that = (GetOrderRequest) o;
        return Objects.equals(getOrderId(), that.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId());
    }

    @Override
    public String toString() {
        return "GetOrderRequest{" +
                "id='" + orderId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String orderId;

        private Builder() {

        }

        public Builder withOrderId(String withOrderId) {
            this.orderId = withOrderId;
            return this;
        }
        public GetOrderRequest build() {
            return new GetOrderRequest(this);
        }
    }
}
