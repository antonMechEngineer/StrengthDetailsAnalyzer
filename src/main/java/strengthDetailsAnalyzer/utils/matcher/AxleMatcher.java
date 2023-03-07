package strengthDetailsAnalyzer.utils.matcher;

import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Axle;
import strengthDetailsAnalyzer.utils.matcher.interfaces.BendingReportGenerated;
import strengthDetailsAnalyzer.utils.matcher.interfaces.VonMissesReportGenerated;
import java.util.HashMap;
import static strengthDetailsAnalyzer.entity.enums.MomentsType.*;

@Component
public class AxleMatcher extends PinMatcher implements VonMissesReportGenerated, BendingReportGenerated {

    public static final String SUPPORT_LENGTH = "${supportLength}";

    public HashMap<String, String> getMapTemplateAxle(Axle axle) {
        HashMap<String, String> mapTemplate = getMapTemplatePin(axle);
        insertAxleProperties(mapTemplate, axle);
        insertMomentDescription(mapTemplate, axle);
        insertBendingResult(mapTemplate, axle);
        insertBendingConclusion(mapTemplate, axle);
        insertVonMissesResult(mapTemplate, axle);
        insertVonMissesConclusion(mapTemplate, axle);
        return mapTemplate;
    }

    private void insertMomentDescription(HashMap<String, String> mapTemplate, Axle axle) {
        if (axle.getNumberShearSection() == 1) {
            String expression = String.format("%,.0f", axle.getForce()) + " ∙ " + String.format("%,.0f", axle.getSupportLength());
            putMoment(mapTemplate, CONSOLE.equation, expression);
        } else if (axle.getNumberShearSection() == 2) {
            String expression = String.format("%,.0f", axle.getForce()) + " ∙ " + String.format("%,.0f", axle.getSupportLength()) + " / 4";
            putMoment(mapTemplate, DOUBLE_SUPPORTED.equation, expression);
        } else if (axle.getNumberShearSection() == 3) {
            String expression = String.format("%,.0f", axle.getForce()) + " ∙ " + String.format("%,.0f", axle.getSupportLength()) + " / 64";
            putMoment(mapTemplate, TRIPLE_SUPPORTED.equation, expression);
        } else if (axle.getNumberShearSection() == 4) {
            String expression = "3" + " ∙ " + String.format("%,.0f", axle.getForce()) + " ∙ " + String.format("%,.0f", axle.getSupportLength()) + " / 128";
            putMoment(mapTemplate, QUAD_SUPPORTED.equation, expression);
        }
    }

    private void putMoment(HashMap<String, String> mapTemplate, String equation, String expression) {
        mapTemplate.put(EQUATION_BENDING_MOMENT, equation);
        mapTemplate.put(CALCULATION_BENDING_MOMENT, expression);
    }

    private void insertAxleProperties(HashMap<String, String> mapTemplate, Axle axle){
        mapTemplate.put(SUPPORT_LENGTH, String.format("%,.0f", axle.getSupportLength()));
    }
}
