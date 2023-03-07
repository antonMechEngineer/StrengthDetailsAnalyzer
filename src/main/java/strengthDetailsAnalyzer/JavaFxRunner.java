package strengthDetailsAnalyzer;

import strengthDetailsAnalyzer.controller.StartController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


public class JavaFxRunner extends Application {
    private ConfigurableApplicationContext context;
    public static Stage primaryStage;

    @Override
    public void init() {
        context = new SpringApplicationBuilder()
                .sources(StrengthDetailsAnalyzer.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        Scene scene = new Scene(fxWeaver.loadView(StartController.class));
        stage.setScene(scene);
        stage.show();
        primaryStage = stage;
    }}

