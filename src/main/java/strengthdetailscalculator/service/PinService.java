package strengthdetailscalculator.service;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import strengthdetailscalculator.controller.PinController;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.service.interfaces.DetailChecked;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.util.List;

@SuppressWarnings("UnnecessaryLocalVariable")
public class PinService extends DetailService implements DetailChecked {

    @Override
    protected Response writeSpecifiedDetail(Detail detail, List<Parent> data){
        Pin pin = build(detail, data);
        documentWriter.writePin(pin);
        return new Response(ResponseStatus.SUCCESS);
    }

    protected Pin build(Detail detail, List<Parent> data) {
        List<TextField> castedData = safetyCastToTextFields(data);
        ComboBox<String> castedComboBox = (ComboBox<String>) data.get(2);
        Double outerDiameter = Double.valueOf(castedData.get(0).getText());
        Double internalDiameter = Double.valueOf(castedData.get(1).getText());
        Double numberShearSection = Double.valueOf(castedComboBox.getValue());
        Pin pin = new Pin(detail, outerDiameter, internalDiameter, numberShearSection);
        return pin;
    }

    @Override
    protected Response getResultChecking(List<TextField> textDetailData, List<TextField> numericalDetailData, List<Parent> data) {
        Response responseDetailData = checkDetailData(textDetailData, numericalDetailData);
        List<TextField> castedData = safetyCastToTextFields(data);
        Response responseNumericalData = inputDataManager.checkMainNumericalData(castedData);
        Response responsePinData0 = inputDataManager.checkMainNumericalData(List.of(castedData.get(PinController.INDEX_INTERNAL_DIAMETER)));
        Response responsePinData1 = inputDataManager.checkNonZeroNumericalData(List.of(castedData.get(PinController.INDEX_OUTER_DIAMETER)));
        return coverToResponse(responseDetailData.getDescription() + responseNumericalData.getDescription()
                + responsePinData0.getDescription() + responsePinData1.getDescription());
    }
}
