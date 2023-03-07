package strengthDetailsAnalyzer.utils.matcher;

import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Detail;

import java.util.HashMap;

@Component
public class DetailMatcher {
    public static final String NAME = "${name}";
    public static final String CODE = "${code}";
    public static final String YIELD_STRESS = "${yieldStress}";
    public static final String MATERIAL = "${material}";
    public static final String MIN_SAFETY_FACTOR = "${minSafetyFactor}";

    protected HashMap<String, String> getMapTemplateDetail(Detail detail){
        HashMap<String, String> detailTemplate = new HashMap<>();
        detailTemplate.put(NAME, detail.getName());
        detailTemplate.put(CODE, detail.getCode());
        detailTemplate.put(YIELD_STRESS, String.format( "%,.0f",  detail.getYieldStress()));
        detailTemplate.put(MATERIAL, detail.getMaterial());
        detailTemplate.put(MIN_SAFETY_FACTOR, String.format( "%,.2f", detail.getMinSafetyFactor()));
        return detailTemplate;
    }
}
