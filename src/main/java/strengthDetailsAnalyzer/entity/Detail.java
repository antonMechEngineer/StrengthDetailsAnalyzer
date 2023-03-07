package strengthDetailsAnalyzer.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static strengthDetailsAnalyzer.controller.DetailController.*;

@Getter
@Setter
public class Detail {

    public static final Double DEFAULT_MIN_SAFETY_FACTOR = 1.0;
    private final String name;
    private final String code;
    private final String material;
    private final Double yieldStress;
    private final Double minSafetyFactor;

    public Detail(String name, String code, String material, Double yieldStress, Double minSafetyFactor) {
        this.name = name;
        this.code = code;
        this.material = material;
        this.yieldStress = yieldStress;
        this.minSafetyFactor = minSafetyFactor;
    }

    public Detail(List<String> textData, List<String> numericalData){
        this.name = textData.get(INDEX_NAME);
        this.code = textData.get(INDEX_CODE);
        this.material = textData.get(INDEX_MATERIAL);
        this.yieldStress =  Double.valueOf(numericalData.get(INDEX_YIELD_STRESS));
        if(Boolean.parseBoolean(textData.get(INDEX_IS_USER_SAFETY_FACTOR)) ){
            this.minSafetyFactor = Double.valueOf(numericalData.get(INDEX_USER_SAFETY_FACTOR));
        }
        else {minSafetyFactor = DEFAULT_MIN_SAFETY_FACTOR;
        }
    }
}
