package strengthdetailscalculator.utils.matcher.interfaces;

import strengthdetailscalculator.entity.enums.StrengthCondition;
import strengthdetailscalculator.entity.interfaces.BendingDeformable;

import java.util.HashMap;

public interface BendingReportGenerated {
    String SIGN = "${bendingSign}";
    String CONCLUSION = "${bendingConclusion}";
    String BENDING_RESISTANCE = "${bendingResistance}";
    String BENDING_MOMENT = "${bendingMoment}";
    String EQUATION_BENDING_MOMENT = "${equationBendingMoment}";
    String CALCULATION_BENDING_MOMENT = "${calculationBendingMoment}";
    String BENDING_STRESS = "${bendingStress}";
    String BENDING_SAFETY_FACTOR = "${bendingSafetyFactor}";

    default void insertBendConclusion(HashMap<String, String> mapTemplate, BendingDeformable bendingDeformable){
        if (bendingDeformable.getBendingSafetyFactor() > bendingDeformable.getMinSafetyFactor()){
            mapTemplate.put(SIGN, StrengthCondition.SAFETY.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.SAFETY.conclusion);
        }
        else {
            mapTemplate.put(SIGN, StrengthCondition.FAIL.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.FAIL.conclusion);
        }
    }

}
