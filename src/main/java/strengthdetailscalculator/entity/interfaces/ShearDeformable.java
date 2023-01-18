package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

public interface ShearDeformable extends Safety {

    Double calculateShearArea();

    Double getShearSafetyFactor();

    default Double calculateShearStress() {
        return getForce() / calculateShearArea();
    }

    default Double calculateShearSafetyFactor() {
        return StressConditionType.SHEAR.stressRatio * getYieldStress() / calculateShearStress();
    }
}
