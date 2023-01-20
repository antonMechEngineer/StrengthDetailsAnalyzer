package strengthdetailscalculator.service;

import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.service.interfaces.DetailChecked;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.util.List;

import static strengthdetailscalculator.controller.PinController.*;

@SuppressWarnings("UnnecessaryLocalVariable")
public class PinService extends DetailService implements DetailChecked {


    public PinService(DocumentWriter documentWriter, InputDataManager inputDataManager) {
        super(documentWriter, inputDataManager);
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, List<String> data) {
        Pin pin = build(detail, data);
        documentWriter.writePin(pin);
        return new Response(ResponseStatus.SUCCESS);
    }

    protected Pin build(Detail detail, List<String> data) {
        Double outerDiameter = Double.valueOf(data.get(INDEX_OUTER_DIAMETER));
        Double internalDiameter = Double.valueOf(data.get(INDEX_INTERNAL_DIAMETER));
        Double numberShearSection = Double.valueOf(data.get(INDEX_NUMBER_SHEAR_SECTION));
        Pin pin = new Pin(detail, outerDiameter, internalDiameter, numberShearSection);
        return pin;
    }

    @Override
    protected Response checkData(List<String> data) {
        Response resPinData0 = inputDataManager.checkMainNumericalData(List.of(data.get(INDEX_INTERNAL_DIAMETER)));
        Response resPinData1 = inputDataManager.checkNonZeroNumericalData(List.of(data.get(INDEX_OUTER_DIAMETER)));
        return coverToResponse(resPinData0.getDescription() + resPinData1.getDescription());
    }

    @Override
    protected List<String> prepareData(List<String> data) {
        List<String> processedData = inputDataManager.replaceCommasWithDots(data);
        return processedData;
    }
}
