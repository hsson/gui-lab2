package gui.lab2;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ComboBox<String> comboCuisine;
    public ComboBox<String> comboIngredient;
    public TextField textMaxPrice;
    public Slider sliderMaxTime;
    public ToggleButton toggleEasy;
    public ToggleButton toggleMedium;
    public ToggleButton toggleHard;
    public CheckBox checkMaxTime;
    public ListView<String> listSearchResult;

    private Model model = new Model();
    private int oldSliderVal;

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
        System.out.println(textMaxPrice.getText());
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
