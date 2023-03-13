package strengthDetailsAnalyzer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.service.AxleService;
import strengthDetailsAnalyzer.service.PinService;
import strengthDetailsAnalyzer.utils.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Component
@FxmlView("axleScene.fxml")
public class AxleController extends DetailController {

    private final AxleService axleService;
    public static final Integer INDEX_OUTER_DIAMETER = 0;
    public static final Integer INDEX_INTERNAL_DIAMETER = 1;
    public static final Integer INDEX_NUMBER_SHEAR_SECTION = 2;
    public static final Integer INDEX_NUMBER_SUPPORT_LENGTH = 3;
    public static final Integer INDEX_FORCE = 4;

    @Autowired
    public AxleController(FxWeaver fxWeaver, AxleService axleService) {
        super(fxWeaver);
        this.axleService = axleService;
    }

    @FXML
    protected TextField force;

    @FXML
    private TextField outerDiameter;

    @FXML
    private TextField internalDiameter;

    @FXML
    private ComboBox<String> numberShearSection;

    @FXML
    protected Button printButton;

    @FXML
    private TextField supportLength;

    @Override
    public void printDoc(ActionEvent event) throws IOException {
        Response response = axleService.write(getDetailData(), getNumericalDataDetail(), getData());
        processResponse(response, event);
    }

    @Override
    protected ArrayList<String> getData() {
        ArrayList<String> axleData = new ArrayList<>();
        axleData.add(INDEX_OUTER_DIAMETER, outerDiameter.getText());
        axleData.add(INDEX_INTERNAL_DIAMETER, internalDiameter.getText());
        axleData.add(INDEX_NUMBER_SHEAR_SECTION, numberShearSection.getValue());
        axleData.add(INDEX_NUMBER_SUPPORT_LENGTH, supportLength.getText());
        axleData.add(INDEX_FORCE, force.getText());
        return axleData;
    }

    @FXML
    protected void getNumberShearSection() {
        numberShearSection.getItems().addAll("1", "2", "3", "4");
        if (numberShearSection.getValue() != null) {
            printButton.setDisable(false);
        }
    }
}
