package strengthDetailsAnalyzer.entity.interfaces;

import static strengthDetailsAnalyzer.entity.enums.StressConditionType.BENDING;

public interface BendingDeformable extends Safety {

    Double getBendingResistance();

    Double getBendingMoment();

    Double getBendingStress();

    Double getBendingSafetyFactor();

    Double calculateBendingResistance();

    Double calculateBendingMoment();

    default Double calculateBendingStress() {
        return calculateBendingMoment() / calculateBendingResistance();
    }

    default Double calculateBendingSafetyFactor() {
        return BENDING.stressRatio * getYieldStress() / calculateBendingStress();
    }

}
