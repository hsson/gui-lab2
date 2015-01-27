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
        System.out.println(comboCuisine.getValue());
    }

    public void comboIngredientOnAction() {
        System.out.println(comboIngredient.getValue());
    }

    public void textMaxPriceOnAction() {
        if (textMaxPrice.getText().trim().equals("")) {
            //TODO: if empty set null in model
        } else if (textMaxPrice.getText().matches("[0-9]+")) {
            System.out.println(textMaxPrice.getText());
            oldMaxPriceVal = textMaxPrice.getText();
        } else {
            textMaxPrice.setText(oldMaxPriceVal);
            textMaxPrice.positionCaret(textMaxPrice.getLength());
        }
    }

    public void toggleDiffOnAction(ActionEvent event) {
        ToggleButton tb = (ToggleButton) event.getSource();
        System.out.println(tb.getText());
    }

    public void sliderMaxTimeOnSelected(int value) {
        if (checkMaxTime.isSelected()) {
            System.out.println(value);
        }
    }

    public void checkMaxTimeOnAction() {
        if (checkMaxTime.isSelected()) {
            sliderMaxTime.setDisable(false);
            //Convert to int
            System.out.println(sliderMaxTime.getValue());
        } else {
            sliderMaxTime.setDisable(true);
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
