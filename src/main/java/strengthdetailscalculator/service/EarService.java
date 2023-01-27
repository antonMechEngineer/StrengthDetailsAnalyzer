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

public class EarService extends DetailService {

    public EarService(DocumentWriter documentWriter, InputDataManager inputDataManager) {
        super(documentWriter, inputDataManager);
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, List<String> data) {

        return new Response(ResponseStatus.SUCCESS);
    }

    public Ear build(Detail detail, List<String> data){
        Double outerDiameter = Double.valueOf(data.get(INDEX_OUTER_D));
        Double internalDiameter = Double.valueOf(data.get(INDEX_INTERNAL_D));
        Double thickness = Double.valueOf(data.get(INDEX_THICKNESS));
        Double eccentricity = Double.valueOf(data.get(INDEX_ECCENTRICITY));
        EarType earType = getTypeEar(data.get(INDEX_TYPE_EAR));
        Boolean isSingleShearedConnection = Boolean.valueOf(data.get(INDEX_IS_SINGLE_SHEARED_CONNECTION));
        Boolean currentEarIsLarger = Boolean.valueOf(data.get(INDEX_IS_SINGLE_SHEARED_CONNECTION));
        Double gap = Double.valueOf(data.get(INDEX_GAP));
        Ear ear = new Ear(detail, outerDiameter, internalDiameter, thickness, eccentricity,
                isSingleShearedConnection, earType, currentEarIsLarger, gap);
        return ear;
    }

    @Override
    protected Response checkData(List<String> data) {

        return new Response(ResponseStatus.SUCCESS);
    }

    @Override
    protected List<String> prepareData(List<String> data) {
        List<String> processedData = inputDataManager.replaceCommasWithDots(data);
        return processedData;
    }

    private EarType getTypeEar(String stringTypeEar) {
        switch (stringTypeEar) {
            case "STEEL_CENTRAL":
                return EarType.STEEL_CENTRAL;
            case "STEEL_SIDE":
                return EarType.STEEL_SIDE;
            default:
                return EarType.ALUMINUM_SIDE;
        }
    }
}
