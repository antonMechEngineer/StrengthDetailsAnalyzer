package strengthDetailsAnalyzer.controller.interfaces;
import javafx.scene.control.Alert;

public interface Alerted {
    default void getAlert(String descriptionAlert){
        Alert alertInputDataError = new Alert(Alert.AlertType.ERROR);
        alertInputDataError.setTitle("Ошибка пользователя");
        alertInputDataError.setHeaderText("Описание проблемы:");
        alertInputDataError.getDialogPane().setMinWidth(450);
        alertInputDataError.setContentText(descriptionAlert);
        alertInputDataError.show();
    }
}
