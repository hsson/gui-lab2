package gui.lab2;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ComboBox<String> comboCuisine;
    public ComboBox<String> comboIngredient;
    public TextField textMaxPrice;
    public Slider sliderMaxTime;
    public ToggleButton toggleEasy;
    public ToggleButton toggleMedium;
    public ToggleButton toggleHard;

    private Model model = new Model();
    private int oldSliderVal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboCuisine.setItems(new ObservableListWrapper<String>(model.getCuisineList()));
        comboIngredient.setItems(new ObservableListWrapper<String>(model.getIngredientList()));

        sliderMaxTime.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                int val = new_val.intValue();
                if (val % 10 == 0 && val != oldSliderVal) {
                    sliderMaxTimeOnSelected(val);
                    oldSliderVal = val;
                }
            }
        });
    }

    public void comboCuisineOnAction() {
        System.out.println(comboCuisine.getValue());
    }

    public void comboIngredientOnAction() {
        System.out.println(comboIngredient.getValue());
    }

    public void textMaxPriceOnAction() {
        System.out.println(textMaxPrice.getText());
    }

    public void toggleDiffOnAction(ActionEvent event) {
        ToggleButton tb = (ToggleButton) event.getSource();
        System.out.println(tb.getText());
    }

    public void sliderMaxTimeOnSelected(int value) {
        System.out.println(value);
    }
}
