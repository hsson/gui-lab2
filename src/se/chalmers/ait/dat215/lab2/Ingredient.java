/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.chalmers.ait.dat215.lab2;

/**
 * This class represents an ingredient in a recipe
 *
 * An ingredient is represented by a name, an amount and an unit. For example
 * if a recipe includes 700 g meat then 700 is the amount, g is the unit and meat
 * is the name
 *
 * @author Martin Hjulström
 */
public class Ingredient {
    private String name;
    private int amount;
    private String unit;

    /**
     * Initializes an ingredient according to the parameters
     *
     * @param name The name of the ingredient
     * @param amount The amount of units of this ingredient that is used in a recipe
     * @param unit The unit that is used in this recipe, for example a weight or a volume
     */
    public Ingredient(String name, int amount, String unit){
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    /**
     * Returns the amount of this ingredient
     * @return The amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of this recipe
     * @param amount The new amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns the name of this ingredient
     * @return The name of this ingredient
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this ingredient
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the unit used in this ingredient
     * @return The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit of this ingredient
     * @param unit The new unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingredient other = (Ingredient) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /**
     * Will print out this ingredient on the form amount + " " + unit + " " + name
     * @return A string representation of this ingredient
     */
    @Override
    public String toString() {
        return this.amount + " " + this.unit + " " + this.name;
    }

}
