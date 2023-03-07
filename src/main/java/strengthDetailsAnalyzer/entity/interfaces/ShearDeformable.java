package strengthDetailsAnalyzer.entity.interfaces;

import strengthDetailsAnalyzer.entity.enums.StressConditionType;

public interface ShearDeformable extends Safety {

    Double getShearSafetyFactor();

    Double getShearArea();

    Double getShearStress();

    Double calculateShearArea();

    default Double calculateShearStress() {
        return getForce() / calculateShearArea();
    }

    default Double calculateShearSafetyFactor() {
        return StressConditionType.SHEAR.stressRatio * getYieldStress() / calculateShearStress();
    }
}
