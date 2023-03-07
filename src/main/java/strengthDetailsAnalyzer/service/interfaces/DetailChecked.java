package strengthDetailsAnalyzer.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import strengthDetailsAnalyzer.controller.DetailController;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.ResponseCovered;
import strengthDetailsAnalyzer.utils.response.Response;

import java.util.List;

import static strengthDetailsAnalyzer.controller.DetailController.INDEX_USER_SAFETY_FACTOR;

public interface DetailChecked extends ResponseCovered {

    default Response checkDetailData(InputDataManager inputDataManager, List<String> textDetailData, List<String> numericalDetailData){
        Response errorDetailTextData = inputDataManager.checkTextData(textDetailData);
        if(!Boolean.parseBoolean(textDetailData.get(DetailController.INDEX_IS_USER_SAFETY_FACTOR))){
            numericalDetailData = numericalDetailData.subList(0,INDEX_USER_SAFETY_FACTOR);
        }
        Response errorNumericalDetailData = inputDataManager.checkPositiveNumericalData(numericalDetailData);
        return coverToResponse(errorDetailTextData.getDescription() + errorNumericalDetailData.getDescription());
    }
}
