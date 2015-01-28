/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.chalmers.ait.dat215.lab2;

/**
 * Represents a search filter that can be matched against a recipe. The search filter
 * matches recipes against its active criterions. Difficulty, cuisine and
 * mainIngredient are considered to be active if a non null value are assigend to them.
 * MaxTime and maxPrice are considered to be acgtive if there values are greater than 0.
 * A recipe matches difficulty, cuisine and mainIngredient if its corresponding parameter is
 * equal to the search filter's parameter, it matches maxTime and maxPrice if its corresponding value is
 * equal to or below the search filter's value.
 *
 * @author Martin Hjulström
 */
public class SearchFilter implements java.io.Serializable{

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SearchFilter other = (SearchFilter) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    private String difficulty;
    private int maxTime;
    private String cuisine;
    private int maxPrice;
    private String mainIngredient;
    private String name;

    /**
     * Returns the cuisine of this search filter
     * @return The cuisine
     */
    public String getCuisine() {
        return cuisine;
    }

    /**
     * Set the cuisine of this search filter
     * @param cuisine The new cuisine
     */
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    /**
     * Returns the difficulty level of this search filter
     * @returnv The difficulty level
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of this recipe
     * @param difficulty The new difficulty level
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    /**
     * Returns the search filter's main ingredient
     * @return The search filter's main ingredient
     */
    public String getMainIngredient() {
        return mainIngredient;
    }

    /**
     * Sets the main ingredient of the search filter
     * @param mainIngredient the new main ingredient
     */
    public void setMainIngredient(String mainIngredient) {
        this.mainIngredient = mainIngredient;
    }


    /**
     * Returns the max price of the search filter
     * @return The max price of the search filter
     */
    public int getMaxPrice() {
        return maxPrice;
    }


    /**
     * Sets the new max price of the recipe
     * @param maxPrice The new max price
     */
    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Returns the search filter's max time
     * @return The search filter's max time
     */
    public int getMaxTime() {
        return maxTime;
    }


    /**
     * Set a new max time of the search filter
     * @param maxTime The new max time
     */
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }


    /**
     * Returns the name of the search filter
     * @return The name of the search filter
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the search filter. The name is only relevant to set if the
     * search filter should be saved, since the name is used to distinct between the
     * different saved filters.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Initializes a new search filter and sets its name
     * @param difficulty The search filter's difficulty level
     * @param maxTime The search filter's max time
     * @param cuisine The search filter's cuisine
     * @param maxPrice The search filter's max price
     * @param mainIngredient The search filter's main ingredient
     * @param name The search filter's name
     */
    public SearchFilter(String difficulty, int maxTime, String cuisine, int maxPrice, String mainIngredient, String name) {
        this.difficulty = difficulty;
        this.maxTime = maxTime;
        this.cuisine = cuisine;
        this.maxPrice = maxPrice;
        this.mainIngredient = mainIngredient;
        this.name = name;
    }

    /**
     * Initializes a new search filter and sets its name
     * @param difficulty The search filter's difficulty level
     * @param maxTime The search filter's max time
     * @param cuisine The search filter's cuisine
     * @param maxPrice The search filter's max price
     * @param mainIngredient The search filter's main ingredient
     */

    public SearchFilter(String difficulty, int maxTime, String cuisine, int maxPrice, String mainIngredient) {
        this.difficulty = difficulty;
        this.maxTime = maxTime;
        this.cuisine = cuisine;
        this.maxPrice = maxPrice;
        this.mainIngredient = mainIngredient;
    }

    /**
     * Matches recipe against this search filters, and returns a percentage (0-100)
     * that incicates how many active criterions the recipe matches.
     * @param recipe The recipe to match against
     * @return A persentage (0-100), indicating how many criterions recipe matches
     */
    public int matchAgainstRecipe(Recipe recipe) {
        int numberOfFilters = 0;
        int numberOfMatches = 0;
        if (difficulty != null){
            numberOfFilters++;
            if (difficulty.equals(recipe.getDifficulty())){
                numberOfMatches++;
            }
        }
        if (maxTime > 0){
            numberOfFilters++;
            if (maxTime >= recipe.getTime()){
                numberOfMatches++;
            }
        }

        if (cuisine != null){
            numberOfFilters++;
            if (cuisine.equals(recipe.getCuisine())){
                numberOfMatches++;
            }
        }

        if (maxPrice > 0){
            numberOfFilters++;
            if (maxPrice >= recipe.getPrice()){
                numberOfMatches++;
            }
        }

        if (mainIngredient != null){
            numberOfFilters++;
            if (mainIngredient.equals(recipe.getMainIngredient())){
                numberOfMatches++;
            }
        }
        return (int)((double)numberOfMatches/(double)numberOfFilters*100);
    }
}
