package strengthdetailscalculator.entity.interfaces;

import strengthdetailscalculator.entity.enums.StressConditionType;

public interface BendingDeformable extends Safety {
    Double calculateBendingResistance();

    Double calculateBendingMoment();

    default Double calculateBendingStress() {
        return calculateBendingMoment() / calculateBendingResistance();
    }

    default Double calculateBendingSafetyFactor() {
        return StressConditionType.BENDING.stressRatio * getYieldStress() / calculateBendingStress();
    }

    Double getBendingSafetyFactor();

}
