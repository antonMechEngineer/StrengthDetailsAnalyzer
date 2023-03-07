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
import org.springframework.stereotype.Component;
import pl.jsolve.sweetener.criteria.restriction.In;
import strengthDetailsAnalyzer.service.ScrewService;
import strengthDetailsAnalyzer.utils.response.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static strengthDetailsAnalyzer.entity.enums.ScrewType.METRICAL;
import static strengthDetailsAnalyzer.entity.enums.ScrewType.TRAPEZOIDAL;

@Setter
@Component
@FxmlView("screwScene.fxml")
public class ScrewController extends DetailController {

    private final static String NAME = "Расчёт прочности резьбы";
    private final ScrewService screwService;

    @Autowired
    public ScrewController(ScrewService screwService, FxWeaver fxWeaver) {
        super(fxWeaver);
        this.screwService = screwService;
    }

    public static final Integer INDEX_MAIN_D = 0;
    public static final Integer INDEX_HEIGHT = 1;
    public static final Integer INDEX_TYPE_SCREW = 2;
    public static final Integer INDEX_IS_SMALL_PITCH = 3;
    public static final Integer INDEX_THREAD_PITCH = 4;
    public static final Integer INDEX_FORCE = 5;

    @FXML
    protected TextField force;

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

    @FXML
    private CheckBox isSmallScrewPitch;

    @Override
    public void printDoc(ActionEvent event) throws IOException {
        Response response = screwService.write(getDetailData(), getNumericalDataDetail(), getData());
        processResponse(response, event);
    }

    protected List<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add(INDEX_MAIN_D, mainD.getText());
        data.add(INDEX_HEIGHT, height.getText());
        data.add(INDEX_TYPE_SCREW, screwType.getValue());
        data.add(INDEX_IS_SMALL_PITCH, String.valueOf(isSmallScrewPitch.isSelected()));
        data.add(INDEX_THREAD_PITCH, threadPitch.getText());
        data.add(INDEX_FORCE, force.getText());
        return data;
    }

    @FXML
    private void getScrewType() {
        screwType.getItems().addAll(METRICAL.description, TRAPEZOIDAL.description);
        if (screwType.getValue() != null) {
            printButton.setDisable(false);
        }
    }

    @FXML
    private void switchAccessThreadPitch(){
        if(isSmallScrewPitch.isSelected()){
            threadPitch.setDisable(false);
        }
        else{
            threadPitch.setDisable(true);
        }
    }
}
