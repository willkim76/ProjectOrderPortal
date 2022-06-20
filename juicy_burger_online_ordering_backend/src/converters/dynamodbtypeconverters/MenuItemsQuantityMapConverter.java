package converters.dynamodbtypeconverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import data.types.MenuItem;

import java.util.HashMap;
import java.util.Map;

/**
 * A converter class for the arbitrary data type Map of MenuItems and
 * Long quantity for DynamoDB parsing
 * @author willi
 */
public class MenuItemsQuantityMapConverter implements DynamoDBTypeConverter<String, Map<MenuItem, Integer>> {

    /**
     * Converts the Map of MenuItems and Long quantity Java datatype
     * into a String for DynamoDB
     * @param object Map of MenuItems mapped to a Long quantity
     * @return String representation of the map
     */
    @Override
    public String convert(Map<MenuItem, Integer> object) {
        StringBuilder orderMenuItemsString = new StringBuilder();
        try {
            int index = 0;
            for (Map.Entry<MenuItem, Integer> entry : object.entrySet()) {
                orderMenuItemsString.append(String.format(
                        "%s : %s : %s : %s : %s",
                        entry.getKey().getMenuItemId(),
                        entry.getKey().getName(),
                        entry.getKey().getPrice(),
                        entry.getKey().getDescription(),
                        entry.getValue())
                );
                orderMenuItemsString.append(index++ < object.size() - 1 ? " & " : "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderMenuItemsString.toString();
    }

    /**
     * Converts the DynamoDB String representation of Map of MenuItems
     * and Long quantity into a Java Map data type
     * @param object representation of the map
     * @return Map of MenuItems mapped to a Long quantity
     */
    @Override
    public Map<MenuItem, Integer> unconvert(String object) {
        Map<MenuItem, Integer> menuItemsQuantityMap = new HashMap<>();
        try {
            for (String menuItemAttributes : object.split("&")) {
                menuItemsQuantityMap.put(
                        MenuItem.builder()
                                .withMenuItemId(menuItemAttributes.split(":")[0].trim())
                                .withName(menuItemAttributes.split(":")[1].trim())
                                .withPrice(Integer.valueOf(menuItemAttributes.split(":")[2].trim()))
                                .withDescription(menuItemAttributes.split(":")[3].trim())
                                .build(),
                        Integer.valueOf(menuItemAttributes.split(":")[4].trim())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuItemsQuantityMap;
    }
}

