package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import strengthdetailscalculator.StrengthDetailsCalculator;

import java.io.IOException;
public class FinishSceneController extends Controller {

    public void switchStartScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_START_SCENE, NAME_FINISH_SCENE, event);
    }

    public void finishApplication(){
        StrengthDetailsCalculator.primaryStage.close();
    }

}
