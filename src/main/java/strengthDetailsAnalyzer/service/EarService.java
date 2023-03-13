package strengthDetailsAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Ear;
import strengthDetailsAnalyzer.entity.enums.EarType;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static strengthDetailsAnalyzer.controller.EarController.*;
import static strengthDetailsAnalyzer.entity.enums.EarType.*;

@Component
public class EarService extends DetailService {

    private final DocumentWriter documentWriter;

    @Autowired
    public EarService(DocumentWriter documentWriter, InputDataManager inputDataManager) {
        super(inputDataManager);
        this.documentWriter = documentWriter;
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, ArrayList<String> data) {
        Ear ear = build(detail, data);
        Response response = documentWriter.writeEar(ear);
        return response;
    }

    public Ear build(Detail detail, List<String> data) {
        Double outerDiameter = Double.valueOf(data.get(INDEX_OUTER_D));
        Double internalDiameter = Double.valueOf(data.get(INDEX_INTERNAL_D));
        Double thickness = Double.valueOf(data.get(INDEX_THICKNESS));
        Double eccentricity = Double.valueOf(data.get(INDEX_ECCENTRICITY));
        Boolean isSingleShearedConnection = Boolean.valueOf(data.get(INDEX_IS_SINGLE_SHEARED_CONNECTION));
        Boolean currentEarIsLarger = Boolean.valueOf(data.get(INDEX_IS_EAR_LARGER));
        EarType earType = getTypeEar(data.get(INDEX_TYPE_EAR), isSingleShearedConnection);
        Double gap = Double.valueOf(prepareDataGap(data.get(INDEX_GAP)));
        Double force = Double.valueOf(data.get(INDEX_FORCE));
        Ear ear = new Ear(detail, outerDiameter, internalDiameter, thickness, eccentricity,
                isSingleShearedConnection, earType, currentEarIsLarger, gap, force);
        return ear;
    }

    @Override
    protected Response checkData(ArrayList<String> data) {
        List<String> positiveData = List.of(data.get(INDEX_OUTER_D), data.get(INDEX_INTERNAL_D), data.get(INDEX_THICKNESS), data.get(INDEX_FORCE));
        List<String> mainNumericalData = List.of(data.get(INDEX_ECCENTRICITY));
        Response checkNonZeroProperties = inputDataManager.checkPositiveNumericalData(positiveData);
        Response checkMainNumericalData = inputDataManager.checkMainNumericalData(mainNumericalData);
        return coverToResponse(checkNonZeroProperties.getDescription() + checkMainNumericalData.getDescription());
    }

    @Override
    protected ArrayList<String> prepareData(ArrayList<String> data) {
        ArrayList<String> clarifyNullValueData = inputDataManager.prepareNullableString(data, INDEX_TYPE_EAR, "");
        ArrayList<String> processedData = inputDataManager.replaceCommasWithDots(clarifyNullValueData);
        return processedData;
    }

    private EarType getTypeEar(String stringTypeEar, Boolean isSingleShearedConnection) {
        if (!isSingleShearedConnection) {
            switch (stringTypeEar) {
                case "Стальная и центральная":
                    return STEEL_CENTRAL;
                case "Стальная и боковая":
                    return STEEL_SIDE;
                case "Алюминиевая боковая":
                    return ALUMINUM_SIDE;
                default:
                    return SINGLE;
            }
        } else return SINGLE;
    }

    private String prepareDataGap(String textGap) {
        if (textGap.length() == 0) {
            return textGap = "0";
        }
        return textGap;
    }
}
