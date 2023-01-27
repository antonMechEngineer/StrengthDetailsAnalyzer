package strengthdetailscalculator.utils.matcher;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static strengthdetailscalculator.utils.matcher.ScrewMatcher.*;

class ScrewMatcherTest {

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final String mainD = "10";
    private final String threadPitch = "1";
    private final String height = "7";
    private final ScrewType screwType = ScrewType.METRICAL;
    private final String midD = "9.35";
    private final String internalD = "8.917";
    private final String minD = "8.773";
    private final List<String> data = List.of(mainD, threadPitch, height, screwType.name);
    private final Screw screw = new Screw(detail, Double.valueOf(mainD), Double.valueOf(threadPitch),
            Double.valueOf(height), screwType, Double.valueOf(internalD), Double.valueOf(minD));
    private final ScrewMatcher screwMatcher = new ScrewMatcher();

    @Test
    void getMapTemplateScrew() {
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        insertExpectedPinTemplate(expectedMapTemplate);
        HashMap<String, String> actualMapTemplate = screwMatcher.getMapTemplateScrew(screw);
        assertEquals(expectedMapTemplate.get(MAIN_D), actualMapTemplate.get(MAIN_D));
        assertEquals(expectedMapTemplate.get(MIN_D), actualMapTemplate.get(MIN_D));
        assertEquals(expectedMapTemplate.get(INTERNAL_D), actualMapTemplate.get(INTERNAL_D));
        assertEquals(expectedMapTemplate.get(THREAD_PITCH), actualMapTemplate.get(THREAD_PITCH));
        assertEquals(expectedMapTemplate.get(HEIGHT), actualMapTemplate.get(HEIGHT));
        assertEquals(expectedMapTemplate.get(Kh), actualMapTemplate.get(Kh));
        assertEquals(expectedMapTemplate.get(Kp), actualMapTemplate.get(Kp));
    }

    private void insertExpectedPinTemplate(HashMap<String, String> expectedMapTemplate) {
        expectedMapTemplate.put("${d}", "10");
        expectedMapTemplate.put("${gost}", "ГОСТ 24705-2004");
        expectedMapTemplate.put("${d2}", "8,917");
        expectedMapTemplate.put("${d3}", "8,773");
        expectedMapTemplate.put("${p}", "1,00");
        expectedMapTemplate.put("${height}", "7,0");
        expectedMapTemplate.put("${kh}", "0,50");
        expectedMapTemplate.put("${kp}", "0,75");
    }
}