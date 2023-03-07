package strengthDetailsAnalyzer.entity.interfaces;

import static strengthDetailsAnalyzer.entity.enums.StressConditionType.VON_MISSES;

public interface ComplexDeformable extends BendingDeformable, ShearDeformable {
    Double getVonMissesSafetyFactor();

    Double getVonMissesStress();

    Double calculateVonMissesStress();

    default Double calculateVonMissesSafetyFactor() {
        return VON_MISSES.stressRatio * getYieldStress() / calculateVonMissesStress();
    }



}
