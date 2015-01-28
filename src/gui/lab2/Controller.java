package gui.lab2;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private Model model = new Model();
    private int oldSliderVal;
    private String oldMaxPriceVal = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboCuisine.setItems(new ObservableListWrapper<String>(model.getCuisineList()));
        comboIngredient.setItems(new ObservableListWrapper<String>(model.getIngredientList()));
        sliderMaxTime.valueProperty().addListener(new MaxTimeListener());

        // List dummy data
        List<String> test = model.getCuisineList();
        test.addAll(model.getIngredientList());
        test.addAll(test);

        listSearchResult.setItems(new ObservableListWrapper<String>(test));
    }

    public void comboCuisineOnAction() {
        model.setCuisineOption(comboCuisine.getValue());
        System.out.println("cuisine = " + comboCuisine.getValue());
    }

    public void comboIngredientOnAction() {
        model.setIngredientOption(comboIngredient.getValue());
        System.out.println("ingredient = " + comboIngredient.getValue());
    }

    public void textMaxPriceOnAction() {
        if (textMaxPrice.getText().trim().equals("")) {
            oldMaxPriceVal = "";
            model.setMaxPriceOption(0);
            System.out.println("maxPrice = " + 0);
        } else if (textMaxPrice.getText().matches("[0-9]+")) {
            model.setMaxPriceOption(Integer.parseInt(textMaxPrice.getText()));
            System.out.println("maxPrice = " + textMaxPrice.getText());
            oldMaxPriceVal = textMaxPrice.getText();
        } else {
            System.out.println("maxPrice = fel");
            textMaxPrice.setText(oldMaxPriceVal);
            textMaxPrice.positionCaret(textMaxPrice.getLength());
        }
    }

    public void toggleEasyOnAction() {
        model.setEasyOption(toggleEasy.isSelected());
        System.out.println("easy = " + toggleEasy.isSelected());
    }

    public void toggleMediumOnAction() {
        model.setMediumOption(toggleMedium.isSelected());
        System.out.println("medium = " + toggleMedium.isSelected());
    }

    public void toggleHardOnAction() {
        model.setHardOption(toggleHard.isSelected());
        System.out.println("hard = " + toggleHard.isSelected());
    }

    public void sliderMaxTimeOnSelected(int value) {
        if (checkMaxTime.isSelected()) {
            model.setMaxTimeOption(value);
            System.out.println("maxTime = " + value);
        } else {
            model.setMaxTimeOption(0);
            System.out.println("maxTime = " + 0);
        }
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
}
