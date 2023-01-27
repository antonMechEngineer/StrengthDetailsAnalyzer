package strengthdetailscalculator.entity.interfaces;

import static strengthdetailscalculator.entity.enums.StressConditionType.VON_MISSES;

public interface ComplexDeformable extends BendingDeformable, ShearDeformable {
    Double getVonMissesSafetyFactor();

    Double getVonMissesStress();

    Double calculateVonMissesStress();

    default Double calculateVonMissesSafetyFactor() {
        return VON_MISSES.stressRatio * getYieldStress() / calculateVonMissesStress();
    }



}
