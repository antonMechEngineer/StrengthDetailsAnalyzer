package strengthdetailscalculator.utils.matcher;

import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.utils.matcher.interfaces.ShearConclusionGenerated;

import java.util.HashMap;

public class PinMatcher extends DetailMatcher implements ShearConclusionGenerated {

    protected static final String OUTER_DIAMETER = "${outerD}";
    protected static final String INTERNAL_DIAMETER = "${internalD}";
    protected static final String NUMBER_SHEAR_SECTION = "${nShearSect}";
    protected static final String SHEAR_AREA = "${shearArea}";
    protected static final String SHEAR_STRESS = "${shearStress}";
    protected static final String SHEAR_SAFETY_FACTOR = "${shearSafetyFactor}";

    public HashMap<String, String> getMapTemplatePin(Pin pin) {
        HashMap<String, String> mapTemplate = getMapTemplateDetail(pin);
        mapTemplate.put(OUTER_DIAMETER, String.format( "%,.1f", pin.getOuterDiameter()));
        mapTemplate.put(INTERNAL_DIAMETER, String.format( "%,.1f", pin.getInternalDiameter()));
        mapTemplate.put(NUMBER_SHEAR_SECTION, String.format( "%,.0f", pin.getNumberShearSection()));
        mapTemplate.put(SHEAR_AREA, String.format( "%,.1f", pin.getShearArea()));
        mapTemplate.put(SHEAR_STRESS, String.format( "%,.0f", pin.getShearStress()));
        mapTemplate.put(SHEAR_SAFETY_FACTOR, String.format( "%,.0f", pin.getShearSafetyFactor()));
        insertShearConclusion(mapTemplate, pin);
        return mapTemplate;
    }
}
