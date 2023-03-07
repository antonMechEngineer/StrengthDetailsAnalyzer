package strengthDetailsAnalyzer.controller;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Controller {
    protected final FxWeaver fxWeaver;

    @Autowired
    public Controller(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }
    protected void switchSceneByEvent(Parent root, String name, ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(name);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setY(0);
        stage.setX(600);
        stage.show();
    }

}
