package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Setter;
import strengthdetailscalculator.service.ScrewService;
import strengthdetailscalculator.utils.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static strengthdetailscalculator.entity.enums.ScrewType.METRICAL;
import static strengthdetailscalculator.entity.enums.ScrewType.TRAPEZOIDAL;
@Setter
public class ScrewController extends DetailController {

    ScrewService screwService = new ScrewService(documentWriter, inputDataManager);
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
    private ComboBox<String> screwType;

    @FXML
    private Button printButton;

    @Override
    public void printDoc(ActionEvent event) throws IOException {
        Response response = screwService.write(getDetailData(), getNumericalDataDetail(), getData());
        processResponse(response, event);
    }

    protected List<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add(INDEX_MAIN_D, mainD.getText());
        data.add(INDEX_THREAD_PITCH, threadPitch.getText());
        data.add(INDEX_HEIGHT, height.getText());
        data.add(INDEX_TYPE_SCREW, screwType.getValue());
        return data;
    }

    @FXML
    private void getScrewType() {
        screwType.getItems().addAll(METRICAL.description, TRAPEZOIDAL.description);
        if (screwType.getValue() != null) {
            printButton.setDisable(false);
        }
    }
}
