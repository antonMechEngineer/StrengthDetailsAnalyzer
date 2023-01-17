package strengthdetailscalculator.service;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
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

    protected Response writeSpecifiedDetail(Detail detail, List<Parent> data){
        Axle axle = build(detail, data);
        Response resAxleProperties = inputDataManager.checkInputAxleProperties(axle);
        if (resAxleProperties.getResponseStatus() == ResponseStatus.FAIL) {
            return resAxleProperties;
        }
        documentWriter.writeAxle(axle);
        return new Response(ResponseStatus.SUCCESS);


    }

    protected Axle build(Detail detail, List<Parent> data){
        Pin pin = super.build(detail, data) ;
        TextField supportLengthTextField = (TextField) data.get(AxleController.INDEX_NUMBER_SUPPORT_LENGTH);
        return new Axle(pin, Double.valueOf(supportLengthTextField.getText()));
    }

}
