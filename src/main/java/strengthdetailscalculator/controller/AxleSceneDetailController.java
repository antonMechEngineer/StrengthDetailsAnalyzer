package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import strengthdetailscalculator.service.AxleService;
import strengthdetailscalculator.utils.response.Response;

import java.io.IOException;
import java.util.List;

public class AxleSceneDetailController extends PinSceneDetailController {

    AxleService axleService = new AxleService();

    public static final Integer INDEX_NUMBER_LENGTH_SUPPORT = 3;

    @FXML
    private TextField lengthSupport;

    public void printDoc(ActionEvent event) throws IOException {
        Response response = axleService.write(getTextDataDetail(), getNumericalDataDetail(), getAxleData());
        processResponse(response, event);
    }

    private void switchFinishScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_FINISH_SCENE, NAME_FINISH_SCENE, event);
    }

    protected List<Parent> getAxleData() {
        List<Parent> axleData = getData();
        axleData.add(INDEX_INTERNAL_DIAMETER, lengthSupport);
        return axleData;
    }

}
