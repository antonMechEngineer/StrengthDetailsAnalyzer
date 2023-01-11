package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

public class StartSceneController extends Controller {

    public void switchToScrewScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_SCREW_SCENE,NAME_SCREW_SCENE,  event);
    }
}