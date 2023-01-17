package strengthdetailscalculator.service;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.ResponseCovered;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class DetailService implements ResponseCovered {

    protected final DocumentWriter documentWriter = new DocumentWriter();
    protected final InputDataManager inputDataManager = new InputDataManager();

    protected abstract Response writeSpecifiedDetail(Detail detail, List<Parent> data);
    protected abstract Response getResultChecking(List<TextField> textDetailData, List<TextField> numericalDetailData, List<Parent> data);

    public Response write(List<TextField> textDetailData, List<TextField> numericalDetailData, List<Parent> data) {
        Response preProcessResponse = preProcessDetailData(textDetailData, numericalDetailData, data);
        if (preProcessResponse.getResponseStatus() == ResponseStatus.SUCCESS) {
            Detail detail = new Detail(textDetailData, numericalDetailData);
            Response response = writeSpecifiedDetail(detail, data);
            return response;
        }
        return preProcessResponse;
    }

    protected Response preProcessDetailData(List<TextField> textDetailData, List<TextField> numericalDetailData, List<Parent> data) {
        prepareData(textDetailData, numericalDetailData, data);
        return getResultChecking(textDetailData, numericalDetailData, data);
    }

    private void prepareData(List<TextField> textDetailData, List<TextField> numericalDetailData, List<Parent> data) {
        inputDataManager.prepareNumericalData(numericalDetailData);
        inputDataManager.prepareTextData(textDetailData);
        inputDataManager.prepareNumericalData(safetyCastToTextFields(data));
    }

    protected List<TextField> safetyCastToTextFields(List<Parent> data){
        List<TextField> list = new ArrayList<>();
        for (Parent dataMember : data) {
            if (dataMember.getClass().equals(TextField.class)) {
                TextField textField = (TextField) dataMember;
                list.add(textField);
            }
        }
        return list;
    }

}

