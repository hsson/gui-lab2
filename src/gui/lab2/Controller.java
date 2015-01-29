package gui.lab2;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import se.chalmers.ait.dat215.lab2.Ingredient;
import se.chalmers.ait.dat215.lab2.Recipe;

import java.net.URL;
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
    private TextArea textIngredients;
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
    private ImageView imgVeg;
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
    }

    private void updateSearchResult() {
        listSearchResult.requestFocus();
        listSearchResult.getSelectionModel().select(0);
        listSearchResult.getFocusModel().focus(0);
        ObservableList<String> result = new ObservableListWrapper<String>(model.search());
        listSearchResult.setItems(result);
        updateDetailView(model.getRecipe(result.get(0)));
    }

    private void updateDetailView(Recipe r) {
        labelName.setText(r.getName());
        labelServings.setText(r.getServings() + " portioner");
        labelTime.setText(r.getTime() + " minuter");
        textDescription.setText(r.getDescription());
        textIngredients.setText("");
        for (Ingredient i : r.getIngredients()) {
            textIngredients.appendText(i.getAmount() + " " + i.getUnit() + "\t" + i.getName() + "\n");
        }

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

    private class MaxTimeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int val = newValue.intValue();
            if (val % 10 == 0 && val != oldSliderVal) {
                sliderMaxTimeOnSelected(val);
                oldSliderVal = val;
            }
        }
    }

    private void handleWrongMaxPriceInput() {
        System.out.println("maxPrice = fel");
        textMaxPrice.setText(oldMaxPriceString);
        textMaxPrice.positionCaret(textMaxPrice.getLength());
    }
}
