package strengthdetailscalculator.service.interfaces;
import javafx.scene.control.TextField;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.ResponseCovered;
import strengthdetailscalculator.utils.response.Response;
import java.util.List;

public interface FullChecked extends ResponseCovered {

    default Response fullCheckDetailData(List<TextField> textDetailData, List<TextField> numericalDetailData, List<TextField> numericalData){
        InputDataManager inputDataManager = new InputDataManager();
        Response errorDetailTextData = inputDataManager.checkTextData(textDetailData);
        Response errorNumericalDetailData = inputDataManager.checkPositiveNumericalData(numericalDetailData);
        Response errorNumericalData = inputDataManager.checkPositiveNumericalData(numericalData);
        return coverToResponse(errorDetailTextData.getDescription() + errorNumericalDetailData.getDescription() + errorNumericalData.getDescription());
    }
}
