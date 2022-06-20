package comparators.models;

import comprators.models.MenuItemModelNameComparator;
import data.types.models.MenuItemModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemModelNameComparatorTest {
    private MenuItemModelNameComparator menuItemModelNameComparator;

    @BeforeEach
    void setup() {
        menuItemModelNameComparator = new MenuItemModelNameComparator();
    }

    @Test
    void compare_menuItemModelWithIdenticalName_returnsZero() {
        // GIVEN
        MenuItemModel menuItemModel_1 = MenuItemModel.builder()
                .withName("Test1")
                .build();

        MenuItemModel menuItemModel_2 = MenuItemModel.builder()
                .withName("Test1")
                .build();

        // WHEN
        int actual = menuItemModelNameComparator.compare(menuItemModel_1, menuItemModel_2);

        // THEN
        assertEquals(0, actual);
    }

    @Test
    void compare_menuItemModelWithLexicographicalGreaterName_returnsIntGreaterThanZero() {
        // GIVEN
        MenuItemModel menuItemModel_1 = MenuItemModel.builder()
                .withName("ZTest1")
                .build();

        MenuItemModel menuItemModel_2 = MenuItemModel.builder()
                .withName("ATest1")
                .build();

        // WHEN
        int actual = menuItemModelNameComparator.compare(menuItemModel_1, menuItemModel_2);

        // THEN
        assertTrue(actual > 0);
    }

    @Test
    void compare_menuItemModelWithLexicographicalSmallerName_returnsIntLessThanZero() {
        // GIVEN
        MenuItemModel menuItemModel_1 = MenuItemModel.builder()
                .withName("ATest1")
                .build();

        MenuItemModel menuItemModel_2 = MenuItemModel.builder()
                .withName("ZTest1")
                .build();

        // WHEN
        int actual = menuItemModelNameComparator.compare(menuItemModel_1, menuItemModel_2);

        // THEN
        assertTrue(actual < 0);
    }
}
