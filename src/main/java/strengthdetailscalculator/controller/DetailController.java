package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import strengthdetailscalculator.StrengthDetailsCalculator;
import strengthdetailscalculator.utils.AlertHandler;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DetailController extends Controller {

    AlertHandler alertHandler = new AlertHandler();

    public static final Integer INDEX_NAME = 0;
    public static final Integer INDEX_CODE = 1;
    public static final Integer INDEX_MATERIAL = 2;
    public static final Integer INDEX_YIELD_STRESS = 0;
    public static final Integer INDEX_FORCE = 1;

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

    public abstract void printDoc(ActionEvent event) throws IOException;

    protected abstract List<String> getData();

    protected void switchScene(String pathScene, String nameScene, Stage currentStage) throws IOException {
        currentStage.setTitle(nameScene);
        root = FXMLLoader.load(StrengthDetailsCalculator.class.getResource(pathScene));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchStartScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_START_SCENE, NAME_START_SCENE, event);
    }

    private void switchFinishScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_FINISH_SCENE, NAME_FINISH_SCENE, event);
    }

    protected List<String> getDetailData(){
        ArrayList<String> textDataDetail = new ArrayList<>();
        textDataDetail.add(INDEX_NAME, name.getText());
        textDataDetail.add(INDEX_CODE, code.getText());
        textDataDetail.add(INDEX_MATERIAL, material.getText());
        return  textDataDetail;
    }

    protected List<String> getNumericalDataDetail(){
        ArrayList<String> numericalDataDetail = new ArrayList<>();
        numericalDataDetail.add(INDEX_YIELD_STRESS, yieldStress.getText());
        numericalDataDetail.add(INDEX_FORCE, force.getText());
        return numericalDataDetail;
    }

    protected void processResponse(Response response, ActionEvent event) throws IOException {
        if (response.getResponseStatus() == ResponseStatus.SUCCESS) {
            switchFinishScene(event);
        } else {
            alertHandler.getErrorInputData(response.getDescription());
        }
    }

}
