package strengthdetailscalculator.service.interfaces;

import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.ResponseCovered;
import strengthdetailscalculator.utils.response.Response;

import java.util.List;

public interface DetailChecked extends ResponseCovered {

    default Response checkDetailData(List<String> textDetailData, List<String> numericalDetailData){
        InputDataManager inputDataManager = new InputDataManager();
        Response errorDetailTextData = inputDataManager.checkTextData(textDetailData);
        Response errorNumericalDetailData = inputDataManager.checkPositiveNumericalData(numericalDetailData);
        return coverToResponse(errorDetailTextData.getDescription() + errorNumericalDetailData.getDescription());
    }
}
