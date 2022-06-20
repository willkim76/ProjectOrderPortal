package data.requests;

import data.types.models.MenuItemModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class PlaceOrderRequest {
    private JSONArray orderDescription;

    public PlaceOrderRequest() {}

    private PlaceOrderRequest(Builder builder) {
        this.orderDescription = builder.orderDescription;
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
        return "PlaceOrderRequest{" +
                "orderDescription=" + orderDescription +
                '}';
    }

    public static class Builder {
        private JSONArray orderDescription;

        public Builder withOrderItems(JSONArray withOrderDescription) {
            this.orderDescription = withOrderDescription;
            return this;
        }

        public PlaceOrderRequest build() {
            return new PlaceOrderRequest(this);
        }
    }
}
