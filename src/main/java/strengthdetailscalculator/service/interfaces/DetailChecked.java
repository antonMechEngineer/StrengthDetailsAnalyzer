package strengthdetailscalculator.service.interfaces;
import javafx.scene.control.TextField;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.ResponseCovered;
import strengthdetailscalculator.utils.response.Response;
import java.util.List;

public interface DetailChecked extends ResponseCovered {

    default Response checkDetailData(List<TextField> textDetailData, List<TextField> numericalDetailData){
        InputDataManager inputDataManager = new InputDataManager();
        Response errorDetailTextData = inputDataManager.checkTextData(textDetailData);
        Response errorNumericalDetailData = inputDataManager.checkPositiveNumericalData(numericalDetailData);
        return coverToResponse(errorDetailTextData.getDescription() + errorNumericalDetailData.getDescription());
    }
}
