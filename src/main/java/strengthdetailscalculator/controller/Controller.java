package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import strengthdetailscalculator.StrengthDetailsCalculator;
import strengthdetailscalculator.utils.AlertHandler;
import java.io.IOException;
import java.util.List;

public abstract class Controller {

    AlertHandler alertHandler = new AlertHandler();

    protected static final String PATH_START_SCENE = "startScene.fxml";
    protected static final String PATH_SCREW_SCENE = "screwScene.fxml";
    protected static final String PATH_PIN_SCENE = "pinScene.fxml";
    protected static final String PATH_FINISH_SCENE = "finishScene.fxml";

    protected static final String NAME_START_SCENE = "Расчёт прочности стандартных деталей";
    protected static final String NAME_SCREW_SCENE = "Расчёт прочности резьбы";
    protected static final String NAME_PIN_SCENE = "Расчёт прочности штифта";
    protected static final String NAME_FINISH_SCENE = NAME_START_SCENE;

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    @FXML
    protected TextField name;

    @FXML
    protected TextField code;

    @FXML
    protected TextField material;

    @FXML
    protected TextField yieldStress;

    @FXML
    protected TextField force;


    protected void switchSceneByEvent(String pathScene, String nameScene, ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(nameScene);

        root = FXMLLoader.load(StrengthDetailsCalculator.class.getResource(pathScene));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected void switchScene(String pathScene, String nameScene, Stage currentStage) throws IOException {
        currentStage.setTitle(nameScene);
        root = FXMLLoader.load(StrengthDetailsCalculator.class.getResource(pathScene));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected List<TextField> getTextDataDetail(){
        return  List.of(name, code, material);
    }

    protected List<TextField> getNumericalDataDetail(){
        return List.of(yieldStress, force);
    }


}
