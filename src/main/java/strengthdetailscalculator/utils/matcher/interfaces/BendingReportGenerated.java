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

    default void insertBendingConclusion(HashMap<String, String> mapTemplate, BendingDeformable bendingDeformable){
        if (bendingDeformable.getBendingSafetyFactor() > bendingDeformable.getMinSafetyFactor()){
            mapTemplate.put(SIGN, StrengthCondition.SAFETY.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.SAFETY.conclusion);
        }
        else {
            mapTemplate.put(SIGN, StrengthCondition.FAIL.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.FAIL.conclusion);
        }
    }

    default void insertBendingResult(HashMap<String, String> mapTemplate, BendingDeformable bendingDeformable){
        mapTemplate.put(BENDING_SAFETY_FACTOR, String.format("%,.2f", bendingDeformable.getBendingSafetyFactor()));
        mapTemplate.put(BENDING_STRESS, String.format("%,.0f", bendingDeformable.getBendingStress()));
        mapTemplate.put(BENDING_MOMENT, String.format("%,.0f", bendingDeformable.getBendingMoment()));
        mapTemplate.put(BENDING_RESISTANCE, String.format("%,.0f", bendingDeformable.getBendingResistance()));
    }

}
