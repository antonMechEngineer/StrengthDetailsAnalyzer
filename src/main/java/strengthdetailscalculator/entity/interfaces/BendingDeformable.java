package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

public interface BendingDeformable extends Safety {
    Double calculateBendingResistance();

    Double calculateBendingMoment();

    default Double calculateBendingStress() {
        return calculateBendingMoment() / calculateBendingResistance();
    }

    default Double calculateBendingSafetyFactor(Double force, Double yieldStress) {
        return StressConditionType.AXIAL.stressRatio * yieldStress / calculateBendingStress();
    }

    Double getBendingSafetyFactor();

}
