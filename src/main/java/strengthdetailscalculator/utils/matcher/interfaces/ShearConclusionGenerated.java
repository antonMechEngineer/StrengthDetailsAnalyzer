package strengthdetailscalculator.utils.matcher.interfaces;

import strengthdetailscalculator.entity.enums.StrengthCondition;
import strengthdetailscalculator.entity.interfaces.ShearDeformable;
import java.util.HashMap;

public interface ShearConclusionGenerated {
    String SIGN = "${shearSign}";
    String CONCLUSION = "${shearConclusion}";

    default void insertShearConclusion(HashMap<String, String> mapTemplate, ShearDeformable shearDeformable){
        if (shearDeformable.getShearSafetyFactor() > shearDeformable.getMinSafetyFactor()){
            mapTemplate.put(SIGN, StrengthCondition.SAFETY.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.SAFETY.conclusion);
        }
        else {
            mapTemplate.put(SIGN, StrengthCondition.FAIL.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.FAIL.conclusion);
        }
    }
}
