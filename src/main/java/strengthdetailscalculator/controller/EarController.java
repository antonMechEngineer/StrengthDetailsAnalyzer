package strengthdetailscalculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Setter;
import strengthdetailscalculator.service.EarService;
import strengthdetailscalculator.utils.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static strengthdetailscalculator.entity.enums.EarType.*;

@Setter
public class EarController extends DetailController {

    EarService earService = new EarService(documentWriter, inputDataManager);
    public static final Integer INDEX_OUTER_D = 0;
    public static final Integer INDEX_INTERNAL_D = 1;
    public static final Integer INDEX_THICKNESS = 2;
    public static final Integer INDEX_ECCENTRICITY = 3;
    public static final Integer INDEX_TYPE_EAR = 4;
    public static final Integer INDEX_IS_SINGLE_SHEARED_CONNECTION = 5;
    public static final Integer INDEX_IS_EAR_LARGER = 6;
    public static final Integer INDEX_GAP = 7;

    @FXML
    private TextField outerDiameter;

    @FXML
    private TextField internalDiameter;

    @FXML
    private TextField eccentricity;

    @FXML
    private TextField thickness;

    @FXML
    private ComboBox<String> earType;

    @FXML
    private CheckBox isSingleShearedConnection;

    @FXML
    private CheckBox currentEarIsLarger;

    @FXML
    private TextField gap;

    @FXML
    private Button printButton;


    @Override
    public void printDoc(ActionEvent event) throws IOException {
        Response response = earService.write(getDetailData(), getNumericalDataDetail(), getData());
        processResponse(response, event);
    }

    protected List<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add(INDEX_OUTER_D, outerDiameter.getText());
        data.add(INDEX_INTERNAL_D, internalDiameter.getText());
        data.add(INDEX_THICKNESS, thickness.getText());
        data.add(INDEX_ECCENTRICITY, eccentricity.getText());
        data.add(INDEX_TYPE_EAR, earType.getValue().toString());
        data.add(INDEX_IS_SINGLE_SHEARED_CONNECTION, isSingleShearedConnection.getText());
        data.add(INDEX_GAP, gap.getText());
        return data;
    }

    @FXML
    private void getEarType() {
        earType.getItems().addAll(STEEL_CENTRAL.name(), STEEL_SIDE.name(), ALUMINUM_SIDE.name());
        if (earType.getValue() != null) {
            printButton.setDisable(false);
        }
    }

    @FXML
    private void getAdditionalProperties() {
        if (isSingleShearedConnection.isSelected()) {
            currentEarIsLarger.setDisable(false);
            gap.setDisable(false);
        } else {
            currentEarIsLarger.setDisable(true);
            gap.setDisable(true);
        }

    }

}
