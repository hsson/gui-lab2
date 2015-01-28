/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.ait.dat215.lab2;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import javax.swing.ImageIcon;

/**
 * This class is responsible for managing a set of recipes and match them against 
 * search filters.
 *
 * @author Martin Hjulström
 */
public class RecipeDatabase {

    private Set<Recipe> recipes;
    private static RecipeDatabase sharedInstance;

    /**
     * This class implements the singelton pattern. This method is called to get
     * the shared instance.
     * @return The shared recipe database.
     */
    public static RecipeDatabase getSharedInstance () {
        if (sharedInstance == null)
            sharedInstance = new RecipeDatabase();
        return sharedInstance;
    }

    /**
     * Clone is not supported
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private RecipeDatabase() {
        File file = new File("recipes.txt");
        FileReader reader;
        BufferedReader dis = null;
        try {
            reader = new FileReader(file);
            dis = new BufferedReader(reader);
            recipes = new HashSet<Recipe>();
            String line = null;
            String name = null;
            int servings = 0;
            int time = 0;
            int price = 0;
            String cuisine = null;
            String difficulty = null;
            String mainIngredient = null;
            String description = null;
            String imagePath = null;
            List<Ingredient> ingredients = null;
            while ((line = dis.readLine()) != null) {
                if (line.equals("#name")) {
                    name = dis.readLine();
                } else if (line.equals("#servings")) {
                    servings = Integer.parseInt(dis.readLine());
                } else if (line.equals("#difficulty")) {
                    difficulty = dis.readLine();
                } else if (line.equals("#time")) {
                    time = Integer.parseInt(dis.readLine());
                } else if (line.equals("#cuisine")) {
                    cuisine = dis.readLine();
                } else if (line.equals("#price")) {
                    price = Integer.parseInt(dis.readLine());
                } else if (line.equals("#image")) {
                    imagePath = dis.readLine();
                } else if (line.equals("#mainIngredient")) {
                    mainIngredient = dis.readLine();
                } else if (line.equals("#ingredients")) {
                    int amount = 0;
                    String iName;
                    String unit;
                    Set<Ingredient> temp = new HashSet<Ingredient>();
                    ingredients = new ArrayList<Ingredient>();
                    while (!(line = dis.readLine()).equals("#end")) {
                        amount = Integer.parseInt(line);
                        unit = dis.readLine();
                        iName = dis.readLine();
                        Ingredient i = new Ingredient(iName, amount, unit);
                        if (!temp.contains(i)){
                            ingredients.add(i);
                            temp.add(i);
                        }
                    }
                } else if (line.equals("#description")) {
                    description = dis.readLine();
                    recipes.add(new Recipe(name, servings, difficulty, time, cuisine, price, mainIngredient, description, new ImageIcon(imagePath), ingredients));
                }
            }
            dis.close();
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Matches the filter against the recipes in the database. This method will return a list
     * with the size of the lowest of numberOfResults and the database's size. The recipes are
     * ordered in descending order.
     * @param filter The filter to match against the database
     * @param numberOfResults The number of recipes to return.
     * @return The recipes that best matches the filter
     */
    public List<Recipe> search(SearchFilter filter, int numberOfResults) {
        List<Recipe> resultSet;
        //SortedSet<Recipe> sortedSet = new ConcurrentSkipListSet<Recipe>(new RecipeComparator());
        // Fixed the code so it works....:
        Set<Recipe> sortedSet = new TreeSet<Recipe>(new RecipeComparator());
        Iterator<Recipe> it = recipes.iterator();
        while (it.hasNext()) {
            Recipe r = it.next();
            r.setMatch(filter.matchAgainstRecipe(r));
           //System.out.println(r.getName() + " | " + r.getMatch() + " | " + r.getCuisine());
            sortedSet.add(r);
        }

        it = sortedSet.iterator();
        resultSet = new ArrayList<Recipe>();
        for (int n = 0; n < numberOfResults && it.hasNext(); n++) {
            Recipe r = it.next();
            resultSet.add(r);
        }
        return resultSet;

    }

    /**
     * Matches the filter with the recipes in the database and returns as many
     * recipes as there are in the database in descending order based on recipe's
     * match variable.
     * @param filter The search filter to match against the database
     * @return The entire database ordered descending based on how well they matches the filter
     */
    public List<Recipe> search(SearchFilter filter){
        return search(filter, recipes.size());
    }

    /**
     * Saves a search filter to the disc. The search filter will be saved to the
     * file: searchFilter.getName()+".rcp". It's therefor important to set the
     * name of the searchFilter before calling this method. The recipes are saved
     * in the application's root folder.
     * @param searchFilter The search filter to save
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveSearchFilter(SearchFilter searchFilter) throws FileNotFoundException, IOException{
        FileOutputStream fout = new FileOutputStream(searchFilter.getName()+".rcp");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(searchFilter);
        oos.close();
    }

    /**
     * Returns a list of all search filters saved in the application's root
     * folder.
     *
     * @return The saved search filters.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<SearchFilter> getSavedSearchFilters() throws FileNotFoundException, IOException, ClassNotFoundException {
        File root = new File(".");
        List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
        File[] files = root.listFiles();
        for (int n = 0; n < files.length; n++){
            if (files[n].getName().endsWith(".rcp")){
                FileInputStream fin = new FileInputStream(files[n].getName());
                ObjectInputStream ois = new ObjectInputStream(fin);
                searchFilters.add((SearchFilter)ois.readObject());
                ois.close();
            }
        }
        return searchFilters;
    }

    /**
     * Deletes a saved search filter with the name searchFilter.
     * @param searchFilter The name of the search filter to delete
     * @return Returns true if the search filter was removed.
     */
    public boolean deleteSavedSearchFilters(String searchFilter){
        File file = new File(searchFilter+".rcp");
        return file.delete();
    }
}
