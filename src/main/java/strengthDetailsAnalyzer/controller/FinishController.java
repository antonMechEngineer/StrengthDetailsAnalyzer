package strengthDetailsAnalyzer.controller;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.JavaFxRunner;

import java.io.IOException;

@Component
@FxmlView("finishScene.fxml")
public class FinishController extends Controller {

    public static final String NAME = "Расчёт прочности деталей";
    private final StartController startController;

    @Autowired
    public FinishController(FxWeaver fxWeaver,
                            StartController startController) {
        super(fxWeaver);
        this.startController = startController;
    }

    public void switchStartScene(ActionEvent event) {
        Parent root = fxWeaver.loadView(StartController.class);
        switchSceneByEvent(root, NAME, event);
    }

    public void finishApplication(){
        JavaFxRunner.primaryStage.close();
    }

}
