/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.chalmers.ait.dat215.lab2;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;

/**
 * This class represents a recipe
 *
 * @author Martin Hjulström
 */
public class Recipe {
    private String name;
    private int servings;
    private String difficulty;
    private int time;
    private String cuisine;
    private int price;
    private String mainIngredient;
    private String description;
    private ImageIcon image;
    private int match;
    private List<Ingredient> ingredients;

    /**
     *
     * Initializes a new instance of this class according to the parameters
     *
     * @param name The name of the recipe
     * @param servings The number of serings that this recipe is intended for
     * @param difficulty The difficulty level of this recipe should be any of the following: "Lätt", "Mellan", or "Svår"
     * @param time The estimated time for this recipe
     * @param cuisine The cuisine that this recipe belongs to
     * @param price The estimated price of this recipe per serving
     * @param mainIngredient The main ingredient used in this recipe
     * @param description The instructions for this recipe
     * @param image The path to an image of this dish
     * @param ingredients A list of ingredients used in this recipe
     */
    public Recipe(String name, int servings, String difficulty, int time, String cuisine, int price, String mainIngredient, String description, ImageIcon image, List<Ingredient> ingredients) {
        this.name = name;
        this.servings = servings;
        this.difficulty = difficulty;
        this.time = time;
        this.cuisine = cuisine;
        this.price = price;
        this.mainIngredient = mainIngredient;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
    }

    /**
     * Returns the name of the recipe
     *
     * @return The recipe's name
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the recipe
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the cuisine of which this recipe belongs to.
     *
     * @return The cuisine
     */
    public String getCuisine() {
        return cuisine;
    }

    /**
     * Sets a new cuisine
     *
     * @param cuisine The new cuisine
     */
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    /**
     * Returns the description of this recipe
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a new description
     *
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the difficulty level of this recipe
     *
     * @return The difficulty level of this recipe
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets a new difficulty level to this recipe
     *
     * @param difficulty The new difficulty level
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Returns the image of this recipe. The image returned has its original dimensions
     *
     * @see getImage(Dimension d)
     * @see getImage(int width, int height)
     *
     * @return The image of this recipe
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * Returns the image of this recipe with the dimensions given by d.
     *
     * @param d The dimensions of the returned image
     * @return The image of this recipe
     */
    public ImageIcon getImage(Dimension d){
        return new ImageIcon(getScaledImage(getImage().getImage(), d.width, d.height));
    }

    /**
     * Returns the image of this recipe with the dimensions given specified by width and height.
     *
     * @param width The width of the returned image
     * @param height The height of the returned image
     * @return The image of this recipe
     */

    public ImageIcon getImage(int width, int height){
        return new ImageIcon(getScaledImage(getImage().getImage(), width, height));
    }

    /**
     * Sets the image of this recipe
     * @param image The new image
     */
    public void setImage(ImageIcon image) {
        this.image = image;
    }

    /**
     * Returns the recipe's list of ingredients
     * @return The list of ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the recipe's list of ingredients
     * @param ingredients The new list of ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Returns the recipe's main ingredient
     * @return The recipes's main ingredient
     */
    public String getMainIngredient() {
        return mainIngredient;
    }

    /**
     * Sets the main ingredient of this recipe
     * @param mainIngredient The new main ingredient
     */
    public void setMainIngredient(String mainIngredient) {
        this.mainIngredient = mainIngredient;
    }

    /**
     * Returns the match of this recipe. The match indicates how well this recipe
     * matches a given search filter. The match corresponds to how many percent
     * of the search criterions that the recipe matches.
     * @return The amtch
     */
    public int getMatch() {
        return match;
    }

    /**
     * Sets the match of this recipe
     * @param match The new match
     */
    public void setMatch(int match) {
        this.match = match;
    }

    /**
     * Returns the number of servings that this recipe is intended for
     *
     * @return The number of servings
     */
    public int getServings() {
        return servings;
    }

    /**
     * Sets the number of servings of this recipe
     * @param servings The new number of servings
     */
    public void setServings(int servings) {
        this.servings = servings;
    }

    /**
     * Returns the estimated price per serving of this recipe
     * @return The estimated price per serving of this recipe
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets a new price per serving of this recipe
     * @param price The price per serving
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Returns the estimated cooking time of this recipe
     * @return The estimated cooking time of this recipe
     */
    public int getTime() {
        return time;
    }

    /**
     * Sets the new cooking time of this recipe
     * @param time The new cooking time
     */
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }



}
