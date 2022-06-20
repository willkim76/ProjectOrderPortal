package utilities;

import data.types.MenuItem;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * A utility class that provides helper methods for the Order object and any
 * of its respective activities.
 */
public class OrderUtilities {
    public static double SALES_TAX_RATE = 0.095;
    public static String TIME_ZONE = "America/Los_Angeles";

    private OrderUtilities() {}

    /**
     * An OrderUtilities method that generates a String UUID for the Order
     * partition key
     * @return String a randomly generated id
     */
    public static String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    /**
     * An OrderUtilities method that generates a ZonedDateTime with
     * American/Los Angeles time zone as default
     * @return ZonedDateTime
     */
    public static ZonedDateTime generateZonedDateTime() {
        return generateZonedDateTime(TIME_ZONE);
    }

    /**
     * An OrderUtilities method that generates a ZonedDateTime with
     * a specific time zone
     * @param timeZone String specified time zone
     * @return ZonedDateTime
     */
    public static ZonedDateTime generateZonedDateTime(String timeZone) {
        return ZonedDateTime.now(ZoneId.of(timeZone));
    }

    /**
     * An OrderUtilities method that generates a String representation of the
     * current LocalDateTime
     * @return String current local date time
     */
    public static String generateDateTimeNow() {
        return LocalDateTime.now().toString();
    }

    /**
     * An OrderUtilities method that generates a String representation of a
     * future LocalDateTime. At least one parameter must be non-zero.
     * @param minutes int the minute offset
     * @param hours int the hours offset
     * @param days int the days offset
     * @return String future local date time
     * @throws IllegalArgumentException if all arguments are zero
     */
    public static String generateFutureDateTime(int minutes, int hours, int days) {
        if (minutes == 0 && hours == 0 && days == 0) {
            throw new IllegalArgumentException("Not a valid future datetime");
        }
        return LocalDateTime.now()
                .plusMinutes(minutes)
                .plusHours(hours)
                .plusDays(days)
                .toString();
    }

    /**
     * An OrderUtilities method that calculates the total price of a
     * Map of MenuItems and Long quantity within an Order.
     * @param orderMenuItems Map of MenuItems and Long quantity
     * @return Long the total price of orderMenuItems
     */
    public static Integer calculateTotalPrice(Map<MenuItem, Integer> orderMenuItems) {
        return calculateTotalPrice(orderMenuItems, 0);
    }

    //Do not use. The Tax calculation is incorrect. Tax calculation is currently Out of Scope
    /**
     * An overloaded OrderUtilities method that calculates the total price of a
     * Map of MenuItems and Long quantity within an Order including tax.
     * @param orderMenuItems Map of MenuItems and Long quantity
     * @param taxRate Long the effective tax rate in decimal
     * @return Long the total price of orderMenuItems
     */
    public static Integer calculateTotalPrice(Map<MenuItem, Integer> orderMenuItems, Integer taxRate) {
        int calculatedPrice = 0;
        for (Map.Entry<MenuItem, Integer> entry : orderMenuItems.entrySet()) {
            calculatedPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return calculatedPrice * (1 + taxRate);
    }
}
