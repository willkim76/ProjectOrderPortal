package comprators.models;

import data.types.models.MenuItemModel;

import java.util.Comparator;

public class MenuItemModelNameComparator implements Comparator<MenuItemModel> {

    @Override
    public int compare(MenuItemModel o1, MenuItemModel o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
