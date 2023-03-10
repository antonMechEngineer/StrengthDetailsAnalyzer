package strengthDetailsAnalyzer.utils.matcher.interfaces;

import strengthDetailsAnalyzer.entity.enums.StrengthCondition;
import strengthDetailsAnalyzer.entity.interfaces.ShearDeformable;

import java.util.HashMap;

public interface ShearReportGenerated {
    String SIGN = "${shearSign}";
    String CONCLUSION = "${shearConclusion}";
    String SHEAR_AREA = "${shearArea}";
    String SHEAR_STRESS = "${shearStress}";
    String SHEAR_SAFETY_FACTOR = "${shearSafetyFactor}";

    default void insertShearConclusion(HashMap<String, String> mapTemplate, ShearDeformable shearDeformable) {
        if (shearDeformable.getShearSafetyFactor() > shearDeformable.getMinSafetyFactor()) {
            mapTemplate.put(SIGN, StrengthCondition.SAFETY.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.SAFETY.conclusion);
        } else {
            mapTemplate.put(SIGN, StrengthCondition.FAIL.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.FAIL.conclusion);
        }
    }

    default void insertShearResults(HashMap<String, String> mapTemplate, ShearDeformable shearDeformable) {
        mapTemplate.put(SHEAR_AREA, String.format("%,.1f", shearDeformable.getShearArea()));
        mapTemplate.put(SHEAR_STRESS, String.format("%,.0f", shearDeformable.getShearStress()));
        mapTemplate.put(SHEAR_SAFETY_FACTOR, String.format("%,.2f", shearDeformable.getShearSafetyFactor()));
    }
}
