package strengthdetailscalculator.utils.matcher.interfaces;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;
import strengthdetailscalculator.utils.matcher.PinMatcher;
import strengthdetailscalculator.utils.matcher.ScrewMatcher;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShearReportGeneratedTest {

    protected final Detail detail = new Detail("name", "code", "сталь", 240d, 50000d);
    private final Screw screw = new Screw(detail, 50d, 3d, 30d, ScrewType.METRICAL, 46.752, 46.319);
    private final Pin pin = new Pin(detail, 20d, 0d, 2d);
    private final ScrewMatcher screwMatcher = new ScrewMatcher();
    private final PinMatcher pinMatcher = new PinMatcher();

    @Test
    void insertShearConclusion() {
        HashMap<String, String> expectedReportGenerated = new HashMap<>();
        String sign = "${shearSign}";
        String conclusion = "${shearConclusion}";
        expectedReportGenerated.put(sign, ">");
        expectedReportGenerated.put(conclusion, "Условие прочности выполняется.");
        HashMap<String, String> actualReportGenerated = new HashMap<>();
        screwMatcher.insertShearConclusion(actualReportGenerated, screw);
        assertEquals(expectedReportGenerated, actualReportGenerated);
    }

    @Test
    void insertShearResult(){
        HashMap<String, String> expectedMapTemplate = buildExpectedMapTemplate();
        HashMap<String, String> actualMapTemplate = pinMatcher.getMapTemplatePin(pin);
        assertEquals(expectedMapTemplate.get(PinMatcher.SHEAR_AREA), actualMapTemplate.get(PinMatcher.SHEAR_AREA));
        assertEquals(expectedMapTemplate.get(PinMatcher.SHEAR_STRESS), actualMapTemplate.get(PinMatcher.SHEAR_STRESS));
        assertEquals(expectedMapTemplate.get(PinMatcher.SHEAR_SAFETY_FACTOR), actualMapTemplate.get(PinMatcher.SHEAR_SAFETY_FACTOR));
    }

    private HashMap<String, String> buildExpectedMapTemplate(){
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        expectedMapTemplate.put("${shearArea}", "628,3");
        expectedMapTemplate.put("${shearStress}", "80");
        expectedMapTemplate.put("${shearSafetyFactor}", "1,81");
        return expectedMapTemplate;
    }

}