package strengthdetailscalculator.utils;

import javafx.scene.control.TextField;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.utils.response.Response;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputDataManager implements ResponseCovered {

    public Response checkTextData(List<TextField> textData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TextField textField : textData) {
            if (textField.getText() == "") {
                stringBuilder.append(textField.getId());
                stringBuilder.append(" имеет в качестве аргумента пустое значение \n");
            }
        }
        return coverToResponse(stringBuilder.toString());
    }

    public Response checkInputThreadProperties(Screw screw){
        String error = "";
        if (screw.getInternalD() == 0 && screw.getMinD() == 0){
            error = " резьба с внешним диаметром = " + screw.getMainD() + ", и шагом = " + screw.getThreadPitch() + " не стандартная.";
        }
        return coverToResponse(error);
    }

    public Response checkFullNumericalData(List<TextField> numericalData){
        Response resCheckMainNumericalData = checkMainNumericalData(numericalData);
        Response resCheckNonZeroNumericalData = checkNonZeroNumericalData(numericalData);
        return coverToResponse(resCheckMainNumericalData.getDescription() + resCheckNonZeroNumericalData.getDescription());
    }

    public Response checkNonZeroNumericalData(List<TextField> numericalData) {
        StringBuilder stringBuilder = new StringBuilder();
        Pattern unCorrectNumericalPattern = Pattern.compile("(0+(\\.0*)*)\\z|(0+(,0*)*)\\z");
        Matcher matcherUnCorrectNum;
        for (TextField textField : numericalData) {
            matcherUnCorrectNum = unCorrectNumericalPattern.matcher(textField.getText());
            if (matcherUnCorrectNum.matches()) {
                writeError(stringBuilder, textField);
            }
        }
        return coverToResponse(stringBuilder.toString());
    }

    public Response checkMainNumericalData(List<TextField> numericalData) {
        StringBuilder stringBuilder = new StringBuilder();
        Pattern correctNumericalPattern = Pattern.compile("(\\d+(\\.\\d*)*)\\z|(\\d+(,\\d*)*)\\z");
        Matcher matcherCorrectNum;
        for (TextField textField : numericalData) {
            matcherCorrectNum = correctNumericalPattern.matcher(textField.getText());
            if (!matcherCorrectNum.matches()) {
                writeError(stringBuilder, textField);
            }
        }
        return coverToResponse(stringBuilder.toString());
    }

    private void writeError(StringBuilder stringBuilder, TextField textField){
        stringBuilder.append(textField.getId());
        stringBuilder.append(" имеет в качестве аргумента не корректное значение: ");
        stringBuilder.append(textField.getText());
        stringBuilder.append("\n");
    }

    public void prepareNumericalData(List<TextField> numericalData) {
        for (TextField textField : numericalData) {
            textField.setText(textField.getText().replace(",", "."));
            textField.setText(textField.getText().replace(",", "."));
        }
    }

    public void prepareTextData(List<TextField> textData) {
        for (TextField textField : textData) {
            // TODO: 12.01.2023 преобразовать падеж в названии детали
        }
    }

    // TODO: 12.01.2023 добавить проверку на несоответсвие размеров друг другу


}
