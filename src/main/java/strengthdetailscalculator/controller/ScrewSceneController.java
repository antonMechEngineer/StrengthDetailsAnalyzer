package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import strengthdetailscalculator.service.ScrewService;
import strengthdetailscalculator.utils.AlertHandler;

import java.io.IOException;
import java.util.List;

public class ScrewSceneController extends Controller {

    ScrewService screwService = new ScrewService();
    AlertHandler alertHandler = new AlertHandler();

    @FXML
    private TextField name;

    @FXML
    private TextField code;

    @FXML
    private TextField mainD;

    @FXML
    private TextField threadPitch;

    @FXML
    private TextField height;

    @FXML
    private TextField material;

    @FXML
    private TextField yieldStress;

    @FXML
    private TextField force;

    @FXML
    private CheckBox isTrapezoidal;


    public void printDoc(ActionEvent event) throws IOException {
        String response = screwService.getResponse(List.of(name, code, material),
                List.of(mainD, threadPitch, height, yieldStress, force), List.of(isTrapezoidal));
        if (response.equals("OK")) {
            switchFinishScene(event);
        } else {
            alertHandler.getErrorInputData(response);
        }
    }

    private void switchFinishScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_FINISH_SCENE, NAME_FINISH_SCENE, event);
    }
}
