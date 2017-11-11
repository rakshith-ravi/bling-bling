package co.updn.blingbling.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<Category> categories = new ArrayList<>();
    public static List<Resource> resources = new ArrayList<>();
    public static List<Item> cart = new ArrayList<>();

    public static class Category {
        public String id;
        public String name;
        public String imageURL;
        public List<Item> items = new ArrayList<>();

        @Override
        public String toString() {
            return name.toUpperCase();
        }
    }

    public static class Item {
        public String id;
        public String name;
        public String[] imageURLs;

        public float goldPrice;
        public float makingCharges;
        public float vat;

        public float goldCharges;
        public String goldColor;
        public float goldPurity;

        public String diamondShape;
        public float diamondWeight;
        public String diamondColor;
        public String diamondClarity;
    }

    public static abstract class Resource {
        public String name;
        public String type;
    }

    public static class ArrayResource extends Resource {
        public List<String> values = new ArrayList<>();

        public int indexOf(String value) {
            for(int i = 0; i < values.size(); i++) {
                if(values.get(i).equals(value))
                    return i;
            }
            return -1;
        }
    }

    @Nullable
    public static Category findCategoryByName(String name) {
        for (Category category: categories) {
            if(category.name.equals(name))
                return category;
        }
        return null;
    }

    @Nullable
    public static Resource findResourceByName(String name) {
        for(Resource resource : resources) {
            if(resource.name.equals(name))
                return resource;
        }
        return null;
    }

    public static void loadResourcesFromJson(JsonObject jsonObject) {
        resources.clear();
        int size = jsonObject.get("size").getAsInt();
        for(int i = 0; i < size; i++) {
            JsonObject resource = jsonObject.get("resource" + i).getAsJsonObject();
            if(resource.get("type").getAsString().equals("array")) {
                ArrayResource arrayResource = new ArrayResource();
                arrayResource.name = resource.get("name").getAsString();
                arrayResource.type = resource.get("type").getAsString();
                int items = resource.get("size").getAsInt();
                for(int j = 0; j < items; j++) {
                    arrayResource.values.add(resource.get("item" + j).getAsString());
                }
                resources.add(arrayResource);
            } else {
                // Add here if anything else exists
                Log.d(Constants.TAG, "Unknown resource type: " + resource.get("type").getAsString());
            }
        }
    }

    public static void loadCategoriesFromJson(JsonObject jsonObject) {
        categories.clear();
        for(int i = 0; i < jsonObject.get("numcategories").getAsInt(); i++) {
            String id = jsonObject.get("category" + i).getAsString();
            JsonObject object = jsonObject.get(id).getAsJsonObject();
            Category category = new Category();
            category.name = object.get("name").getAsString();
            category.id = object.get("id").getAsString();
            category.imageURL = object.get("imageurl").getAsString();

            if(object.has("items")) {
                JsonObject itemsObject = object.get("items").getAsJsonObject();
                for (int j = 0; j < itemsObject.get("size").getAsInt(); j++) {
                    JsonObject itemObject = itemsObject.get("item" + j).getAsJsonObject();
                    Item item = new Item();
                    item.id = itemObject.get("id").getAsString();
                    item.name = itemObject.get("name").getAsString();
                    item.imageURLs = new String[itemObject.get("images").getAsInt()];
                    for (int k = 0; k < item.imageURLs.length; k++) {
                        item.imageURLs[k] = itemObject.get("image" + k).getAsString();
                    }

                    item.goldCharges = itemObject.get("g_charges").getAsFloat();
                    item.goldColor = itemObject.get("g_color").getAsString();
                    item.goldPurity = itemObject.get("g_purity").getAsFloat();

                    item.diamondShape = itemObject.get("d_shape").getAsString();
                    item.diamondWeight = itemObject.get("d_weight").getAsFloat();
                    item.diamondColor = itemObject.get("d_color").getAsString();
                    item.diamondClarity = itemObject.get("d_clarity").getAsString();
                    category.items.add(item);
                }
            }
            categories.add(category);
        }
    }

    public static List<Category> getDefaultCategories() {
        List<Category> categories = new ArrayList<>();

        Category pendants = new Category();
        pendants.name = "Pendants";
        pendants.imageURL = "android.resource://co.updn.blingbling/drawable/pendants";
        categories.add(pendants);

        Category earrings = new Category();
        earrings.name = "Earrings";
        earrings.imageURL = "android.resource://co.updn.blingbling/drawable/earrings";
        categories.add(earrings);

        Category bracelets = new Category();
        bracelets.name = "Bracelets";
        bracelets.imageURL = "android.resource://co.updn.blingbling/drawable/bracelets";
        categories.add(bracelets);

        Category rings = new Category();
        rings.name = "Rings";
        rings.imageURL = "android.resource://co.updn.blingbling/drawable/rings";
        categories.add(rings);

        Category bangle = new Category();
        bangle.name = "Bangles";
        bangle.imageURL = "android.resource://co.updn.blingbling/drawable/bangles";
        categories.add(bangle);

        return categories;
    }
}
