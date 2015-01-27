package gui.lab2;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<String> cuisineList = new ArrayList<String>();
    private List<String> ingredientList = new ArrayList<String>();

    public Model() {
        initCuisineList();
        initIngredientList();
    }

    private void initCuisineList() {
        cuisineList.add("Alla");
        cuisineList.add("Sverige");
        cuisineList.add("Grekland");
        cuisineList.add("Indien");
        cuisineList.add("Asien");
        cuisineList.add("Afrika");
        cuisineList.add("Frankrike");
    }

    private void initIngredientList() {
        ingredientList.add("Alla");
        ingredientList.add("Kött");
        ingredientList.add("Fisk");
        ingredientList.add("Kyckling");
        ingredientList.add("Vegetarisk");
    }

    public List<String> getCuisineList() {
        return new ArrayList<String>(cuisineList);
    }

    public List<String> getIngredientList() {
        return new ArrayList<String>(ingredientList);
    }
}
