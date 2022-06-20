package comprators.types;

import data.types.Order;

import java.util.Comparator;

public class OrderDateTimeComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getPlacedDate().equals(o2.getPlacedDate())) {
            return o1.getPlacedTime().compareTo(o2.getPlacedTime());
        }
        return o1.getPlacedDate().compareTo(o2.getPlacedDate());
    }
}
