package strengthdetailscalculator.utils.matcher.interfaces;

import strengthdetailscalculator.entity.enums.StrengthCondition;
import strengthdetailscalculator.entity.interfaces.AxialDeformable;

import java.util.HashMap;

public interface AxialConclusionGenerated {
    String SIGN = "${axialSign}";
    String CONCLUSION = "${axialConclusion}";

    default void insertAxialConclusion(HashMap<String, String> mapTemplate, AxialDeformable axialDeformable){
        if (axialDeformable.getAxialSafetyFactor() > axialDeformable.getMinSafetyFactor()){
            mapTemplate.put(SIGN, StrengthCondition.SAFETY.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.SAFETY.conclusion);
        }
        else {
            mapTemplate.put(SIGN, StrengthCondition.FAIL.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.FAIL.conclusion);
        }
    }

}
