package strengthdetailscalculator.utils;

import javafx.scene.control.TextField;
import strengthdetailscalculator.entity.Screw;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputDataManager {

    public String checkTextData(List<TextField> textData) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TextField textField : textData) {
            if (textField.getText() == "") {
                stringBuilder.append(textField.getId());
                stringBuilder.append(" имеет в качестве аргумента пустое значение \n");
            }
        }
        return stringBuilder.toString();
    }

    public String checkInputThreadProperties(Screw screw){
        String response = "OK";
        if (screw.getInternalD() == 0 && screw.getMinD() == 0){
            response = " резьба с внутренним диаметром = " + screw.getMainD() + ", и шагом = " + screw.getThreadPitch() + " не стандартная.";
        }
        return response;
    }


    public String checkNumericalData(List<TextField> numericalData) {
        StringBuilder stringBuilder = new StringBuilder();
        Pattern correctNumericalPattern = Pattern.compile("(\\d+(\\.\\d*)*)\\z|(\\d+(,\\d*)*)\\z");
        Pattern unCorrectNumericalPattern = Pattern.compile("(0+(\\.0*)*)\\z|(0+(,0*)*)\\z");
       // Pattern unCorrectNumericalPattern = Pattern.compile("");
        Matcher matcherCorrectNum;
        Matcher matcherUnCorrectNum;
        for (TextField textField : numericalData) {
            matcherCorrectNum = correctNumericalPattern.matcher(textField.getText());
            matcherUnCorrectNum = unCorrectNumericalPattern.matcher(textField.getText());
            if (!matcherCorrectNum.matches() || matcherUnCorrectNum.matches()) {
                stringBuilder.append(textField.getId());
                stringBuilder.append(" имеет в качестве аргумента не корректное значение: ");
                stringBuilder.append(textField.getText());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public void prepareNumericalData(List<TextField> numericalData) {
        for (TextField textField : numericalData) {
            textField.setText(textField.getText().replace(",", "."));
            textField.setText(textField.getText().replace(",", "."));
        }
    }


}
