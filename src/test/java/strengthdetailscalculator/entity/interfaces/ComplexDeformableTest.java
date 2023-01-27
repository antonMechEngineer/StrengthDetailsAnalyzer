package strengthdetailscalculator.entity.interfaces;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Axle;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import static org.junit.jupiter.api.Assertions.*;
import static strengthdetailscalculator.entity.enums.StressConditionType.VON_MISSES;

class ComplexDeformableTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final Pin pin = new Pin(detail, 20d, 0d, 1d);
    private final Axle axle = new Axle(pin, 100d);

    @Test
    void calculateVonMissesSafetyFactor() {
        Double expectedVonMissesStressSafetyFactor = VON_MISSES.stressRatio * axle.getYieldStress() /axle.calculateVonMissesStress();
        assertEquals(expectedVonMissesStressSafetyFactor, axle.calculateVonMissesSafetyFactor());

    }
}