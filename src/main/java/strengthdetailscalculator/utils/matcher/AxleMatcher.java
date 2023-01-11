package strengthdetailscalculator.utils.matcher;
import strengthdetailscalculator.entity.Axle;
import java.util.HashMap;

public class AxleMatcher extends PinMatcher {

    protected static final String SUPPORT_LENGTH = "${supportLength}";
    protected static final String BENDING_RESISTANCE = "${bendingResistance}";
    protected static final String BENDING_MOMENT = "${bendingMoment}";
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
        return mapTemplate;
    }
}
