package strengthdetailscalculator.service;
import javafx.scene.control.TextField;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.service.interfaces.DetailChecked;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;
import java.util.List;

@SuppressWarnings("UnnecessaryLocalVariable")
public class PinService extends DetailService implements DetailChecked {


    public Response write(List<TextField> textDetailData,
                          List<TextField> numericalDetailData, List<TextField> numericalPinData){
        Response preProcessResponse = preProcessDetailData(textDetailData, numericalDetailData, numericalPinData);
        if (preProcessResponse.getResponseStatus() == ResponseStatus.SUCCESS) {
            Detail detail = new Detail(textDetailData, numericalDetailData);
            Pin pin = build(detail, numericalPinData);
            documentWriter.writePin(pin);
            return new Response(ResponseStatus.SUCCESS);
        }
        return preProcessResponse;
    }

    public Pin build(Detail detail, List<TextField> numericalPinData){
        Double outerDiameter = Double.valueOf(numericalPinData.get(0).getText());
        Double internalDiameter = Double.valueOf(numericalPinData.get(1).getText());
        Double numberShearSection = Double.valueOf(numericalPinData.get(2).getText()); // TODO: 12.01.2023 поставить защиту от дробных чисел
        Pin pin = new Pin(detail, outerDiameter, internalDiameter, numberShearSection);
        return pin;
    }

    @Override
    protected Response getResultChecking(List<TextField> textDetailData, List<TextField> numericalDetailData, List<TextField> pinNumericalData) {
        Response errorDetailData = checkDetailData(textDetailData, numericalDetailData);
        Response errorNumericalData = inputDataManager.checkMainNumericalData(pinNumericalData);
        return coverToResponse(errorDetailData.getDescription() + errorNumericalData.getDescription());
    }
}
