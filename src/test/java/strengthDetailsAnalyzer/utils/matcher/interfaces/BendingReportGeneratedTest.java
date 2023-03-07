package strengthDetailsAnalyzer.utils.matcher.interfaces;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.Axle;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Pin;
import strengthDetailsAnalyzer.utils.matcher.AxleMatcher;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class BendingReportGeneratedTest {

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final Pin pin = new Pin(detail, 20d, 0d, 1d);
    private final Axle axle = new Axle(pin, 50d);
    private final AxleMatcher axleMatcher = new AxleMatcher();

    @Test
    public void insertBendConclusion() {
        HashMap<String, String> expectedReportGenerated = new HashMap<>();
        String sign = "${bendingSign}";
        String conclusion = "${bendingConclusion}";
        expectedReportGenerated.put(sign, ">");
        expectedReportGenerated.put(conclusion, "Условие прочности выполняется.");
        HashMap<String, String> actualReportGenerated = new HashMap<>();
        axleMatcher.insertBendingConclusion(actualReportGenerated, axle);
        assertEquals(expectedReportGenerated, actualReportGenerated);
    }

    @Test
    public void insertBendingResult(){
        HashMap<String, String> expectedMapTemplate = buildExpectedMapTemplate();
        HashMap<String, String> actualMapTemplate = axleMatcher.getMapTemplateAxle(axle);
        assertEquals(expectedMapTemplate.get(AxleMatcher.BENDING_SAFETY_FACTOR), actualMapTemplate.get(AxleMatcher.BENDING_SAFETY_FACTOR));
        assertEquals(expectedMapTemplate.get(AxleMatcher.BENDING_STRESS), actualMapTemplate.get(AxleMatcher.BENDING_STRESS));
        assertEquals(expectedMapTemplate.get(AxleMatcher.BENDING_MOMENT), actualMapTemplate.get(AxleMatcher.BENDING_MOMENT));
        assertEquals(expectedMapTemplate.get(AxleMatcher.BENDING_RESISTANCE), actualMapTemplate.get(AxleMatcher.BENDING_RESISTANCE));
    }

    private HashMap<String, String> buildExpectedMapTemplate(){
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        expectedMapTemplate.put("${bendingSafetyFactor}", "4,22");
        expectedMapTemplate.put("${bendingStress}", "63");
        expectedMapTemplate.put("${bendingMoment}", "50 000");
        expectedMapTemplate.put("${bendingResistance}", "800");
        return expectedMapTemplate;
    }
}