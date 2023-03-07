package strengthDetailsAnalyzer.utils.matcher.interfaces;

import strengthDetailsAnalyzer.entity.enums.StrengthCondition;
import strengthDetailsAnalyzer.entity.interfaces.AxialDeformable;

import java.util.HashMap;

public interface AxialReportGenerated {
    String SIGN = "${axialSign}";
    String CONCLUSION = "${axialConclusion}";
    String AXIAL_AREA = "${axialArea}";
    String AXIAL_STRESS = "${axialStress}";
    String AXIAL_SAFETY_FACTOR = "${axialSafetyFactor}";

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

    default void insertAxialResult(HashMap<String, String> mapTemplate, AxialDeformable axialDeformable){
        mapTemplate.put(AXIAL_AREA, String.format( "%,.2f", axialDeformable.calculateAxialArea()));
        mapTemplate.put(AXIAL_STRESS, String.format( "%,.0f", axialDeformable.getAxialStress()));
        mapTemplate.put(AXIAL_SAFETY_FACTOR,  String.format( "%,.2f", axialDeformable.getAxialSafetyFactor()));
    }
}
