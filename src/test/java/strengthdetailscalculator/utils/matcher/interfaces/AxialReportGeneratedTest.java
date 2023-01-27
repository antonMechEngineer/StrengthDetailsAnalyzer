package strengthdetailscalculator.utils.matcher.interfaces;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;
import strengthdetailscalculator.utils.matcher.ScrewMatcher;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static strengthdetailscalculator.utils.matcher.interfaces.AxialReportGenerated.*;

class AxialReportGeneratedTest {

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final Screw screw = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);
    private final ScrewMatcher screwMatcher = new ScrewMatcher();

    @Test
    void insertAxialConclusion() {
        HashMap<String, String> expectedReportGenerated = new HashMap<>();
        String sign = "${axialSign}";
        String conclusion = "${axialConclusion}";
        expectedReportGenerated.put(sign, ">");
        expectedReportGenerated.put(conclusion, "Условие прочности выполняется.");
        HashMap<String, String> actualReportGenerated = new HashMap<>();
        screwMatcher.insertAxialConclusion(actualReportGenerated, screw);
        assertEquals(expectedReportGenerated, actualReportGenerated);
    }

    @Test
    void insertAxialResult(){
        HashMap<String, String> expectedMapTemplate = buildExpectedMapTemplate();
        HashMap<String, String> actualMapTemplate = screwMatcher.getMapTemplateScrew(screw);
        assertEquals(expectedMapTemplate.get(AXIAL_AREA), actualMapTemplate.get(AXIAL_AREA));
        assertEquals(expectedMapTemplate.get(AXIAL_STRESS), actualMapTemplate.get(AXIAL_STRESS));
        assertEquals(expectedMapTemplate.get(AXIAL_SAFETY_FACTOR), actualMapTemplate.get(AXIAL_SAFETY_FACTOR));
    }

    private HashMap<String, String> buildExpectedMapTemplate(){
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        expectedMapTemplate.put("${axialArea}", "60,45");
        expectedMapTemplate.put("${axialStress}", "17");
        expectedMapTemplate.put("${axialSafetyFactor}", "14,51");
        return expectedMapTemplate;
    }

}