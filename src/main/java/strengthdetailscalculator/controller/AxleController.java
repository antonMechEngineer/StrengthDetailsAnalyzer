package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import strengthdetailscalculator.service.AxleService;
import strengthdetailscalculator.utils.response.Response;

import java.io.IOException;
import java.util.List;

public class AxleController extends PinController {

    private final AxleService axleService = new AxleService(documentWriter, inputDataManager);
    public static final Integer INDEX_NUMBER_SUPPORT_LENGTH = 3;

    @FXML
    public TextField supportLength;

    public void printDoc(ActionEvent event) throws IOException {
        Response response = axleService.write(getDetailData(), getNumericalDataDetail(), getAxleData());
        processResponse(response, event);
    }

    private void switchFinishScene(ActionEvent event) throws IOException {
        switchSceneByEvent(PATH_FINISH_SCENE, NAME_FINISH_SCENE, event);
    }

    protected List<String> getAxleData() {
        List<String> axleData = getData();
        axleData.add(INDEX_NUMBER_SUPPORT_LENGTH, supportLength.getText());
        return axleData;
    }


}
