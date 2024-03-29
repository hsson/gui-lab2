package gui.lab2;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import se.chalmers.ait.dat215.lab2.Ingredient;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ComboBox<String> comboCuisine;
    @FXML
    private ComboBox<String> comboIngredient;
    @FXML
    private TextField textMaxPrice;
    @FXML
    private Slider sliderMaxTime;
    @FXML
    private ToggleButton toggleEasy;
    @FXML
    private ToggleButton toggleMedium;
    @FXML
    private ToggleButton toggleHard;
    @FXML
    private CheckBox checkMaxTime;
    @FXML
    private ListView<String> listSearchResult;
    @FXML
    private ListView<String> listIngredients;
    @FXML
    private TextArea textDescription;
    @FXML
    private Label labelName;
    @FXML
    private Label labelTime;
    @FXML
    private Label labelServings;
    @FXML
    private Label labelVeg;
    @FXML
    private Label labelCuisine;
    @FXML
    private Label labelPrice;
    @FXML
    private ImageView imgVeg;
    @FXML
    private ImageView imgFoodBanner;
    @FXML
    private ImageView imgRecipe;
    @FXML
    private ProgressBar progressDiff;

    private Model model = new Model();
    private int oldSliderVal;
    private int oldMaxPriceVal = 0;
    private String oldMaxPriceString = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboCuisine.setItems(new ObservableListWrapper<String>(model.getCuisineList()));
        comboIngredient.setItems(new ObservableListWrapper<String>(model.getIngredientList()));
        sliderMaxTime.valueProperty().addListener(new MaxTimeListener());

        listSearchResult.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                listSearchResultOnClicked();
            }
        });

        listSearchResult.setCellFactory(new Callback<ListView<String>,
                                    ListCell<String>>() {
                                @Override
                                public ListCell<String> call(ListView<String> list) {
                                    return new RecipeListCell();
                                }
                            }
        );

        updateSearchResult();
    }

    private void updateSearchResult() {
        ObservableList<String> result = new ObservableListWrapper<String>(model.search());
        listSearchResult.setItems(result);
        updateDetailView(model.getRecipe(result.get(0)));
        listSearchResult.scrollTo(0);
        listSearchResult.getSelectionModel().select(0);
        listSearchResult.getFocusModel().focus(0);
    }

    private void updateDetailView(Recipe r) {
        labelName.setText(r.getName());
        labelServings.setText(r.getServings() + " portioner");
        labelTime.setText(r.getTime() + " minuter");
        labelCuisine.setText(r.getCuisine());
        labelPrice.setText("(" + r.getPrice()+ " kr)");
        textDescription.setText(r.getDescription());
        List<String> ingredientList = new ArrayList<String>();
        for (Ingredient i : r.getIngredients()) {
            ingredientList.add(i.getAmount() + " " + i.getUnit() + " " + i.getName());
        }
        listIngredients.setItems(new ObservableListWrapper<String>(ingredientList));

        labelVeg.setVisible(r.getMainIngredient().equals("Vegetarisk"));
        imgVeg.setVisible(r.getMainIngredient().equals("Vegetarisk"));
        if (r.getDifficulty().equals("Lätt")) {
            progressDiff.setStyle("-fx-accent: darkseagreen;");
            progressDiff.setProgress(0.33);
        } else if (r.getDifficulty().equals("Mellan")) {
            progressDiff.setStyle("-fx-accent: #fab367;");
            progressDiff.setProgress(0.67);
        } else if (r.getDifficulty().equals("Svår")) {
            progressDiff.setStyle("-fx-accent: indianred; ");
            progressDiff.setProgress(1);
        }

        imgFoodBanner.setImage(new Image("/res/img/" + r.getCuisine() + ".jpg"));
        imgRecipe.setImage(new Image("/res/img/"+ r.getImage().getDescription()));
        double recipeImageRatio = imgRecipe.getImage().getHeight() / imgRecipe.getImage().getWidth();
        imgRecipe.setFitHeight(300);
        imgRecipe.setFitWidth(300 * recipeImageRatio);
    }

    private void listSearchResultOnClicked() {
        String selected = listSearchResult.getSelectionModel().getSelectedItem();
        Recipe recipe = model.getRecipe(selected);
        if (recipe != null) {
            updateDetailView(recipe);
        }
    }

    public void comboCuisineOnAction() {
        model.setCuisineOption(comboCuisine.getValue());
        System.out.println("cuisine = " + comboCuisine.getValue());
        updateSearchResult();
    }

    public void comboIngredientOnAction() {
        model.setIngredientOption(comboIngredient.getValue());
        System.out.println("ingredient = " + comboIngredient.getValue());
        updateSearchResult();
    }

    public void textMaxPriceOnAction() {
        String maxPrice = textMaxPrice.getText();
        if (maxPrice.trim().equals("") && !maxPrice.trim().equals(oldMaxPriceString)) {
            oldMaxPriceString = "";
            model.setMaxPriceOption(0);
            System.out.println("maxPrice = " + 0);
            updateSearchResult();
        } else if (maxPrice.matches("[0-9]+")) {
            try {
                if (oldMaxPriceVal != (Integer.parseInt(maxPrice))) {
                    model.setMaxPriceOption(Integer.parseInt(maxPrice));
                    System.out.println("maxPrice = " + maxPrice);
                    oldMaxPriceString = maxPrice;
                    oldMaxPriceVal = Integer.parseInt(maxPrice);
                    updateSearchResult();
                }
            } catch (NumberFormatException e) {
                handleWrongMaxPriceInput();
            }
        } else {
            handleWrongMaxPriceInput();
        }
    }

    public void toggleEasyOnAction() {
        model.setEasyOption(toggleEasy.isSelected());
        System.out.println("easy = " + toggleEasy.isSelected());
        updateSearchResult();
    }

    public void toggleMediumOnAction() {
        model.setMediumOption(toggleMedium.isSelected());
        System.out.println("medium = " + toggleMedium.isSelected());
        updateSearchResult();
    }

    public void toggleHardOnAction() {
        model.setHardOption(toggleHard.isSelected());
        System.out.println("hard = " + toggleHard.isSelected());
        updateSearchResult();
    }

    public void sliderMaxTimeOnSelected(int value) {
        if (checkMaxTime.isSelected()) {
            model.setMaxTimeOption(value);
            System.out.println("maxTime = " + value);
        } else {
            model.setMaxTimeOption(0);
            System.out.println("maxTime = " + 0);
        }

        updateSearchResult();
    }

    public void checkMaxTimeOnAction() {
        if (checkMaxTime.isSelected()) {
            sliderMaxTime.setDisable(false);
            System.out.println("maxTime = " + (int) sliderMaxTime.getValue());
            model.setMaxTimeOption((int) sliderMaxTime.getValue());
        } else {
            sliderMaxTime.setDisable(true);
            model.setMaxTimeOption(0);
            System.out.println("maxTime = " + 0);
        }

        updateSearchResult();
    }

    private void handleWrongMaxPriceInput() {
        System.out.println("maxPrice = fel");
        textMaxPrice.setText(oldMaxPriceString);
        textMaxPrice.positionCaret(textMaxPrice.getLength());
    }

    private class MaxTimeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int val = newValue.intValue();
            if (val % 10 == 0 && val != oldSliderVal && !sliderMaxTime.isValueChanging()) {
                sliderMaxTimeOnSelected(val);
                oldSliderVal = val;
            }
        }
    }

    private class RecipeListCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                HBox master = new HBox();
                VBox container = new VBox();
                HBox details = new HBox();

                Recipe r = model.getRecipe(item);
                Label name = new Label(r.getName());
                ProgressBar bar = new ProgressBar();
                if (r.getDifficulty().equals("Lätt")) {
                    bar.setStyle("-fx-accent: darkseagreen;");
                    bar.setProgress(0.33);
                } else if (r.getDifficulty().equals("Mellan")) {
                    bar.setStyle("-fx-accent: #fab367;");
                    bar.setProgress(0.67);
                } else if (r.getDifficulty().equals("Svår")) {
                    bar.setStyle("-fx-accent: indianred; ");
                    bar.setProgress(1);
                }
                ImageView timeIcon = new ImageView("/res/img/time.png");
                timeIcon.setFitHeight(32);
                timeIcon.setFitWidth(32);
                Label time = new Label(r.getTime() + " minuter");

                ImageView recipeImage = new ImageView("/res/img/" + r.getImage().getDescription());
                double recipeImageRatio = recipeImage.getImage().getHeight() / recipeImage.getImage().getWidth();
                recipeImage.setFitHeight(64);
                recipeImage.setFitWidth(64 * recipeImageRatio);

                details.setSpacing(16);
                details.getChildren().addAll(bar, timeIcon, time);
                container.getChildren().addAll(name, details);
                master.setSpacing(16);
                master.setAlignment(Pos.CENTER_LEFT);
                master.getChildren().addAll(recipeImage, container);
                setGraphic(master);
            }
        }
    }
}
