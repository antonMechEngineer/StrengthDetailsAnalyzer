package strengthdetailscalculator.service;

import javafx.scene.control.TextField;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.ResponseCovered;
import strengthdetailscalculator.utils.response.Response;

import java.util.List;

public abstract class DetailService implements ResponseCovered {

    protected final DocumentWriter documentWriter = new DocumentWriter();
    protected final InputDataManager inputDataManager = new InputDataManager();

    protected Response preProcessDetailData(List<TextField> textDetailData, List<TextField> numericalDetailData, List<TextField> numericalData) {
        prepareData(textDetailData, numericalDetailData, numericalData);
        return getResultChecking(textDetailData, numericalDetailData, numericalData);
    }

    private void prepareData(List<TextField> textDetailData, List<TextField> numericalDetailData, List<TextField> numericalData) {
        inputDataManager.prepareNumericalData(numericalDetailData);
        inputDataManager.prepareTextData(textDetailData);
        inputDataManager.prepareNumericalData(numericalData);
    }

    protected abstract Response getResultChecking(List<TextField> textDetailData, List<TextField> numericalDetailData, List<TextField> numericalData);

}

