package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

public interface ShearDeformable extends Safety {

    Double calculateShearArea();

    Double getShearSafetyFactor();

    default Double calculateShearStress(Double force) {
        return force / calculateShearArea();
    }

    default Double calculateShearSafetyFactor(Double force, Double yieldStress) {
        return StressConditionType.SHEAR.stressRatio * yieldStress / calculateShearStress(force);
    }
}
