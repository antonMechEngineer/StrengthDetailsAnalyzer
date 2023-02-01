package strengthdetailscalculator.utils.matcher;

import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.utils.matcher.interfaces.ShearReportGenerated;

import java.util.HashMap;

public class PinMatcher extends DetailMatcher implements ShearReportGenerated {

    public static final String OUTER_DIAMETER = "${outerD}";
    public static final String INTERNAL_DIAMETER = "${internalD}";
    public static final String NUMBER_SHEAR_SECTION = "${nShearSect}";

    public HashMap<String, String> getMapTemplatePin(Pin pin) {
        HashMap<String, String> mapTemplate = getMapTemplateDetail(pin);
        insertPinProperties(mapTemplate, pin);
        insertShearResults(mapTemplate, pin);
        insertShearConclusion(mapTemplate, pin);
        return mapTemplate;
    }

    private void insertPinProperties(HashMap<String, String> mapTemplate, Pin pin){
        mapTemplate.put(OUTER_DIAMETER, String.format( "%,.1f", pin.getOuterDiameter()));
        mapTemplate.put(INTERNAL_DIAMETER, String.format( "%,.1f", pin.getInternalDiameter()));
        mapTemplate.put(NUMBER_SHEAR_SECTION, String.format( "%,.0f", pin.getNumberShearSection()));
    }
}
