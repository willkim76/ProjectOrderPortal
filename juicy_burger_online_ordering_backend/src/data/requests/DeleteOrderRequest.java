package data.requests;

import java.util.Objects;

public class DeleteOrderRequest {
    private String orderId;

    public DeleteOrderRequest() {

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
        DeleteOrderRequest that = (DeleteOrderRequest) o;
        return Objects.equals(getOrderId(), that.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId());
    }

    @Override
    public String toString() {
        return "DeleteOrderRequest{" +
                "orderId='" + orderId + '\'' +
                '}';
    }

    private DeleteOrderRequest(Builder builder) {
        this.orderId = builder.orderId;
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

        public DeleteOrderRequest build() {
            return new DeleteOrderRequest(this);
        }

    }
}
