package strengthDetailsAnalyzer.utils;

import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Screw;
import strengthDetailsAnalyzer.utils.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputDataManager implements ResponseCovered {

    private static final String MAIN_NUMERICAL_PATTERN = "(\\d+(\\.\\d*)*)\\z|(\\d+(,\\d*)*)\\z";
    private static final String Z_NUMERICAL_PATTERN = "\\d+\\z";
    private static final String ERROR_ZERO_NUMERICAL_PATTERN = "(0+(\\.0*)*)\\z|(0+(,0*)*)\\z";
    private static final String EMPTY_PATTERN = "";
    private static final String DESCRIPTION = " имеет в качестве аргумента не корректное значение: ";

    public Response checkMainNumAndEmptyData(List<String> data){
        Boolean isCorrectPattern = true;
        return checkData(MAIN_NUMERICAL_PATTERN + "|" + EMPTY_PATTERN, data, isCorrectPattern);
    }

    public Response checkTextData(List<String> textData) {
        Boolean isCorrectPattern = false;
        return checkData(EMPTY_PATTERN, textData, isCorrectPattern);
    }

    public Response checkNonZeroNumericalData(List<String> numericalData) {
        Boolean isCorrectPattern = false;
        return checkData(ERROR_ZERO_NUMERICAL_PATTERN, numericalData, isCorrectPattern);
    }

    public Response checkZNumericalData(List<String> numericalData) {
        Boolean isCorrectPattern = true;
        return checkData(Z_NUMERICAL_PATTERN, numericalData, isCorrectPattern);
    }

    public Response checkMainNumericalData(List<String> numericalData) {
        Boolean isCorrectPattern = true;
        return checkData(MAIN_NUMERICAL_PATTERN, numericalData, isCorrectPattern);
    }

    public Response checkPositiveNumericalData(List<String> numericalData) {
        Response resCheckMainNumericalData = checkMainNumericalData(numericalData);
        Response resCheckNonZeroNumericalData = checkNonZeroNumericalData(numericalData);
        return coverToResponse(resCheckMainNumericalData.getDescription() + resCheckNonZeroNumericalData.getDescription());
    }

    public Response checkNaturalNumericalData(List<String> numericalData) {
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

    public ArrayList<String> replaceCommasWithDots(List<String> data) {
        ArrayList<String> processedStrings = new ArrayList<>();
        for (String string : data) {
            String processedString = replaceCommasWithDots(string);
            processedStrings.add(processedString);
        }
        return processedStrings;
    }

    public String replaceCommasWithDots(String dataString) {
        String processedString = dataString.replace(",", ".");
        return processedString;
    }

    public ArrayList<String> replaceEmptyStringWithZero(List<String> data) {
        ArrayList<String> resList = new ArrayList<>();
        for (String string : data) {
            if (string.equals(EMPTY_PATTERN)) {
                resList.add("0");
            } else {
                resList.add(string);
            }
        }
        return resList;
    }


    public ArrayList<String> prepareNullableString(List<String> data, Integer nullableIndex, String replaceSign) {
        ArrayList<String> processedData = new ArrayList<>();
        for (String dataMember : data) {
            if (dataMember == null) {
                processedData.add(nullableIndex, replaceSign);
            } else {
                processedData.add(dataMember);
            }
        }
        return processedData;
    }

    public ArrayList<String> prepareNullableString(List<String> data, String replaceSign) {
        ArrayList<String> processedData = new ArrayList<>();
        Integer currentIndex = 0;
        for (String dataMember : data) {
            if (dataMember == null) {
                processedData.add(currentIndex, replaceSign);
            } else {
                processedData.add(dataMember);
            }
            currentIndex++;
        }
        return processedData;
    }

    private Boolean getInversionIfRequired(Boolean isRequired, Boolean processedBoolean) {
        if (isRequired) {
            return !processedBoolean;
        } else {
            return processedBoolean;
        }
    }

    private Response checkData(String pattern, List<String> data, Boolean isCorrectPattern) {
        StringBuilder stringBuilder = new StringBuilder();
        Pattern numericalPattern = Pattern.compile(pattern);
        Matcher matcherData;
        for (String textField : data) {
            matcherData = numericalPattern.matcher(textField);
            Boolean isFound = matcherData.matches();
            if (getInversionIfRequired(isCorrectPattern, isFound)) {
                writeError(stringBuilder, textField);
            }
        }
        return coverToResponse(stringBuilder.toString());
    }

    private void writeError(StringBuilder stringBuilder, String textField) {
        stringBuilder.append(DESCRIPTION);
        stringBuilder.append(textField);
        stringBuilder.append("\n");
    }
}
