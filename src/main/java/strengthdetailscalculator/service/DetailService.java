package strengthdetailscalculator.service;

import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.service.interfaces.DetailChecked;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.ResponseCovered;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.util.List;

public abstract class DetailService implements ResponseCovered, DetailChecked {

    protected final DocumentWriter documentWriter = new DocumentWriter();
    protected final InputDataManager inputDataManager = new InputDataManager();
    protected abstract Response writeSpecifiedDetail(Detail detail, List<String> data);
    protected abstract Response checkData(List<String> data);
    protected abstract List<String> prepareData(List<String> data);




    public Response write(List<String> textDetailData, List<String> numericalDetailData, List<String> data) {
        Response preProcessData = preProcessData(textDetailData, numericalDetailData, data);
        if (preProcessData.getResponseStatus() == ResponseStatus.SUCCESS) {
            Detail detail = new Detail(textDetailData, numericalDetailData);
            Response response = writeSpecifiedDetail(detail, data);
            return response;
        }
        return preProcessData;
    }

    private Response preProcessData(List<String> textDetailData, List<String> numericalDetailData, List<String> data) {
        List<String> processedNumericalDetailData = inputDataManager.replaceCommasWithDots(numericalDetailData);
        Response detailChecking = checkDetailData(textDetailData, processedNumericalDetailData);
        List<String> processedData = prepareData(data);
        Response dataChecking = checkData(data);
        return coverToResponse(detailChecking.getDescription() + dataChecking.getDescription());
    }
}

