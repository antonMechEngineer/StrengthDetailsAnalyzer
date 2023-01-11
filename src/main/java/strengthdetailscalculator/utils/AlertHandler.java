package strengthdetailscalculator.utils;

import javafx.scene.control.Alert;

public class AlertHandler {
    Alert alertInputDataError = new Alert(Alert.AlertType.ERROR);

    public AlertHandler(){
        buildErrorInputData();
    }

    private void buildErrorInputData() {
        alertInputDataError.setTitle("Ошибка ввода данных");
        alertInputDataError.setHeaderText("Проверьте введённые данные и исправьте их");
        alertInputDataError.getDialogPane().setMinWidth(450);
    }
    public void getErrorInputData(String description){
        alertInputDataError.setContentText(description);
        alertInputDataError.show();
    }

}
