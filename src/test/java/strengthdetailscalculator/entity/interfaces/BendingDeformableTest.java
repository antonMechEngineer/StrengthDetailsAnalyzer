package strengthdetailscalculator.entity.interfaces;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Axle;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static strengthdetailscalculator.entity.enums.StressConditionType.BENDING;

class BendingDeformableTest  {

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final Pin pin = new Pin(detail, 20d, 0d, 1d);
    private final Axle axle = new Axle(pin, 100d);

    @Test
    public void calculateBendingStress(){
        Double expectedBendingStress = axle.calculateBendingMoment() / axle.calculateBendingResistance();
        assertEquals(expectedBendingStress, axle.calculateBendingStress());
    }

    @Test
    void calculateBendingSafetyFactor() {
        Double expectedBendingSafetyFactor = BENDING.stressRatio * axle.getYieldStress()/ axle.calculateBendingStress();
        assertEquals(expectedBendingSafetyFactor, axle.calculateBendingSafetyFactor());
    }
}