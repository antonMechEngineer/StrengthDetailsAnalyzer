package strengthDetailsAnalyzer.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import strengthDetailsAnalyzer.controller.interfaces.Alerted;
import strengthDetailsAnalyzer.utils.DataLoader;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;
import java.io.IOException;
import java.util.*;

@Setter
public abstract class DetailController extends Controller implements Alerted {

    @Autowired
    private DataLoader dataLoader;

    private HashMap<String, Set<String>> materials;
    private Set<String> sorts = new HashSet<>();

    @Autowired
    public DetailController(FxWeaver fxWeaver) {
        super(fxWeaver);
    }

    public static final Integer INDEX_NAME = 0;
    public static final Integer INDEX_CODE = 1;
    public static final Integer INDEX_MATERIAL = 2;
    public static final Integer INDEX_IS_USER_SAFETY_FACTOR = 3;

    public static final Integer INDEX_YIELD_STRESS = 0;
    public static final Integer INDEX_USER_SAFETY_FACTOR = 1;


    @FXML
    protected TextField name;

    @FXML
    protected TextField code;

    @FXML
    protected TextField material;

    @FXML
    protected TextField yieldStress;

    @FXML
    protected TextField usersSafetyFactor;

    @FXML
    protected CheckBox isUsersSafetyFactor;


    public abstract void printDoc(ActionEvent event) throws IOException;

    protected abstract ArrayList<String> getData();

    @FXML
    protected void switchStartScene(ActionEvent event) {
        Parent root = fxWeaver.loadView(StartController.class);
        switchSceneByEvent(root, StartController.NAME, event);
    }

    protected void switchFinishScene(ActionEvent event) {
        Parent root = fxWeaver.loadView(FinishController.class);
        switchSceneByEvent(root, FinishController.NAME, event);
    }

    public final ArrayList<String> getDetailData() {
        ArrayList<String> textDataDetail = new ArrayList<>();
        textDataDetail.add(INDEX_NAME, name.getText());
        textDataDetail.add(INDEX_CODE, code.getText());
        textDataDetail.add(INDEX_MATERIAL, material.getText());
        textDataDetail.add(INDEX_IS_USER_SAFETY_FACTOR, String.valueOf(isUsersSafetyFactor.isSelected()));
        return textDataDetail;
    }

    protected final ArrayList<String> getNumericalDataDetail() {
        ArrayList<String> numericalDataDetail = new ArrayList<>();
        numericalDataDetail.add(INDEX_YIELD_STRESS, yieldStress.getText());
        numericalDataDetail.add(INDEX_USER_SAFETY_FACTOR, usersSafetyFactor.getText());
        return numericalDataDetail;
    }

    protected final void processResponse(Response response, ActionEvent event) {
        if (response.getResponseStatus() == ResponseStatus.SUCCESS) {
            switchFinishScene(event);
        } else {
            getAlert(response.getDescription());
        }
    }

    @FXML
    protected void getMinSafetyFactor() {
        if (isUsersSafetyFactor.isSelected()) {
            usersSafetyFactor.setDisable(false);
        } else {
            usersSafetyFactor.setDisable(true);
        }
    }
}
