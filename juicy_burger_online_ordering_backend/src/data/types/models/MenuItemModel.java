package data.types.models;

import java.util.Comparator;
import java.util.Objects;

/**
 * Defines the characteristics of a MenuItemModel object. A MenuItemModel
 * consists of String name, Integer price in cents, String description,
 * and int quantity of MenuItem.
 * @author willi
 */
public class MenuItemModel {
    private String name;
    private int price;
    private String description;
    private int quantity;

    public MenuItemModel() {}

    private MenuItemModel(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.description = builder.description;
        this.quantity = builder.quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemModel that = (MenuItemModel) o;
        return price == that.price &&
                quantity == that.quantity &&
                name.equals(that.name) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, description, quantity);
    }

    @Override
    public String toString() {
        return "MenuItemModel{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public static class Builder {
        private String name;
        private int price;
        private String description;
        private int quantity;

        public Builder withName(String withName) {
            this.name = withName;
            return this;
        }

        public Builder withPrice(int withPrice) {
            this.price = withPrice;
            return this;
        }

        public Builder withDescription(String withDescription) {
            this.description = withDescription;
            return this;
        }

        public Builder withQuantity(int withQuantity) {
            this.quantity = withQuantity;
            return this;
        }

        public MenuItemModel build() {
            return new MenuItemModel(this);
        }
    }
}
