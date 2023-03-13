package strengthDetailsAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Pin;
import strengthDetailsAnalyzer.service.interfaces.DetailChecked;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static strengthDetailsAnalyzer.controller.PinController.*;

@Component
public class PinService extends DetailService implements DetailChecked {

    protected final DocumentWriter documentWriter;

    @Autowired
    public PinService(InputDataManager inputDataManager, DocumentWriter documentWriter) {
        super(inputDataManager);
        this.documentWriter = documentWriter;
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, ArrayList<String> data) {
        Pin pin = build(detail, data);
        Response response = documentWriter.writePin(pin);
        return response;
    }

    protected Pin build(Detail detail, List<String> data) {
        Double outerDiameter = Double.valueOf(data.get(INDEX_OUTER_DIAMETER));
        Double internalDiameter = Double.valueOf(data.get(INDEX_INTERNAL_DIAMETER));
        Double numberShearSection = Double.valueOf(data.get(INDEX_NUMBER_SHEAR_SECTION));
        Double force = Double.valueOf(data.get(INDEX_FORCE));
        Pin pin = new Pin(detail, outerDiameter, internalDiameter, numberShearSection, force);
        return pin;
    }

    @Override
    protected Response checkData(ArrayList<String> data) {
        Response resPinData0 = inputDataManager.checkMainNumericalData(List.of(data.get(INDEX_INTERNAL_DIAMETER)));
        Response resPinData1 = inputDataManager.checkNonZeroNumericalData(List.of(data.get(INDEX_OUTER_DIAMETER), data.get(INDEX_FORCE)));
        return coverToResponse(resPinData0.getDescription() + resPinData1.getDescription());
    }

    @Override
    protected ArrayList<String> prepareData(ArrayList<String> data) {
        ArrayList<String> processedData = inputDataManager.replaceCommasWithDots(data);
        return processedData;
    }
}
