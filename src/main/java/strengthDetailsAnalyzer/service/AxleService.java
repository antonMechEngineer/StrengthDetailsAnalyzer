package strengthDetailsAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.controller.AxleController;
import strengthDetailsAnalyzer.entity.Axle;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Pin;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;

import java.util.List;

@Component
public class AxleService extends PinService {

    @Autowired
    public AxleService(InputDataManager inputDataManager, DocumentWriter documentWriter) {
        super(inputDataManager, documentWriter);
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, List<String> data){
        Axle axle = build(detail, data);
        Response resAxleProperties = inputDataManager.checkNonZeroNumericalData(List.of(axle.getSupportLength().toString()));
        if (resAxleProperties.getResponseStatus() == ResponseStatus.FAIL) {
            return resAxleProperties;
        }
        Response response = documentWriter.writeAxle(axle);
        return response;
    }

    @Override
    protected Axle build(Detail detail, List<String> data){
        Pin pin = super.build(detail, data) ;
        String supportLength =  data.get(AxleController.INDEX_NUMBER_SUPPORT_LENGTH);
        Axle axle = new Axle(pin, Double.valueOf(supportLength));
        return axle;
    }

    @Override
    protected Response checkData(List<String> data) {
        Response pinResponse = super.checkData(data);
        Response checkLength = inputDataManager.checkPositiveNumericalData(List.of(data.get(AxleController.INDEX_NUMBER_SUPPORT_LENGTH)));
        return coverToResponse(pinResponse.getDescription() + checkLength.getDescription());
    }


}
