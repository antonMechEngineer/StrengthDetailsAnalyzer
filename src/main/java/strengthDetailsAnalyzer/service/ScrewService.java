package strengthDetailsAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.controller.ScrewController;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Screw;
import strengthDetailsAnalyzer.entity.enums.ScrewType;
import strengthDetailsAnalyzer.utils.DataLoader;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static strengthDetailsAnalyzer.controller.ScrewController.*;
import static strengthDetailsAnalyzer.entity.enums.ScrewType.METRICAL;
import static strengthDetailsAnalyzer.entity.enums.ScrewType.TRAPEZOIDAL;
import static strengthDetailsAnalyzer.utils.response.ResponseStatus.FAIL;

@Component
public class ScrewService extends DetailService {

    private static final Integer INDEX_MID_D = 0;
    private static final Integer INDEX_INTERNAL_D = 1;
    private static final Integer INDEX_MIN_D = 2;
    private HashMap<Double, HashMap<Double, List<Double>>> metricalScrewData;
    private final HashMap<Double, HashMap<Double, List<Double>>> trapezoidalScrewData;
    private final DocumentWriter documentWriter;

    @Autowired
    public ScrewService(DocumentWriter documentWriter, InputDataManager inputDataManager, DataLoader dataLoader) {
        super(inputDataManager);
        this.metricalScrewData = dataLoader.getMetricalScrews();
        this.documentWriter = documentWriter;
        this.metricalScrewData = dataLoader.getMetricalScrews();
        this.trapezoidalScrewData = dataLoader.getTrapezoidalScrews();
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, ArrayList<String> data) {
        Screw screw = build(detail, data);
        Response resThreadProperties = inputDataManager.checkInputThreadProperties(screw);
        if (resThreadProperties.getResponseStatus() == FAIL) {
            return resThreadProperties;
        }
        return documentWriter.writeScrew(screw);

    }

    @Override
    protected Response checkData(ArrayList<String> data) {
        ArrayList<String> numericalData = new ArrayList<>(Arrays.asList(data.get(INDEX_MAIN_D), data.get(INDEX_HEIGHT), data.get(INDEX_FORCE)));
        Boolean isSmallPitch = Boolean.valueOf(data.get(INDEX_IS_SMALL_PITCH));
        if (isSmallPitch) {
            numericalData.add(data.get(INDEX_THREAD_PITCH));
        }
        Response response = inputDataManager.checkPositiveNumericalData(numericalData);
        return response;
    }

    @Override
    protected ArrayList<String> prepareData(ArrayList<String> data) {
        ArrayList<String> processedData = inputDataManager.replaceCommasWithDots(data);
        return processedData;
    }

    private Screw build(Detail detail, List<String> data) {
        ScrewType screwType = getScrewType(data.get(ScrewController.INDEX_TYPE_SCREW));
        Double mainD = Double.valueOf(data.get(INDEX_MAIN_D));
        Double height = Double.valueOf(data.get(INDEX_HEIGHT));
        Double force = Double.valueOf(data.get(INDEX_FORCE));
        Boolean isSmallThreadPitch = Boolean.valueOf(data.get(INDEX_IS_SMALL_PITCH));
        if (isSmallThreadPitch) {
            Double threadPitch = Double.valueOf(data.get(INDEX_THREAD_PITCH));
            Screw screw = new Screw(detail,
                    mainD, threadPitch, height, screwType,
                    getData(mainD, threadPitch, screwType, INDEX_INTERNAL_D),
                    getData(mainD, threadPitch, screwType, INDEX_MIN_D), force);
            return screw;
        } else {
            return new Screw(detail,
                    mainD, getData(mainD, screwType, INDEX_THREAD_PITCH), height, screwType,
                    getData(mainD, screwType, INDEX_INTERNAL_D),
                    getData(mainD, screwType, INDEX_MIN_D), force);
        }
    }

    private Double getData(Double mainD, ScrewType screwType, Integer indexData) {
        Double dataValue = (double) 0;
        try {
            switch (screwType) {
                case METRICAL:
                    Double maxPitchMetrical = metricalScrewData.get(mainD).keySet().stream().mapToDouble(v -> v).max().orElseThrow();
                    if (indexData.equals(INDEX_THREAD_PITCH)) {
                        return maxPitchMetrical;
                    }
                    dataValue = metricalScrewData.get(mainD).get(maxPitchMetrical).get(indexData);
                    break;
                case TRAPEZOIDAL:
                    Double maxPitchTrapezoidal = metricalScrewData.get(mainD).keySet().stream().mapToDouble(v -> v).max().orElseThrow();
                    if (indexData.equals(INDEX_THREAD_PITCH)) {
                        return maxPitchTrapezoidal;
                    }
                    dataValue = trapezoidalScrewData.get(mainD).get(maxPitchTrapezoidal).get(indexData);
                    break;
            }
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
        return dataValue;
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

    private ScrewType getScrewType(String name) {
        if (name.equals("Трапецеидальная")) {
            return TRAPEZOIDAL;
        }
        return METRICAL;
    }
}
