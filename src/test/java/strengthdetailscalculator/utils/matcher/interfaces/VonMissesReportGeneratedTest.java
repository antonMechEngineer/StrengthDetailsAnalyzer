package strengthdetailscalculator.utils.matcher.interfaces;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Axle;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.utils.matcher.AxleMatcher;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static strengthdetailscalculator.utils.matcher.interfaces.VonMissesReportGenerated.VON_MISSES_SAFETY_FACTOR;
import static strengthdetailscalculator.utils.matcher.interfaces.VonMissesReportGenerated.VON_MISSES_STRESS;

class VonMissesReportGeneratedTest {

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 10000d);
    private final Pin pin = new Pin(detail, 20d, 0d, 2d);
    private final Axle axle = new Axle(pin, 50d);
    private final AxleMatcher axleMatcher = new AxleMatcher();

    @Test
    void insertVonMissesConclusion() {
        HashMap<String, String> expectedReportGenerated = new HashMap<>();
        String sign = "${vonMissesSign}";
        String conclusion = "${vonMissesConclusion}";
        expectedReportGenerated.put(sign, ">");
        expectedReportGenerated.put(conclusion, "Условие прочности выполняется.");
        HashMap<String, String> actualReportGenerated = new HashMap<>();
        axleMatcher.insertVonMissesConclusion(actualReportGenerated, axle);
        assertEquals(expectedReportGenerated, actualReportGenerated);
    }

    @Test
    void insertVonMissesResult(){
        HashMap<String, String> expectedMapTemplate = buildExpectedMapTemplate();
        HashMap<String, String> actualMapTemplate = axleMatcher.getMapTemplateAxle(axle);
        assertEquals(expectedMapTemplate.get(VON_MISSES_STRESS), actualMapTemplate.get(VON_MISSES_STRESS));
        assertEquals(expectedMapTemplate.get(VON_MISSES_SAFETY_FACTOR), actualMapTemplate.get(VON_MISSES_SAFETY_FACTOR));
    }

    private HashMap<String, String> buildExpectedMapTemplate(){
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        expectedMapTemplate.put("${vonMissesStress}", "159");
        expectedMapTemplate.put("${vonMissesSafetyFactor}", "1,51");
        return expectedMapTemplate;
    }
}