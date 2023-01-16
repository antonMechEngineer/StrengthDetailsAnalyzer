package strengthdetailscalculator.utils.matcher;

import strengthdetailscalculator.entity.Axle;

import java.util.HashMap;

import static strengthdetailscalculator.entity.enums.MomentsType.CONSOLE;
import static strengthdetailscalculator.entity.enums.MomentsType.DOUBLE_SUPPORTED_ROTATION;

public class AxleMatcher extends PinMatcher {

    protected static final String SUPPORT_LENGTH = "${supportLength}";
    protected static final String BENDING_RESISTANCE = "${bendingResistance}";
    protected static final String BENDING_MOMENT = "${bendingMoment}";
    protected static final String EQUATION_BENDING_MOMENT = "${bendingStress}";
    protected static final String CALCULATION_BENDING_MOMENT = "${bendingStress}";
    protected static final String BENDING_STRESS = "${bendingStress}";
    protected static final String BENDING_SAFETY_FACTOR = "${bendingSafetyFactor}";
    protected static final String VON_MISSES_STRESS = "${vonMissesStress}";
    protected static final String VON_MISSES_SAFETY_FACTOR = "${vonMissesSafetyFactor}";

    public HashMap<String, String> getMapTemplateAxle(Axle axle) {
        HashMap<String, String> mapTemplate = getMapTemplatePin(axle);
        mapTemplate.put(BENDING_SAFETY_FACTOR, String.format( "%,.0f", axle.getBendingSafetyFactor()));
        mapTemplate.put(BENDING_STRESS, String.format( "%,.0f", axle.getBendingStress()));
        mapTemplate.put(SUPPORT_LENGTH, String.format( "%,.0f", axle.getSupportLength()));
        mapTemplate.put(BENDING_MOMENT, String.format( "%,.0f", axle.getBendingMoment()));
        mapTemplate.put(BENDING_RESISTANCE, String.format( "%,.0f", axle.getBendingResistance()));
        mapTemplate.put(VON_MISSES_STRESS, String.format( "%,.0f", axle.getVonMissesStress()));
        mapTemplate.put(VON_MISSES_SAFETY_FACTOR, String.format( "%,.0f", axle.getVonMissesSafetyFactor()));
        addMomentDescription(mapTemplate, axle);
        return mapTemplate;
    }

    private void addMomentDescription(HashMap<String, String> mapTemplate, Axle axle){
        if (axle.getNumberShearSection() == 1) {
            mapTemplate.put(EQUATION_BENDING_MOMENT, CONSOLE.equation);
            mapTemplate.put(CALCULATION_BENDING_MOMENT, axle.getForce() + " " +  axle.getSupportLength());
        } else if (axle.getNumberShearSection() == 2) {
            mapTemplate.put(EQUATION_BENDING_MOMENT, DOUBLE_SUPPORTED_ROTATION.equation);
            mapTemplate.put(CALCULATION_BENDING_MOMENT, axle.getForce() + " " +  axle.getSupportLength() + " / 4");
        }

    }
}
