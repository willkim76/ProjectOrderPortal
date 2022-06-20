package daos;

import data.types.MenuItem;

import exceptions.OrderException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.inject.Inject;

import java.io.FileReader;
import java.util.*;

/**
 * MenuItemDao defines the characteristics and behavior of a readonly
 * data access object for MenuItem objects. MenuItem attributes must
 * NOT contain the characters ':' nor '&'.
 * @author willi
 */
public class MenuItemDao {
    private final String fileURL;
    private final JSONParser jsonParser;

    @Inject
    public MenuItemDao(JSONParser jsonParser) {
        this(jsonParser, "menuItems.json");
    }

    /**
     * An argument constructor for testing
     * @param fileName Name of the file with extension type
     * @throws NullPointerException if the file does not exist in resources
     */
    public MenuItemDao(JSONParser jsonParser, String fileName) {
        this.jsonParser = jsonParser;
        this.fileURL = Objects.requireNonNull(getClass()
                .getClassLoader()
                .getResource(fileName))
                .getFile();
    }

    /**
     * Generates and returns a List of MenuItems.
     * @return List of MenuItems
     */
    public List<MenuItem> getListOfMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileURL)) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                Long price = (Long) jsonObject.get("price");
                menuItems.add(
                        MenuItem.builder()
                                .withMenuItemId((String) jsonObject.get("itemId"))
                                .withName((String) jsonObject.get("name"))
                                .withPrice(price.intValue())
                                .withDescription((String) jsonObject.get("description"))
                                .build()
                );
            }
        } catch (Exception e) {
            throw new OrderException("Unable to parse internal file!", e);
        }
        return menuItems;
    }

    /**
     * Generates and returns a MenuItems mapped to the String name of the MenuItem
     * @return Map of String to MenuItems
     */
    public Map<String, MenuItem> getMapOfMenuItems() {
        Map<String, MenuItem> menuItems = new HashMap<>();

        try (FileReader fileReader = new FileReader(fileURL)) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                Long price = (Long) jsonObject.get("price");
                menuItems.put(
                        (String) jsonObject.get("name"),
                        MenuItem.builder()
                                .withName((String) jsonObject.get("name"))
                                .withPrice(Math.toIntExact(price))
                                .withDescription((String) jsonObject.get("description"))
                                .build()
                );
            }
        } catch (Exception e) {
            throw new OrderException("Unable to parse internal file!", e);
        }
        return menuItems;
    }
}
