package daos;

import data.types.MenuItem;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemDaoTest {
    private MenuItemDao menuItemDao;

    @BeforeEach
    void setup() {
        try {
            menuItemDao = new MenuItemDao(new JSONParser(), "menuItemsTest.json");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void getListOfMenuItems_withAJSONFileOfMenuItems_returnsAListOfMenuItems() {
        // GIVEN
        List<MenuItem> menuItemList = menuItemDao.getListOfMenuItems();

        MenuItem menuItem1Expected = MenuItem.builder()
                .withMenuItemId("MenuItem1Id")
                .withName("MenuItem1Name")
                .withPrice(100)
                .withDescription("MenuItem1Description")
                .build();

        MenuItem menuItem4Expected = MenuItem.builder()
                .withMenuItemId("MenuItem4Id")
                .withName("MenuItem4Name")
                .withPrice(400)
                .withDescription("MenuItem4Description")
                .build();

        // WHEN
        MenuItem menuItem1Actual = menuItemList.get(0);
        MenuItem menuItem4Actual = menuItemList.get(3);

        // THEN
        assertEquals(menuItem1Expected, menuItem1Actual);
        assertEquals(menuItem4Expected, menuItem4Actual);
    }

    @Test
    void getMapOfMenuItems_withAJSONFileOfMenuItems_returnsMenuItemsMappedToNameOfMenuItems() {
        // GIVEN
        Map<String, MenuItem> menuItemMap = menuItemDao.getMapOfMenuItems();

        // WHEN
        List<MenuItem> menuItemsList = new ArrayList<>();
        List<String> menuItemNamesList = new ArrayList<>();

        for (Map.Entry<String, MenuItem> entry : menuItemMap.entrySet()) {
            menuItemsList.add(entry.getValue());
            menuItemNamesList.add(entry.getKey());
        }

        // THEN
        for (int i = 0; i < menuItemMap.size(); i++) {
            assertEquals(menuItemsList.get(i).getName(), menuItemNamesList.get(i));
        }
    }
}
