package model;

import java.util.HashMap;

/**
 * Created by mara on 1/2/15.
 */
public class Category {

    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static HashMap<Integer, String> DefaultCategories() {
        HashMap<Integer, String> result = new HashMap<Integer, String>();
        result.put(1, "Electricity");
        result.put(2, "Water");
        result.put(3, "Gas");
        return result;
    }

}
