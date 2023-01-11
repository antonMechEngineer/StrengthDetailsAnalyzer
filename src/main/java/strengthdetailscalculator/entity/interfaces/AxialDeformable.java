package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

public interface AxialDeformable extends Safety {
    Double calculateAxialArea();

    default Double calculateAxialStress(Double force) {
        return force / calculateAxialArea();
    }

    default Double calculateAxialSafetyFactor(Double force, Double yieldStress) {
        return StressConditionType.AXIAL.stressRatio * yieldStress / calculateAxialStress(force);
    }

    Double getAxialSafetyFactor();

    Double getMinSafetyFactor();

}
