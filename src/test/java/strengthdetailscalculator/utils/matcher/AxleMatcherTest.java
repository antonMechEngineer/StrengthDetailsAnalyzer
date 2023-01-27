package strengthdetailscalculator.utils.matcher;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Axle;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AxleMatcherTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 50000d);
    private final Pin pin = new Pin(detail, 20d, 0d, 2d);
    private final Axle axle = new Axle(pin, 50d);

    private AxleMatcher axleMatcher = new AxleMatcher();

    @Test
    void getMapTemplateAxle() {
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        insertExpectedAxleTemplate(expectedMapTemplate);
        HashMap <String, String> actualMapTemplate = axleMatcher.getMapTemplateAxle(axle);
        assertEquals(expectedMapTemplate.get(AxleMatcher.SUPPORT_LENGTH), actualMapTemplate.get(AxleMatcher.SUPPORT_LENGTH));
    }

    private void insertExpectedAxleTemplate( HashMap<String, String> expectedMapTemplate){
        expectedMapTemplate.put("${supportLength}", "50");

    }
}