package strengthDetailsAnalyzer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import strengthDetailsAnalyzer.entity.enums.EarType;
import strengthDetailsAnalyzer.service.EarService;
import strengthDetailsAnalyzer.utils.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static strengthDetailsAnalyzer.entity.enums.EarType.*;

@Setter
@Component
@FxmlView("earScene.fxml")
public class EarController extends DetailController {

    private static final String NAME = "Расчёт прочности проушины";
    private final EarService earService;

    @Autowired
    public EarController(EarService earService, FxWeaver fxWeaver) {
        super(fxWeaver);
        this.earService = earService;
    }

    public static final Integer INDEX_OUTER_D = 0;
    public static final Integer INDEX_INTERNAL_D = 1;
    public static final Integer INDEX_THICKNESS = 2;
    public static final Integer INDEX_ECCENTRICITY = 3;
    public static final Integer INDEX_TYPE_EAR = 4;
    public static final Integer INDEX_IS_SINGLE_SHEARED_CONNECTION = 5;
    public static final Integer INDEX_IS_EAR_LARGER = 6;
    public static final Integer INDEX_GAP = 7;
    public static final Integer INDEX_FORCE = 8;

    @FXML
    protected TextField force;

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

    protected ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add(INDEX_OUTER_D, outerDiameter.getText());
        data.add(INDEX_INTERNAL_D, internalDiameter.getText());
        data.add(INDEX_THICKNESS, thickness.getText());
        data.add(INDEX_ECCENTRICITY, eccentricity.getText());
        data.add(INDEX_TYPE_EAR, earType.getValue());
        data.add(INDEX_IS_SINGLE_SHEARED_CONNECTION, Boolean.valueOf(isSingleShearedConnection.isSelected()).toString());
        data.add(INDEX_IS_EAR_LARGER, Boolean.valueOf(currentEarIsLarger.isSelected()).toString());
        data.add(INDEX_GAP, gap.getText());
        data.add(INDEX_FORCE, force.getText());
        return data;
    }

    @FXML
    private void getEarType() {
        earType.getItems().addAll(Arrays.stream(EarType.values()).map(value -> value.description).collect(Collectors.toList()));
        if (earType.getValue() != null) {
            printButton.setDisable(false);
        }
    }

    @FXML
    private void getAdditionalProperties() {
        if (isSingleShearedConnection.isSelected()) {
            earType.setDisable(true);
            currentEarIsLarger.setDisable(false);
            printButton.setDisable(false);
        } else {
            earType.setDisable(false);
            currentEarIsLarger.setDisable(true);
            gap.setDisable(true);
            if (earType.getValue() == null) {
                printButton.setDisable(true);
            }
        }
    }

    @FXML
    void getGapProperty() {
        if (currentEarIsLarger.isSelected()) {
            gap.setDisable(false);
        } else {
            gap.setDisable(true);
        }
    }
}
