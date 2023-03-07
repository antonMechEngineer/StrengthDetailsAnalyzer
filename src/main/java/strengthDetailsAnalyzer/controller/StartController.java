package strengthDetailsAnalyzer.controller;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.utils.DataLoader;

import java.io.IOException;

@Component
@FxmlView("startScene.fxml")
public class StartController extends Controller {

    public static final String NAME = "Расчёт прочности деталей";

    @Autowired
    public StartController(FxWeaver fxWeaver) {
        super(fxWeaver);
    }

    public void switchToScrewScene(ActionEvent event) {

        Parent root = fxWeaver.loadView(ScrewController.class);
        switchSceneByEvent(root , NAME,  event);
    }
    public void switchToPinScene(ActionEvent event) {
        Parent root = fxWeaver.loadView(PinController.class);
        switchSceneByEvent(root, NAME,  event);
    }

    public void switchToAxleScene(ActionEvent event) {
        Parent root = fxWeaver.loadView(AxleController.class);
        switchSceneByEvent(root, NAME,  event);
    }

    public void switchToEarScene(ActionEvent event) {
        Parent root = fxWeaver.loadView(EarController.class);
        switchSceneByEvent(root, NAME, event);
    }

    public void switchToWeldScene(ActionEvent event) {
        Parent root = fxWeaver.loadView(WeldController.class);
        switchSceneByEvent(root, NAME, event);
    }

}