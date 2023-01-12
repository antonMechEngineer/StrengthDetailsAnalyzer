package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import strengthdetailscalculator.service.ScrewService;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrewSceneController extends Controller {

    ScrewService screwService = new ScrewService();

    @FXML
    private TextField mainD;

    @FXML
    private TextField threadPitch;

    @FXML
    private TextField height;

    @FXML
    private CheckBox isTrapezoidal;

    public void printDoc(ActionEvent event) throws IOException {
            List<TextField> screwNumericalData = new ArrayList<>(List.of(mainD, threadPitch, height));
            Response response = screwService.write(getTextDataDetail(), getNumericalDataDetail(), screwNumericalData, List.of(isTrapezoidal));
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
