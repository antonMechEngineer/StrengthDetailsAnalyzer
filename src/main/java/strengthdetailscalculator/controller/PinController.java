package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import strengthdetailscalculator.service.PinService;
import strengthdetailscalculator.utils.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PinController extends DetailController {

    private PinService pinService = new PinService();

    public static final Integer INDEX_OUTER_DIAMETER = 0;
    public static final Integer INDEX_INTERNAL_DIAMETER = 1;
    public static final Integer INDEX_NUMBER_SHEAR_SECTION = 2;

    @FXML
    private TextField outerDiameter;

    @FXML
    private TextField internalDiameter;

    @FXML
    private ComboBox<String> numberShearSection;

    @FXML
    protected Button printButton;

    public void printDoc(ActionEvent event) throws IOException {
        Response response = pinService.write(getDetailData(), getNumericalDataDetail(), getData());
        processResponse(response, event);
    }

    private void switchFinishScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_FINISH_SCENE, NAME_FINISH_SCENE, event);
    }

    protected List<String> getData() {
        ArrayList<String> pinData = new ArrayList<>();
        pinData.add(INDEX_OUTER_DIAMETER, outerDiameter.getText());
        pinData.add(INDEX_INTERNAL_DIAMETER, internalDiameter.getText());
        pinData.add(INDEX_NUMBER_SHEAR_SECTION, numberShearSection.getValue());
        return pinData;
    }

    @FXML
    protected void getNumberShearSection() {
        numberShearSection.getItems().addAll("1", "2", "3", "4");
        if (numberShearSection.getValue() != null) {
            printButton.setDisable(false);
        }
    }
}
