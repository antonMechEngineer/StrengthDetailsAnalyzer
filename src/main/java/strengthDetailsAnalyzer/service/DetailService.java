package strengthDetailsAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import strengthDetailsAnalyzer.controller.DetailController;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.service.interfaces.DetailChecked;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.ResponseCovered;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;

import java.util.List;

import static strengthDetailsAnalyzer.entity.Detail.DEFAULT_MIN_SAFETY_FACTOR;

public abstract class DetailService implements ResponseCovered, DetailChecked {


    protected final InputDataManager inputDataManager;

    @Autowired
    public DetailService(InputDataManager inputDataManager) {
        this.inputDataManager = inputDataManager;
    }

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
        processedNumericalDetailData = inputDataManager.prepareNullableString(processedNumericalDetailData, DetailController.INDEX_USER_SAFETY_FACTOR, DEFAULT_MIN_SAFETY_FACTOR.toString());
        Response detailChecking = checkDetailData(inputDataManager, textDetailData, processedNumericalDetailData);
        List<String> processedData = prepareData(data);
        Response dataChecking = checkData(data);
        return coverToResponse(detailChecking.getDescription() + dataChecking.getDescription());
    }
}

