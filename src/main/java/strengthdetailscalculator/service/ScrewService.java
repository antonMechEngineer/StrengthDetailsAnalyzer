package strengthdetailscalculator.service;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;
import strengthdetailscalculator.service.interfaces.FullChecked;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ScrewService extends DetailService implements FullChecked {

    private static final Integer INDEX_MID_D = 0;
    private static final Integer INDEX_INTERNAL_D = 1;
    private static final Integer INDEX_MIN_D = 2;
    private HashMap<Double, HashMap<Double, List<Double>>> metricalScrewData = new HashMap<>();
    private HashMap<Double, HashMap<Double, List<Double>>> trapezoidalScrewData = new HashMap<>();
    private static final String DATA_PATH_METRICAL_SCREW = "src/main/resources/data/screw/metricalScrewData.csv";
    private static final String DATA_PATH_TRAPEZOIDAL_SCREW = "src/main/resources/data/screw/trapezoidalScrewData.csv";

    public ScrewService() {
        loadDataFromCSVFile(DATA_PATH_METRICAL_SCREW, metricalScrewData);
        loadDataFromCSVFile(DATA_PATH_TRAPEZOIDAL_SCREW, trapezoidalScrewData);
    }

    public Response write(List<TextField> textDataDetail, List<TextField> textNumericalData,
                          List<TextField> screwNumericalData, List<CheckBox> checkBoxes) {
        Response preProcessDetailResponse = preProcessDetailData(textDataDetail, textNumericalData, screwNumericalData);
        if (preProcessDetailResponse.getResponseStatus() == ResponseStatus.SUCCESS) {
            Detail detail = new Detail(textDataDetail, textNumericalData);
            Screw screw = build(detail, screwNumericalData, checkBoxes);
            Response errorResponseThreadProperties = inputDataManager.checkInputThreadProperties(screw);
            if (errorResponseThreadProperties.getResponseStatus() == ResponseStatus.FAIL) {
                return errorResponseThreadProperties;
            }
            documentWriter.writeScrew(screw);
            return new Response(ResponseStatus.SUCCESS);
        }
        return preProcessDetailResponse;
    }

    private Screw build(Detail detail, List<TextField> numericalData, List<CheckBox> checkBoxes) {
        ScrewType screwType = ScrewType.METRICAL;
        if (checkBoxes.get(0).isSelected()) {
            screwType = ScrewType.TRAPEZOIDAL;
        }
        Double mainD = Double.valueOf(numericalData.get(0).getText());
        Double threadPitch = Double.valueOf(numericalData.get(1).getText());
        Double height = Double.valueOf(numericalData.get(2).getText());
        Screw screw = new Screw(detail,
                mainD, threadPitch, height, screwType,
                getInternalD(mainD, threadPitch, screwType),
                getMinD(mainD, threadPitch, screwType));
        return screw;
    }

    private Double getInternalD(Double mainD, Double threadPitch, ScrewType screwType) {
        return getData(mainD, threadPitch, screwType, INDEX_INTERNAL_D);
    }

    private Double getMinD(Double mainD, Double threadPitch, ScrewType screwType) {
        return getData(mainD, threadPitch, screwType, INDEX_MIN_D);
    }

    private Double getMidD(Double mainD, Double threadPitch, ScrewType screwType) {
        return getData(mainD, threadPitch, screwType, INDEX_MID_D);
    }

    private Double getData(Double mainD, Double threadPitch, ScrewType screwType, Integer indexData) {
        Double dataValue = (double) 0;
        try {
            switch (screwType) {
                case METRICAL:
                    dataValue = metricalScrewData.get(mainD).get(threadPitch).get(indexData);
                    break;
                case TRAPEZOIDAL:
                    dataValue = trapezoidalScrewData.get(mainD).get(threadPitch).get(indexData);
            }
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
        return dataValue;
    }

    private void loadDataFromCSVFile(String currentDataPath, HashMap<Double, HashMap<Double, List<Double>>> currentStore) {
        try {
            Scanner scanner = new Scanner(new File(currentDataPath));
            scanner.nextLine();
            while (scanner.hasNext()) {
                String lineString = scanner.nextLine();
                HashMap<Double, HashMap<Double, List<Double>>> parsedLine = parseLine(lineString);
                insertLineDataInStore(parsedLine, currentStore);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertLineDataInStore(HashMap<Double, HashMap<Double, List<Double>>> lineData,
                                       HashMap<Double, HashMap<Double, List<Double>>> currentStore) {
        Double currentMainD = lineData.keySet().stream().findFirst().orElseThrow();
        if (currentStore.containsKey(currentMainD)) {
            HashMap<Double, List<Double>> currentData = lineData.get(currentMainD);
            Double currentThreadPitch = currentData.keySet().stream().findFirst().orElseThrow();
            HashMap<Double, List<Double>> currentScrewData = currentStore.get(currentMainD);
            currentScrewData.put(currentThreadPitch, currentData.get(currentThreadPitch));
        } else {
            currentStore.putAll(lineData);
        }
    }

    private HashMap<Double, HashMap<Double, List<Double>>> parseLine(String line) {
        HashMap<Double, HashMap<Double, List<Double>>> screwLine = new HashMap<>();
        String[] arrayLine = line.split(",");
        Double mainD = Double.valueOf(arrayLine[0]);
        Double threadPitch = Double.valueOf(arrayLine[1]);
        Double midD = Double.valueOf(arrayLine[2]);
        Double internalD = Double.valueOf(arrayLine[3]);
        Double minD = Double.valueOf(arrayLine[4]);
        HashMap<Double, List<Double>> pitchData = new HashMap<>();
        pitchData.put(threadPitch, Arrays.asList(midD, internalD, minD));
        screwLine.put(mainD, pitchData);
        return screwLine;
    }

    @Override
    protected Response getResultChecking(List<TextField> textDetailData, List<TextField> numericalDetailData, List<TextField> numericalScrewData) {
        return fullCheckDetailData(textDetailData, numericalDetailData, numericalScrewData);
    }


}
