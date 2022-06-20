package data.types.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Defines the characteristics of a MenuItem object. A MenuItem consists
 * of String name, Integer price in cents, and String description of the
 * MenuItem (such as ingredients)
 * @author willi
 */
public class OrderModel {
    private String orderId;
    private String placedDate;
    private String placedTime;
    private List<MenuItemModel> orderMenuItemsList;
    private int totalPrice;

    public OrderModel() {}

    private OrderModel(Builder builder) {
        this.orderId = builder.orderId;
        this.placedDate = builder.placedDate;
        this.placedTime = builder.placedTime;
        this.orderMenuItemsList = builder.orderMenuItemsList;
        this.totalPrice = builder.totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(String placedDateTime) {
        this.placedDate = placedDate;
    }

    public String getPlacedTime() {
        return placedTime;
    }

    public void setPlacedTime(String placedTime) {
        this.placedTime = placedTime;
    }

    public List<MenuItemModel> getOrderMenuItemsList() {
        return orderMenuItemsList;
    }

    public void setOrderMenuItemsList(List<MenuItemModel> orderMenuItemsList) {
        this.orderMenuItemsList = orderMenuItemsList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel that = (OrderModel) o;
        return totalPrice == that.totalPrice &&
                orderId.equals(that.orderId) &&
                placedDate.equals(that.placedDate) &&
                placedTime.equals(that.placedTime) &&
                orderMenuItemsList.equals(that.orderMenuItemsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                orderId,
                placedDate,
                placedTime,
                orderMenuItemsList,
                totalPrice
        );
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderId='" + orderId + '\'' +
                ", placedDate='" + placedDate + '\'' +
                ", placedTime='" + placedTime + '\'' +
                ", orderMenuItemsList=" + orderMenuItemsList +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public static class Builder {
        private String orderId;
        private String placedDate;
        private String placedTime;
        private List<MenuItemModel> orderMenuItemsList;
        private int totalPrice;

        public Builder withOrderId(String withOrderId) {
            this.orderId = withOrderId;
            return this;
        }

        public Builder withPlacedDate(String withPlaceDate) {
            this.placedDate = withPlaceDate;
            return this;
        }

        public Builder withPlacedTime(String withPlacedTime) {
            this.placedTime = withPlacedTime;
            return this;
        }

        public Builder withOrderMenuItemsList(List<MenuItemModel> withOrderMenuItemsList) {
            this.orderMenuItemsList = new ArrayList<>(withOrderMenuItemsList);
            return this;
        }

        public Builder withTotalPrice(int withTotalPrice) {
            this.totalPrice = withTotalPrice;
            return this;
        }

        public OrderModel build() {
            return new OrderModel(this);
        }
    }
}
