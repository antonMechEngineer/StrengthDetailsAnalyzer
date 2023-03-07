package strengthDetailsAnalyzer.utils.matcher;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.Axle;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Pin;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AxleMatcherTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 50000d, 1.3);
    private final Pin pin = new Pin(detail, 20d, 0d, 2d);
    private final Axle axle = new Axle(pin, 50d);

    private AxleMatcher axleMatcher = new AxleMatcher();

    @Test
    public void getMapTemplateAxle() {
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        insertExpectedAxleTemplate(expectedMapTemplate);
        HashMap <String, String> actualMapTemplate = axleMatcher.getMapTemplateAxle(axle);
        assertEquals(expectedMapTemplate.get(AxleMatcher.SUPPORT_LENGTH), actualMapTemplate.get(AxleMatcher.SUPPORT_LENGTH));
    }

    private void insertExpectedAxleTemplate( HashMap<String, String> expectedMapTemplate){
        expectedMapTemplate.put("${supportLength}", "50");

    }
}