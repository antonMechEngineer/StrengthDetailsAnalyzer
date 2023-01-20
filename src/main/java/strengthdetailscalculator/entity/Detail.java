package strengthdetailscalculator.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Detail {
    private final String name;
    private final String code;
    private final String material;
    private final Double yieldStress;
    protected final Double force;
    private final Double minSafetyFactor = 1.0;

    public Detail(String name, String code, String material, Double yieldStress, Double force) {
        this.name = name;
        this.code = code;
        this.material = material;
        this.yieldStress = yieldStress;
        this.force = force;
    }
    public Detail(List<String> textData, List<String> numericalData){
        this.name = textData.get(0);
        this.code = textData.get(1);
        this.material = textData.get(2);
        this.yieldStress =  Double.valueOf(numericalData.get(0));
        this.force = Double.valueOf(numericalData.get(1));
    }
}
