package data.types;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import converters.dynamodbtypeconverters.LocalDateConverter;
import converters.dynamodbtypeconverters.LocalTimeConverter;
import converters.dynamodbtypeconverters.MenuItemsQuantityMapConverter;

import exceptions.InvalidOrderException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Defines the characteristics of an Order object. An Order consists of a
 * String orderId, LocalDateTimes of the order place datetime, the order
 * process datetime, the order completed datetime, MenuItems mapped to a
 * Long quantity, and Long price in cents.
 *
 * Invariants: orderId must not be null, placeDateTime must not be null,
 * orderMenuItems must not be null nor empty, and totalPrice must not be
 * null or negative.
 * @author willi
 */
@DynamoDBTable(tableName = "OrderHistory")
public class Order {
    public static final String PLACED_DATE_TIME_INDEX = "PlacedDateTimeIndex";
    private String orderId;
    private LocalDate placedDate;
    private LocalTime placedTime;
    private Map<MenuItem, Integer> orderMenuItemsMap;
    private Integer totalPrice;

    public Order() {}

    /**
     * A private Order constructor used in conjunction with the inner
     * Builder class.
     * @param builder The parsed Builder to build an Order Object
     * @throws InvalidOrderException when an Order invariant is broken
     */
    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.placedDate = builder.placedDate;
        this.placedTime = builder.placedTime;
        this.orderMenuItemsMap = builder.orderMenuItemsMap;
        this.totalPrice = builder.totalPrice;
    }

    @DynamoDBHashKey(attributeName = "orderId")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @DynamoDBIndexHashKey(attributeName = "placed_date", globalSecondaryIndexName = PLACED_DATE_TIME_INDEX)
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    public LocalDate getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(LocalDate placedDate) {
        this.placedDate = placedDate;
    }

    @DynamoDBIndexRangeKey(attributeName = "placed_time", globalSecondaryIndexName = PLACED_DATE_TIME_INDEX)
    @DynamoDBTypeConverted(converter = LocalTimeConverter.class)
    public LocalTime getPlacedTime() {
        return placedTime;
    }

    public void setPlacedTime(LocalTime placedTime) {
        this.placedTime = placedTime;
    }

    @DynamoDBAttribute(attributeName = "menuItem_quantity_map")
    @DynamoDBTypeConverted(converter = MenuItemsQuantityMapConverter.class)
    public Map<MenuItem, Integer> getOrderMenuItemsMap() {
        return orderMenuItemsMap;
    }

    public void setOrderMenuItemsMap(Map<MenuItem, Integer> orderMenuItemsMap) {
        this.orderMenuItemsMap = new HashMap<>(orderMenuItemsMap);
    }

    @DynamoDBAttribute(attributeName = "total_price_cents")
    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId) &&
                placedDate.equals(order.placedDate) &&
                placedTime.equals(order.placedTime) &&
                orderMenuItemsMap.equals(order.orderMenuItemsMap) &&
                totalPrice.equals(order.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                orderId,
                placedDate,
                placedTime,
                orderMenuItemsMap,
                totalPrice
        );
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", placedDate=" + placedDate +
                ", placedTime=" + placedTime +
                ", orderMenuItemsMap=" + orderMenuItemsMap +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public static class Builder {
        private String orderId;
        private LocalDate placedDate;
        private LocalTime placedTime;
        private Map<MenuItem, Integer> orderMenuItemsMap;
        private Integer totalPrice;

        private Builder() {}

        public Builder withOrderId(String withOrderId) {
            this.orderId = withOrderId;
            return this;
        }

        public Builder withPlacedDate(LocalDate withPlacedDate) {
            this.placedDate = withPlacedDate;
            return this;
        }

        public Builder withPlacedTime(LocalTime withPlacedTime) {
            this.placedTime = withPlacedTime;
            return this;
        }

        public Builder withOrderMenuItems(Map<MenuItem, Integer> withOrderItemsMap) {
            this.orderMenuItemsMap = new HashMap<>(withOrderItemsMap);
            return this;
        }

        public Builder withTotalPrice(Integer withTotalPrice) {
            this.totalPrice = withTotalPrice;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
