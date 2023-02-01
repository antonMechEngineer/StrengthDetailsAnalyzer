package strengthdetailscalculator.service;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Ear;
import strengthdetailscalculator.entity.enums.EarType;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;
import java.util.List;
import static strengthdetailscalculator.controller.EarController.*;
import static strengthdetailscalculator.entity.enums.EarType.*;

public class EarService extends DetailService {

    public EarService(DocumentWriter documentWriter, InputDataManager inputDataManager) {
        super(documentWriter, inputDataManager);
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, List<String> data) {
        Ear ear = build(detail, data);
        documentWriter.writeEar(ear);
        return new Response(ResponseStatus.SUCCESS);
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
        Ear ear = new Ear(detail, outerDiameter, internalDiameter, thickness, eccentricity,
                isSingleShearedConnection, earType, currentEarIsLarger, gap);
        return ear;
    }

    @Override
    protected Response checkData(List<String> data) {
        List<String> positiveData = List.of(data.get(INDEX_OUTER_D), data.get(INDEX_INTERNAL_D), data.get(INDEX_THICKNESS));
        List<String> mainNumericalData = List.of(data.get(INDEX_ECCENTRICITY));
        Response checkNonZeroProperties = inputDataManager.checkPositiveNumericalData(positiveData);
        Response checkMainNumericalData = inputDataManager.checkMainNumericalData(mainNumericalData);
        return coverToResponse(checkNonZeroProperties.getDescription() + checkMainNumericalData.getDescription());
    }

    @Override
    protected List<String> prepareData(List<String> data) {
        List<String> clarifyNullValueData = inputDataManager.prepareNullableString(data, INDEX_TYPE_EAR);
        List<String> processedData = inputDataManager.replaceCommasWithDots(clarifyNullValueData);
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
