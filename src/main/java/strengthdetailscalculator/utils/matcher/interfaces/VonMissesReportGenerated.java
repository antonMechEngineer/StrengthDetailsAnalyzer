package strengthdetailscalculator.utils.matcher.interfaces;

import strengthdetailscalculator.entity.enums.StrengthCondition;
import strengthdetailscalculator.entity.interfaces.ComplexDeformable;

import java.util.HashMap;

public interface VonMissesReportGenerated {

    String SIGN = "${vonMissesSign}";
    String CONCLUSION = "${vonMissesConclusion}";
    String VON_MISSES_STRESS = "${vonMissesStress}";
    String VON_MISSES_SAFETY_FACTOR = "${vonMissesSafetyFactor}";

    default void insertVonMissesConclusion(HashMap<String, String> mapTemplate, ComplexDeformable complexDeformable){
        if (complexDeformable.getVonMissesSafetyFactor() > complexDeformable.getMinSafetyFactor()){
            mapTemplate.put(SIGN, StrengthCondition.SAFETY.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.SAFETY.conclusion);
        }
        else {
            mapTemplate.put(SIGN, StrengthCondition.FAIL.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.FAIL.conclusion);
        }
    }
}
