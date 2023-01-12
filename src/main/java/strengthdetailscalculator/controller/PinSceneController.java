package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import strengthdetailscalculator.service.PinService;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PinSceneController extends Controller {

    PinService pinService = new PinService();

    @FXML
    private TextField outerDiameter;

    @FXML
    private TextField internalDiameter;

    @FXML
    private TextField numberShearSection;

    public void printDoc(ActionEvent event) throws IOException {
        List<TextField> numericalPinData = new ArrayList<>(List.of(outerDiameter, internalDiameter, numberShearSection));
        Response response = pinService.write(getTextDataDetail(), getNumericalDataDetail(), numericalPinData);
        if (response.getResponseStatus() == ResponseStatus.SUCCESS) {
            switchFinishScene(event);
        } else {
            alertHandler.getErrorInputData(response.getDescription());
        }
    }

    private void switchFinishScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_FINISH_SCENE, NAME_FINISH_SCENE, event);
    }
}
