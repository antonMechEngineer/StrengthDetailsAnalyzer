package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class StartSceneController extends Controller {

    public void switchToScrewScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_SCREW_SCENE,NAME_SCREW_SCENE,  event);
    }
    public void switchToPinScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_PIN_SCENE, NAME_PIN_SCENE,  event);
    }

    public void switchToAxleScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_AXLE_SCENE, NAME_AXLE_SCENE,  event);
    }

}