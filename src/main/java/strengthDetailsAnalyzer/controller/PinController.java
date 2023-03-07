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
import strengthDetailsAnalyzer.service.PinService;
import strengthDetailsAnalyzer.utils.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Component
@FxmlView("pinScene.fxml")
public class PinController extends DetailController {

    private static final String NAME = "Расчёт прочности штифта";
    private final PinService pinService;

    @Autowired
    public PinController(PinService pinService, FxWeaver fxWeaver) {
        super(fxWeaver);
        this.pinService = pinService;
    }

    public static final Integer INDEX_OUTER_DIAMETER = 0;
    public static final Integer INDEX_INTERNAL_DIAMETER = 1;
    public static final Integer INDEX_NUMBER_SHEAR_SECTION = 2;
    public static final Integer INDEX_FORCE = 3;

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

    @Override
    public void printDoc(ActionEvent event) throws IOException {
        Response response = pinService.write(getDetailData(), getNumericalDataDetail(), getData());
        processResponse(response, event);
    }

    protected List<String> getData() {
        ArrayList<String> pinData = new ArrayList<>();
        pinData.add(INDEX_OUTER_DIAMETER, outerDiameter.getText());
        pinData.add(INDEX_INTERNAL_DIAMETER, internalDiameter.getText());
        pinData.add(INDEX_NUMBER_SHEAR_SECTION, numberShearSection.getValue());
        pinData.add(INDEX_FORCE, force.getText());
        return pinData;
    }

    @FXML
    private void getNumberShearSection() {
        numberShearSection.getItems().addAll("1", "2", "3", "4");
        if (numberShearSection.getValue() != null) {
            printButton.setDisable(false);
        }
    }

}
