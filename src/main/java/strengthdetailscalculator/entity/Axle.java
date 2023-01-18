package strengthdetailscalculator.entity;

import lombok.Getter;
import lombok.Setter;
import strengthdetailscalculator.entity.interfaces.BendingDeformable;
import strengthdetailscalculator.entity.interfaces.ComplexDeformable;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

@Getter
@Setter
public class Axle extends Pin implements BendingDeformable, ComplexDeformable {

    private final Double supportLength;
    private final Double bendingResistance;
    private final Double bendingMoment;
    private final Double bendingStress;
    private final Double bendingSafetyFactor;
    private final Double vonMissesStress;
    private final Double vonMissesSafetyFactor;

    public Axle(String name, String code, String material, Double yieldStress, Double force,
                Double outerDiameter, Double internalDiameter, Double numberShearSection, Double supportLength) {
        super(name, code, material, yieldStress, force, outerDiameter, internalDiameter, numberShearSection);
        this.supportLength = supportLength;
        this.bendingResistance = calculateBendingResistance();
        this.bendingStress = calculateBendingStress();
        this.bendingSafetyFactor = calculateBendingSafetyFactor();
        this.bendingMoment = calculateBendingMoment();
        this.vonMissesStress = calculateVonMissesStress();
        this.vonMissesSafetyFactor = calculateVonMissesSafetyFactor();
    }

    public Axle(Pin pin, Double supportLength) {
        super(pin.getName(), pin.getCode(), pin.getMaterial(), pin.getYieldStress(), pin.getForce(),
                pin.getOuterDiameter(), pin.getInternalDiameter(), pin.getNumberShearSection());
        this.supportLength = supportLength;
        this.bendingResistance = calculateBendingResistance();
        this.bendingStress = calculateBendingStress();
        this.bendingSafetyFactor = calculateBendingSafetyFactor();
        this.bendingMoment = calculateBendingMoment();
        this.vonMissesStress = calculateVonMissesStress();
        this.vonMissesSafetyFactor = calculateVonMissesSafetyFactor();
    }

    @Override
    public Double calculateVonMissesStress() {
        return pow((pow(calculateBendingStress(), 2) + 3 * pow(calculateShearStress(), 2)), 0.5);
    }

    @Override
    public Double calculateShearArea() {
        return  numberShearSection * PI * pow(outerDiameter, 2.0) / 4;
    }

    @Override
    public Double calculateBendingResistance() {
        return 0.1 * (pow(outerDiameter, 3) - pow(internalDiameter, 3));
    }

    @Override
    public Double calculateBendingMoment() {
        Double bendingMoment = (double) 0;
        if (numberShearSection == 1d) {
            bendingMoment = force * supportLength;
        } else if (numberShearSection == 2d) {
            bendingMoment = force * supportLength / 4;
        }
        else if (numberShearSection == 3d) {
            bendingMoment = 3 * force * supportLength / 64;
        }
        else if (numberShearSection == 4d) {
            bendingMoment = 3 * force * supportLength / 128;
        }
        return bendingMoment;
    }

}
