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
        this.bendingSafetyFactor = calculateBendingSafetyFactor(force, yieldStress);
        this.bendingMoment = calculateBendingMoment();
        this.vonMissesStress = calculateVonMissesStress(force);
        this.vonMissesSafetyFactor = calculateVonMissesSafetyFactor(force, yieldStress);
    }

    public Axle(Pin pin, Double supportLength) {
        super(pin.getName(), pin.getCode(), pin.getMaterial(), pin.getYieldStress(), pin.getForce(),
                pin.getOuterDiameter(), pin.getInternalDiameter(), pin.getNumberShearSection());
        this.supportLength = supportLength;
        this.bendingResistance = calculateBendingResistance();
        this.bendingStress = calculateBendingStress();
        this.bendingSafetyFactor = calculateBendingSafetyFactor(pin.getForce(), pin.getYieldStress());
        this.bendingMoment = calculateBendingMoment();
        this.vonMissesStress = calculateVonMissesStress(pin.getForce());
        this.vonMissesSafetyFactor = calculateVonMissesSafetyFactor(pin.getForce(), pin.getYieldStress());
    }

    @Override
    public Double calculateVonMissesStress(Double force) {
        return pow((pow(calculateBendingStress(), 2) + 3 * pow(calculateShearStress(force), 2)), 0.5);
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
        return bendingMoment;
    }

}
