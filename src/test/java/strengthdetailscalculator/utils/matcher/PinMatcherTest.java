package strengthdetailscalculator.utils.matcher;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PinMatcherTest {

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 50000d);
    private final Pin pin = new Pin(detail, 20d, 0d, 2d);
    private PinMatcher pinMatcher = new PinMatcher();

    @Test
    void getMapTemplatePin() {
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        insertExpectedPinTemplate(expectedMapTemplate);
        HashMap <String, String> actualMapTemplate = pinMatcher.getMapTemplatePin(pin);
        assertEquals(expectedMapTemplate.get(PinMatcher.OUTER_DIAMETER), actualMapTemplate.get(PinMatcher.OUTER_DIAMETER));
        assertEquals(expectedMapTemplate.get(PinMatcher.INTERNAL_DIAMETER), actualMapTemplate.get(PinMatcher.INTERNAL_DIAMETER));
        assertEquals(expectedMapTemplate.get(PinMatcher.NUMBER_SHEAR_SECTION), actualMapTemplate.get(PinMatcher.NUMBER_SHEAR_SECTION));
    }

    private void insertExpectedPinTemplate( HashMap<String, String> expectedMapTemplate){
        expectedMapTemplate.put("${outerD}", "20,0");
        expectedMapTemplate.put("${internalD}", "0,0");
        expectedMapTemplate.put("${nShearSect}", "2");

    }

}