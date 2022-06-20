package converters.dynamodbtypeconverters;

import data.types.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemsMapQuantityConverterTest {
    private MenuItemsQuantityMapConverter menuItemsQuantityMapConverter;

    @BeforeEach
    void setup() {
        menuItemsQuantityMapConverter = new MenuItemsQuantityMapConverter();
    }

    @Test
    void convert_withAMapOfMenuItemsAndQuantity_returnsAStringRepresentation() {
        // GIVEN
        MenuItem test1 = MenuItem.builder()
                .withName("MenuItemTestName_1")
                .withPrice(-100)
                .withDescription("MenuItemTest1Description_1")
                .build();

        MenuItem test2 = MenuItem.builder()
                .withName("MenuItemTestName_2")
                .withPrice(-200)
                .withDescription("MenuItemTest1Description_2")
                .build();

        Map<MenuItem, Integer> orderMenuItemsMap = new HashMap<>();
        orderMenuItemsMap.put(test1, 1);
        orderMenuItemsMap.put(test2, 2);

        // WHEN
        String dynamoDbString = menuItemsQuantityMapConverter.convert(orderMenuItemsMap);

        // THEN
        assertTrue(dynamoDbString.contains(test1.getName()));
        assertTrue(dynamoDbString.contains(test1.getPrice().toString()));
        assertTrue(dynamoDbString.contains(test1.getDescription()));
        assertTrue(dynamoDbString.contains(test2.getName()));
        assertTrue(dynamoDbString.contains(test2.getPrice().toString()));
        assertTrue(dynamoDbString.contains(test2.getDescription()));
    }

    @Test
    void unconvert_withAValidStringRepresentation_returnsAMapOfMenuItemsAndQuantity() {
        // GIVEN
        MenuItem test1 = MenuItem.builder()
                .withMenuItemId("MenuItemTestId_1")
                .withName("MenuItemTestName_1")
                .withPrice(-100)
                .withDescription("MenuItemTest1Description_1")
                .build();

        MenuItem test2 = MenuItem.builder()
                .withMenuItemId("MenuItemTestId_2")
                .withName("MenuItemTestName_2")
                .withPrice(-200)
                .withDescription("MenuItemTest1Description_2")
                .build();

        String dynamoDbString = "MenuItemTestId_1 : MenuItemTestName_1 : -100 : MenuItemTest1Description_1 : 1 &" +
                " MenuItemTestId_2 : MenuItemTestName_2 : -200 : MenuItemTest1Description_2 : 2";

        // WHEN
        Map<MenuItem, Integer> result = menuItemsQuantityMapConverter.unconvert(dynamoDbString);

        // THEN
        assertTrue(result.containsKey(test1));
        assertTrue(result.containsValue(1));
        assertTrue(result.containsKey(test2));
        assertTrue(result.containsValue(2));
    }
}
