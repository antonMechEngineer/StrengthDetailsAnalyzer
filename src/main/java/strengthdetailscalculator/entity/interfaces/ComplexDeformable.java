package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

import static java.lang.Math.pow;

public interface ComplexDeformable extends BendingDeformable, ShearDeformable {
     default Double calculateVonMissesStress(Double force) {
        return pow((pow(calculateBendingStress(), 2) + 3 * pow(calculateShearStress(force), 2)), 0.5);
    }

    default Double calculateVonMissesSafetyFactor(Double force, Double yieldStress) {
        return StressConditionType.VON_MISSES.stressRatio * yieldStress / calculateVonMissesStress(force);
    }

    Double getVonMissesSafetyFactor();

}
