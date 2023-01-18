package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

public interface AxialDeformable extends Safety {
    Double calculateAxialArea();

    default Double calculateAxialStress() {
        return getForce() / calculateAxialArea();
    }

    default Double calculateAxialSafetyFactor() {
        return StressConditionType.AXIAL.stressRatio * getYieldStress() / calculateAxialStress();
    }

    Double getAxialSafetyFactor();

    Double getMinSafetyFactor();

}
