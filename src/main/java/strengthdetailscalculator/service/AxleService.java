package strengthdetailscalculator.service;

import strengthdetailscalculator.controller.AxleController;
import strengthdetailscalculator.entity.Axle;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.util.List;

public final class AxleService extends PinService {

    protected final DocumentWriter documentWriter = new DocumentWriter();

    @Override
    protected Response writeSpecifiedDetail(Detail detail, List<String> data){
        Axle axle = build(detail, data);
        Response resAxleProperties = inputDataManager.checkNonZeroNumericalData(List.of(axle.getSupportLength().toString()));
        if (resAxleProperties.getResponseStatus() == ResponseStatus.FAIL) {
            return resAxleProperties;
        }
        documentWriter.writeAxle(axle);
        return new Response(ResponseStatus.SUCCESS);
    }

    @Override
    protected Axle build(Detail detail, List<String> data){
        Pin pin = super.build(detail, data) ;
        String supportLength =  data.get(AxleController.INDEX_NUMBER_SUPPORT_LENGTH);
        return new Axle(pin, Double.valueOf(supportLength));
    }

    @Override
    protected Response checkData(List<String> data) {
        Response pinResponse = super.checkData(data);
        Response checkLength = inputDataManager.checkPositiveNumericalData(List.of(data.get(AxleController.INDEX_NUMBER_SUPPORT_LENGTH)));
        return coverToResponse(pinResponse.getDescription() + checkLength.getDescription());
    }


}
