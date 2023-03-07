package strengthDetailsAnalyzer.utils.matcher;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.Detail;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class DetailMatcherTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    protected DetailMatcher detailMatcher = new DetailMatcher();

    @Test
    public void getMapTemplateDetail() {
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        insertExpectedDetailTemplate(expectedMapTemplate, detail);
        HashMap<String, String> actualMapTemplate = detailMatcher.getMapTemplateDetail(detail);
        assertEquals(expectedMapTemplate, actualMapTemplate);
    }

    protected void insertExpectedDetailTemplate(HashMap<String, String> expectedMapTemplate, Detail detail){
        expectedMapTemplate.put("${name}", detail.getName());
        expectedMapTemplate.put("${code}", detail.getCode());
        expectedMapTemplate.put("${yieldStress}", String.format( "%,.0f",  detail.getYieldStress()));
        expectedMapTemplate.put("${material}", detail.getMaterial());
        expectedMapTemplate.put("${force}", String.format( "%,.0f", detail.getForce()));
        expectedMapTemplate.put("${minSafetyFactor}", String.format( "%,.2f", detail.getMinSafetyFactor()));
    }
}