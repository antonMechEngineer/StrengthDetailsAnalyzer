package strengthdetailscalculator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StrengthDetailsCalculator extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(StrengthDetailsCalculator.class.getResource("startScene.fxml"));
        Scene scene = new Scene(root, 300, 300);
        stage.setTitle("Расчёт прочности стандартных деталей");
        stage.setScene(scene);
        stage.show();
        primaryStage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}