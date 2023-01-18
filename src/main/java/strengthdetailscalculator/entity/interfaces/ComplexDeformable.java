package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

public interface ComplexDeformable extends BendingDeformable, ShearDeformable {
     Double calculateVonMissesStress();

    default Double calculateVonMissesSafetyFactor() {
        return StressConditionType.VON_MISSES.stressRatio * getYieldStress() / calculateVonMissesStress();
    }

    Double getVonMissesSafetyFactor();

}
