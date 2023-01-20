package strengthdetailscalculator.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import strengthdetailscalculator.entity.interfaces.ShearDeformable;
import static java.lang.Math.PI;
import static java.lang.Math.pow;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Pin extends Detail implements ShearDeformable {

    protected final Double outerDiameter;
    protected final Double internalDiameter;
    protected final Double numberShearSection;
    protected final Double shearArea;

    protected final Double shearStress;
    protected final Double shearSafetyFactor;

    public Pin(String name, String code, String material, Double yieldStress, Double force,
                Double outerDiameter, Double internalDiameter, Double numberShearSection) {
        super(name, code, material, yieldStress, force);
        this.outerDiameter = outerDiameter;
        this.internalDiameter = internalDiameter;
        this.numberShearSection = numberShearSection;
        this.shearArea = calculateShearArea();
        this.shearStress = calculateShearStress();
        this.shearSafetyFactor = calculateShearSafetyFactor();
    }

    public Pin(Detail detail, Double outerDiameter, Double internalDiameter, Double numberShearSection) {
        super(detail.getName(), detail.getCode(), detail.getMaterial(), detail.getYieldStress(), detail.getForce());
        this.outerDiameter = outerDiameter;
        this.internalDiameter = internalDiameter;
        this.numberShearSection = numberShearSection;
        this.shearArea = calculateShearArea();
        this.shearStress = calculateShearStress();
        this.shearSafetyFactor = calculateShearSafetyFactor();
    }

    public Pin(Pin pin, Double numberShearSection){
        super(pin.getName(), pin.getCode(), pin.getMaterial(), pin.getYieldStress(), pin.getForce());
        this.internalDiameter = pin.getInternalDiameter();
        this.outerDiameter = pin.getOuterDiameter();
        this.numberShearSection = numberShearSection;
        this.shearArea = calculateShearArea();
        this.shearStress = calculateShearStress();
        this.shearSafetyFactor = calculateShearSafetyFactor();
    }

    @Override
    public Double calculateShearArea() {
        return  numberShearSection * PI * ( pow(outerDiameter, 2.0) - pow(internalDiameter, 2.0) ) / 4;
    }
}
