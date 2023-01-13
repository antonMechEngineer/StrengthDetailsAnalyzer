package strengthdetailscalculator.utils;

import javafx.scene.control.TextField;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.utils.response.Response;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputDataManager implements ResponseCovered {

    private static final String MAIN_NUMERICAL_PATTERN = "(\\d+(\\.\\d*)*)\\z|(\\d+(,\\d*)*)\\z";
    private static final String Z_NUMERICAL_PATTERN = "\\d+\\z";
    private static final String ERROR_ZERO_NUMERICAL_PATTERN = "(0+(\\.0*)*)\\z|(0+(,0*)*)\\z";
    private static final String ERROR_EMPTY_TEXT_PATTERN = "";
    private static final String DESCRIPTION = " имеет в качестве аргумента не корректное значение: ";

    private Response checkData(String pattern, List<TextField> data, Boolean isCorrectPattern) {
        StringBuilder stringBuilder = new StringBuilder();
        Pattern numericalPattern = Pattern.compile(pattern);
        Matcher matcherData;
        for (TextField textField : data) {
            matcherData = numericalPattern.matcher(textField.getText());
            Boolean isFound = matcherData.matches();
            if(getInversionIfRequired(isCorrectPattern, isFound)){
                writeError(stringBuilder, textField);
            }
        }
        return coverToResponse(stringBuilder.toString());
    }

    public Response checkTextData(List<TextField> textData) {
        Boolean isCorrectPattern = false;
        return checkData(ERROR_EMPTY_TEXT_PATTERN, textData, isCorrectPattern);
    }

    public Response checkNonZeroNumericalData(List<TextField> numericalData) {
        Boolean isCorrectPattern = false;
        return checkData(ERROR_ZERO_NUMERICAL_PATTERN, numericalData, isCorrectPattern);
    }

    public Response checkZNumericalData(List<TextField> numericalData) {
        Boolean isCorrectPattern = true;
        return checkData(Z_NUMERICAL_PATTERN, numericalData, isCorrectPattern);
    }

    public Response checkMainNumericalData(List<TextField> numericalData) {
        Boolean isCorrectPattern = true;
        return checkData(MAIN_NUMERICAL_PATTERN, numericalData, isCorrectPattern);
    }

    public Response checkPositiveNumericalData(List<TextField> numericalData) {
        Response resCheckMainNumericalData = checkMainNumericalData(numericalData);
        Response resCheckNonZeroNumericalData = checkNonZeroNumericalData(numericalData);
        return coverToResponse(resCheckMainNumericalData.getDescription() + resCheckNonZeroNumericalData.getDescription());
    }

    public Response checkNaturalNumericalData(List<TextField> numericalData) {
        Response resCheckPositiveNumericalData = checkPositiveNumericalData(numericalData);
        Response resCheckZNumericalData = checkZNumericalData(numericalData);
        return coverToResponse(resCheckPositiveNumericalData.getDescription() + resCheckZNumericalData.getDescription());
    }

    public Response checkInputThreadProperties(Screw screw) {
        String error = "";
        if (screw.getInternalD() == 0 && screw.getMinD() == 0) {
            error = " резьба с внешним диаметром = " + screw.getMainD() + ", и шагом = " + screw.getThreadPitch() + " не стандартная.";
        }
        return coverToResponse(error);
    }

    private void writeError(StringBuilder stringBuilder, TextField textField) {
        stringBuilder.append(textField.getId());
        stringBuilder.append(DESCRIPTION);
        stringBuilder.append(textField.getText());
        stringBuilder.append("\n");
    }

    public void prepareNumericalData(List<TextField> numericalData) {
        for (TextField textField : numericalData) {
            textField.setText(textField.getText().replace(",", "."));
            textField.setText(textField.getText().replace(",", "."));
        }
    }

    public Boolean getInversionIfRequired(Boolean isRequired, Boolean processedBoolean) {
        if (isRequired) {
            return !processedBoolean;
        } else {
            return processedBoolean;
        }
    }

    public void prepareTextData(List<TextField> textData) {
        for (TextField textField : textData) {
            // TODO: 12.01.2023 преобразовать падеж в названии детали
        }
    }

}
