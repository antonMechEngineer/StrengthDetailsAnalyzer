package strengthdetailscalculator.service;

import strengthdetailscalculator.controller.ScrewController;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static strengthdetailscalculator.controller.ScrewController.*;
import static strengthdetailscalculator.entity.enums.ScrewType.METRICAL;
import static strengthdetailscalculator.entity.enums.ScrewType.TRAPEZOIDAL;

public final class ScrewService extends DetailService {
    private static final Integer INDEX_MID_D = 0;
    private static final Integer INDEX_INTERNAL_D = 1;
    private static final Integer INDEX_MIN_D = 2;
    private HashMap<Double, HashMap<Double, List<Double>>> metricalScrewData = new HashMap<>();
    private HashMap<Double, HashMap<Double, List<Double>>> trapezoidalScrewData = new HashMap<>();
    private static final String DATA_PATH_METRICAL_SCREW = "src/main/resources/data/screw/metricalScrewData.csv";
    private static final String DATA_PATH_TRAPEZOIDAL_SCREW = "src/main/resources/data/screw/trapezoidalScrewData.csv";

    public ScrewService(DocumentWriter documentWriter, InputDataManager inputDataManager) {
        super(documentWriter, inputDataManager);
        loadDataFromCSVFile(DATA_PATH_METRICAL_SCREW, metricalScrewData);
        loadDataFromCSVFile(DATA_PATH_TRAPEZOIDAL_SCREW, trapezoidalScrewData);
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, List<String> data) {
        Screw screw = build(detail, data);
        Response resThreadProperties = inputDataManager.checkInputThreadProperties(screw);
        if (resThreadProperties.getResponseStatus() == ResponseStatus.FAIL) {
            return resThreadProperties;
        }
        documentWriter.writeScrew(screw);
        return new Response(ResponseStatus.SUCCESS);

    }

    @Override
    protected Response checkData(List<String> data) {
        List<String> numericalData = List.of(data.get(INDEX_MAIN_D), data.get(INDEX_THREAD_PITCH), data.get(INDEX_HEIGHT));
        Response response = inputDataManager.checkPositiveNumericalData(numericalData);
        return response;
    }

    @Override
    protected List<String> prepareData(List<String> data) {
       List<String> processedData = inputDataManager.replaceCommasWithDots(data);
        return processedData;
    }

    private Screw build(Detail detail, List<String> data) {
        ScrewType screwType = getScrewType(data.get(ScrewController.INDEX_TYPE_SCREW));
        Double mainD = Double.valueOf(data.get(INDEX_MAIN_D));
        Double threadPitch = Double.valueOf(data.get(INDEX_THREAD_PITCH));
        Double height = Double.valueOf(data.get(INDEX_HEIGHT));
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

    private ScrewType getScrewType(String name) {
        if (name.equals("Трапецеидальная")) {
            return TRAPEZOIDAL;
        }
        return METRICAL;
    }
}
