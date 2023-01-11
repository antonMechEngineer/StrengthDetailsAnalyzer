package strengthdetailscalculator.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Detail {
    protected final String name;
    protected final String code;
    protected final String material;
    protected final Double yieldStress;
    protected final Double force;
    protected final Double minSafetyFactor = 1.0;

    public Detail(String name, String code, String material, Double yieldStress, Double force) {
        this.name = name;
        this.code = code;
        this.material = material;
        this.yieldStress = yieldStress;
        this.force = force;
    }

}
