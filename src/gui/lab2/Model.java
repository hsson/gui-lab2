package gui.lab2;

import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeComparator;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.*;

public class Model {
    private List<String> cuisineList = new ArrayList<String>();
    private List<String> ingredientList = new ArrayList<String>();

    private String cuisineOption;
    private String ingredientOption;
    private int maxPriceOption;
    private int maxTimeOption;
    private boolean easyOption;
    private boolean mediumOption;
    private boolean hardOption;

    private List<Recipe> allRecipes = new ArrayList<Recipe>();

    RecipeDatabase db = RecipeDatabase.getSharedInstance();


    public Model() {
        initCuisineList();
        initIngredientList();
    }

    public List<String> search() {
        SortedSet<Recipe> res = new TreeSet<>(new RecipeComparator());

        if (easyOption) {
            res.addAll(db.search(new SearchFilter("Lätt", maxTimeOption, cuisineOption, maxPriceOption, ingredientOption)));
        }
        if (mediumOption) {
            res.addAll(db.search(new SearchFilter("Mellan", maxTimeOption, cuisineOption, maxPriceOption, ingredientOption)));
        }
        if (hardOption) {
            res.addAll(db.search(new SearchFilter("Svår", maxTimeOption, cuisineOption, maxPriceOption, ingredientOption)));
        }
        if (!easyOption && !mediumOption && !hardOption) {
            res.addAll(db.search(new SearchFilter(null, maxTimeOption, cuisineOption, maxPriceOption, ingredientOption)));
        }

        allRecipes.addAll(res);

        List<String> finalRes = new ArrayList<String>();
        for (Recipe r : res) {
            finalRes.add(r.getName());
        }

        return finalRes;
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

    public void setCuisineOption(String cuisineOption) {
        this.cuisineOption = cuisineOption;
    }

    public void setIngredientOption(String ingredientOption) {
        this.ingredientOption = ingredientOption;
    }

    public void setMaxPriceOption(int maxPriceOption) {
        this.maxPriceOption = maxPriceOption;
    }

    public void setMaxTimeOption(int maxTimeOption) {
        this.maxTimeOption = maxTimeOption;
    }

    public void setEasyOption(boolean easyOption) {
        this.easyOption = easyOption;
    }

    public void setMediumOption(boolean mediumOption) {
        this.mediumOption = mediumOption;
    }

    public void setHardOption(boolean hardOption) {
        this.hardOption = hardOption;
    }

    public List<String> getCuisineList() {
        return new ArrayList<String>(cuisineList);
    }

    public List<String> getIngredientList() {
        return new ArrayList<String>(ingredientList);
    }
}
