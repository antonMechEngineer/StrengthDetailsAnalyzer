package strengthdetailscalculator.entity.interfaces;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static strengthdetailscalculator.entity.enums.StressConditionType.SHEAR;

class ShearDeformableTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    protected final Pin pin = new Pin(detail, 20d, 0d, 1d);

    @Test
    public void calculateShearStress() {
        Double expectedShearStress = pin.getForce() / pin.getShearArea();
        assertEquals(expectedShearStress, pin.calculateShearStress());
    }

    @Test
    void calculateShearSafetyFactor() {
        Double expectedShearSafetyFactor = SHEAR.stressRatio * pin.getYieldStress() / pin.getShearStress();
        assertEquals(expectedShearSafetyFactor, pin.calculateShearSafetyFactor());
    }
}