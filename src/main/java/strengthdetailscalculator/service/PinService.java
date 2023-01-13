package strengthdetailscalculator.service;

import javafx.scene.control.TextField;
import strengthdetailscalculator.controller.PinSceneController;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.service.interfaces.DetailChecked;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.util.List;

@SuppressWarnings("UnnecessaryLocalVariable")
public class PinService extends DetailService implements DetailChecked {

    public Response write(List<TextField> textDetailData,
                          List<TextField> numericalDetailData, List<TextField> pinData) {
        Response preProcessResponse = preProcessDetailData(textDetailData, numericalDetailData, pinData);
        if (preProcessResponse.getResponseStatus() == ResponseStatus.SUCCESS) {
            Detail detail = new Detail(textDetailData, numericalDetailData);
            Pin pin = build(detail, pinData);
            documentWriter.writePin(pin);
            return new Response(ResponseStatus.SUCCESS);
        }
        return preProcessResponse;
    }

    public Pin build(Detail detail, List<TextField> pinData) {
        Double outerDiameter = Double.valueOf(pinData.get(0).getText());
        Double internalDiameter = Double.valueOf(pinData.get(1).getText());
        Double numberShearSection = Double.valueOf(pinData.get(2).getText());
        Pin pin = new Pin(detail, outerDiameter, internalDiameter, numberShearSection);
        return pin;
    }

    @Override
    protected Response getResultChecking(List<TextField> textDetailData, List<TextField> numericalDetailData, List<TextField> pinData) {
        Response responseDetailData = checkDetailData(textDetailData, numericalDetailData);
        Response responseNumericalData = inputDataManager.checkMainNumericalData(pinData);
        Response responsePinData0 = inputDataManager.checkMainNumericalData(List.of(pinData.get(PinSceneController.INDEX_INTERNAL_DIAMETER)));
        Response responsePinData1 = inputDataManager.checkNonZeroNumericalData(List.of(pinData.get(PinSceneController.INDEX_OUTER_DIAMETER)));
        Response responsePinData2 = inputDataManager.checkNaturalNumericalData(List.of(pinData.get(PinSceneController.INDEX_NUMBER_SHEAR_SECTION)));
        return coverToResponse(responseDetailData.getDescription() + responseNumericalData.getDescription()
                + responsePinData0.getDescription() + responsePinData1.getDescription() + responsePinData2.getDescription());
    }
}
