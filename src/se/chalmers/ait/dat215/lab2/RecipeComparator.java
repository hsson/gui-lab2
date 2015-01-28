/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.chalmers.ait.dat215.lab2;

import java.util.Comparator;

/**
 *
 * @author id
 */
public class RecipeComparator implements Comparator<Recipe>{

    public int compare(Recipe t, Recipe t1) {
        if (t.getMatch() == t1.getMatch()){
            return 1;
        } else if ((t.getMatch() - t1.getMatch()) < 0) {
            return 1;
        }
        return -1;
    }

}
