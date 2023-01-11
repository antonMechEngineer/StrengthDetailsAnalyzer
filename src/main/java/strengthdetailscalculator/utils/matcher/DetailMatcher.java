package strengthdetailscalculator.utils.matcher;

import strengthdetailscalculator.entity.Detail;
import java.util.HashMap;

public abstract class DetailMatcher {
    protected static final String NAME = "${name}";
    protected static final String CODE = "${code}";
    protected static final String YIELD_STRESS = "${sigmaT}";
    protected static final String MATERIAL = "${material}";
    protected static final String FORCE = "${force}";
    protected static final String MIN_SAFETY_FACTOR = "${minSafetyFactor}";

    protected HashMap<String, String> getMapTemplateDetail(Detail detail){
        HashMap<String, String> detailTemplate = new HashMap<>();
        detailTemplate.put(NAME, detail.getName());
        detailTemplate.put(CODE, detail.getCode());
        detailTemplate.put(YIELD_STRESS, String.format( "%,.0f",  detail.getYieldStress()));
        detailTemplate.put(MATERIAL, detail.getMaterial());
        detailTemplate.put(FORCE, String.format( "%,.0f", detail.getForce()));
        detailTemplate.put(MIN_SAFETY_FACTOR, String.format( "%,.2f", detail.getMinSafetyFactor()));
        return detailTemplate;
    }
}
