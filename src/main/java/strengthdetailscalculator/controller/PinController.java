package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
        Response response = pinService.write(getTextDataDetail(), getNumericalDataDetail(), getData());
        processResponse(response, event);
    }

    private void switchFinishScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_FINISH_SCENE, NAME_FINISH_SCENE, event);
    }

    protected List<Parent> getData() {
        ArrayList<Parent> pinData = new ArrayList<>();
        pinData.add(INDEX_OUTER_DIAMETER, outerDiameter);
        pinData.add(INDEX_INTERNAL_DIAMETER, internalDiameter);
        pinData.add(INDEX_NUMBER_SHEAR_SECTION, numberShearSection);
        return pinData;
    }

    @FXML
    protected void getNumberShearSection(){
        numberShearSection.getItems().addAll("1","2");
        if (numberShearSection.getValue() != null) {
            printButton.setDisable(false);
        }
    }

}
