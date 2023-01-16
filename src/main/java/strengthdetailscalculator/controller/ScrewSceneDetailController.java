package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import strengthdetailscalculator.service.ScrewService;
import strengthdetailscalculator.utils.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrewSceneDetailController extends DetailController {

    ScrewService screwService = new ScrewService();

    public static final Integer INDEX_MAIN_D = 0;
    public static final Integer INDEX_THREAD_PITCH = 1;
    public static final Integer INDEX_HEIGHT = 2;
    public static final Integer INDEX_TYPE_SCREW = 3;

    @FXML
    private TextField mainD;

    @FXML
    private TextField threadPitch;

    @FXML
    private TextField height;

    @FXML
    private CheckBox isTrapezoidal;


    public void printDoc(ActionEvent event) throws IOException {
        Response response = screwService.write(getTextDataDetail(), getNumericalDataDetail(), getData());
        processResponse(response, event);
    }

    protected List<Parent> getData() {
        ArrayList<Parent> data = new ArrayList<>();
        data.add(INDEX_MAIN_D, mainD);
        data.add(INDEX_THREAD_PITCH, threadPitch);
        data.add(INDEX_HEIGHT, height);
        data.add(INDEX_TYPE_SCREW, isTrapezoidal);
        return data;
    }
}
