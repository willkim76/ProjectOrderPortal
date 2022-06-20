package data.types;

import java.util.Objects;

/**
 * Defines the characteristics of a MenuItem object. A MenuItem consists
 * of String name, Integer price in cents, and String description of the
 * MenuItem (such as ingredients)
 * @author willi
 */
public class MenuItem {
    private String menuItemId;
    private String name;
    private Integer price;
    private String description;

    private MenuItem(Builder builder) {
        this.menuItemId = builder.menuItemId;
        this.name = builder.name;
        this.price = builder.price;
        this.description = builder.description;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "menuItemId='" + menuItemId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return menuItemId.equals(menuItem.menuItemId) &&
                name.equals(menuItem.name) &&
                price.equals(menuItem.price) &&
                description.equals(menuItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItemId, name, price, description);
    }

    public static class Builder {
        private String menuItemId;
        private String name;
        private Integer price;
        private String description;

        public Builder withMenuItemId(String builderMenuItemId) {
            this.menuItemId = builderMenuItemId;
            return this;
        }

        public Builder withName(String builderName) {
            this.name = builderName;
            return this;
        }

        public Builder withPrice(Integer builderPrice) {
            this.price = builderPrice;
            return this;
        }

        public Builder withDescription(String builderDescription) {
            this.description = builderDescription;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(this);
        }
    }
}
