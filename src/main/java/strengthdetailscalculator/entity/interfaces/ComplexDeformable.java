package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

public interface ComplexDeformable extends BendingDeformable, ShearDeformable {
     Double calculateVonMissesStress(Double force);

    default Double calculateVonMissesSafetyFactor(Double force, Double yieldStress) {
        return StressConditionType.VON_MISSES.stressRatio * yieldStress / calculateVonMissesStress(force);
    }

    Double getVonMissesSafetyFactor();

}
