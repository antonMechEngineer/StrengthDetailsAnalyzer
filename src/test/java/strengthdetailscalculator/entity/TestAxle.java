package strengthdetailscalculator.entity;

import java.util.List;

import static java.lang.Math.pow;
import static strengthdetailscalculator.entity.enums.StressConditionType.BENDING;
import static strengthdetailscalculator.entity.enums.StressConditionType.VON_MISSES;

public class TestAxle extends TestPin {
   private final Axle axle1 = new Axle(pin1, 100d);
   private final Axle axle2 = new Axle(pin2, 100d);
   private final Axle axle3 = new Axle(pin3, 100d);
   private final Axle axle4 = new Axle(pin4, 100d);
   private final List<Axle> axles = List.of(axle1, axle2, axle3, axle4);

    public void testBendingResistance(){
        Double expectedBendingResistance = 0.1 * (pow(axle1.getOuterDiameter(), 3) - (pow (axle1.getInternalDiameter(), 3)));
        assertEquals(expectedBendingResistance, axle1.calculateBendingResistance());
    }

    public void testBendingMoment(){
        Double expectedBendingMoment1 = axle1.getForce() * axle1.getSupportLength();
        assertEquals(expectedBendingMoment1, axle1.calculateBendingMoment());
        Double expectedBendingMoment2 = axle2.getForce() * axle2.getSupportLength() / 4;
        assertEquals(expectedBendingMoment2, axle2.calculateBendingMoment());
        Double expectedBendingMoment3 = axle3.getForce() * axle3.getSupportLength() * 3 / 64;
        assertEquals(expectedBendingMoment3, axle3.calculateBendingMoment());
        Double expectedBendingMoment4 = axle4.getForce() * axle4.getSupportLength() * 3 / 128;
        assertEquals(expectedBendingMoment4, axle4.calculateBendingMoment());
    }

    public void testCalculateBendingStress(){
        Double expectedBendingStress = axle1.calculateBendingMoment() / axle1.calculateBendingResistance();
        assertEquals(expectedBendingStress, axle1.calculateBendingStress());
    }

    public void testBendingSafetyFactor(){
        Double expectedBendingSafetyFactor1 = BENDING.stressRatio * axle1.getYieldStress()/ axle1.calculateBendingStress();
        assertEquals(expectedBendingSafetyFactor1, axle1.calculateBendingSafetyFactor());
    }

    public void testCalculateVonMisesStress(){
        Double expectedVonMissesStress1 = pow (( pow(axle1.calculateBendingStress(), 2) + 3 * pow(axle1.calculateShearStress(), 2)), 0.5);
        assertEquals(expectedVonMissesStress1, axle1.calculateVonMissesStress());
    }

    public void testCalculateVonMissesSafetyFactor(){
        Double expectedVonMissesStressSafetyFactor1 = VON_MISSES.stressRatio * axle1.getYieldStress() / axle1.calculateVonMissesStress();
        assertEquals(expectedVonMissesStressSafetyFactor1, axle1.calculateVonMissesSafetyFactor());

    }
}
