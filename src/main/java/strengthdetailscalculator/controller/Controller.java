package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import strengthdetailscalculator.StrengthDetailsCalculator;

import java.io.IOException;

public abstract class Controller {
    protected static final String PATH_START_SCENE = "startScene.fxml";
    protected static final String PATH_SCREW_SCENE = "screwScene.fxml";
    protected static final String PATH_PIN_SCENE = "pinScene.fxml";
    protected static final String PATH_AXLE_SCENE = "axleScene.fxml";
    protected static final String PATH_EAR_SCENE = "earScene.fxml";
    protected static final String PATH_FINISH_SCENE = "finishScene.fxml";

    protected static final String NAME_START_SCENE = "Расчёт прочности стандартных деталей";
    protected static final String NAME_SCREW_SCENE = "Расчёт прочности резьбы";
    protected static final String NAME_PIN_SCENE = "Расчёт прочности штифта";
    protected static final String NAME_AXLE_SCENE = "Расчёт прочности оси";
    protected static final String NAME_EAR_SCENE = "Расчёт прочности проушины";
    protected static final String NAME_FINISH_SCENE = NAME_START_SCENE;

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    protected void switchSceneByEvent(String pathScene, String nameScene, ActionEvent event) throws IOException {

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(nameScene);
        root = FXMLLoader.load(StrengthDetailsCalculator.class.getResource(pathScene));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
