package strengthDetailsAnalyzer.entity.interfaces;

import static strengthDetailsAnalyzer.entity.enums.StressConditionType.AXIAL;

public interface AxialDeformable extends Safety {
    Double getAxialSafetyFactor();

    Double getMinSafetyFactor();

    Double getAxialStress();

    default Double calculateAxialStress() {
        return getForce() / calculateAxialArea();
    }

    default Double calculateAxialSafetyFactor() {
        return AXIAL.stressRatio * getYieldStress() / calculateAxialStress();
    }

    Double calculateAxialArea();

}
