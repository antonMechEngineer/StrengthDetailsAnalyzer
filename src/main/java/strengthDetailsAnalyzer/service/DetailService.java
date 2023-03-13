package strengthDetailsAnalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.service.interfaces.DetailChecked;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.ResponseCovered;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;

import java.util.ArrayList;

import static strengthDetailsAnalyzer.controller.DetailController.INDEX_USER_SAFETY_FACTOR;
import static strengthDetailsAnalyzer.entity.Detail.DEFAULT_MIN_SAFETY_FACTOR;

public abstract class DetailService implements ResponseCovered, DetailChecked {


    protected final InputDataManager inputDataManager;

    @Autowired
    public DetailService(InputDataManager inputDataManager) {
        this.inputDataManager = inputDataManager;
    }

    protected abstract Response writeSpecifiedDetail(Detail detail, ArrayList<String> data);

    protected abstract Response checkData(ArrayList<String> data);

    protected abstract ArrayList<String> prepareData(ArrayList<String> data);

    public Response write(ArrayList<String> textDetailData, ArrayList<String> numericalDetailData, ArrayList<String> data) {
        ArrayList<String> preparedNumDetailData = prepareNumericalDetailData(numericalDetailData);
        ArrayList<String> preparedData = prepareData(data);
        Response preProcessData = checkData(textDetailData, preparedNumDetailData, preparedData);
        if (preProcessData.getResponseStatus() == ResponseStatus.SUCCESS) {
            System.out.println(preparedData);
            Detail detail = new Detail(textDetailData, preparedNumDetailData);
            Response response = writeSpecifiedDetail(detail, preparedData);
            return response;
        }
        return preProcessData;
    }

    private Response checkData(ArrayList<String> textDetailData, ArrayList<String> numericalDetailData, ArrayList<String> data) {
        Response detailChecking = checkDetailData(inputDataManager, textDetailData, numericalDetailData);
        Response dataChecking = checkData(data);
        return coverToResponse(detailChecking.getDescription() + dataChecking.getDescription());
    }

    private ArrayList<String> prepareNumericalDetailData(ArrayList<String> numericalDetailData){
        ArrayList<String> replacedCommasWithDots = inputDataManager.replaceCommasWithDots(numericalDetailData);
        ArrayList<String> processedNumDetailData = inputDataManager.prepareNullableString(replacedCommasWithDots, INDEX_USER_SAFETY_FACTOR, DEFAULT_MIN_SAFETY_FACTOR.toString());
        return processedNumDetailData;
    }
}

