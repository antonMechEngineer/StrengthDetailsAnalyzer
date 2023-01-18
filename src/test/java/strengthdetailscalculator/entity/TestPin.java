package strengthdetailscalculator.entity;

import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static strengthdetailscalculator.entity.enums.StressConditionType.SHEAR;

public class TestPin extends TestDetail {

    protected final Pin pin1 = new Pin(detail, 20d, 0d, 1d);
    protected final Pin pin2 = new Pin(pin1, 2d);
    protected final Pin pin3 = new Pin(pin1, 3d);
    protected final Pin pin4 = new Pin(pin1, 4d);
    private final List<Pin> pins = List.of(pin1, pin2, pin3, pin4);

    public void testCalculateShearArea() {
        for (Pin pin : pins) {
            Double expectedArea = pin.getNumberShearSection() * PI * (pow(pin.getOuterDiameter(), 2) - pow(pin.getInternalDiameter(), 2)) / 4;
            assertEquals(expectedArea, pin.calculateShearArea());
        }
    }

    public void testCalculateShearStress() {
        for (Pin pin : pins) {
            Double expectedShearStress = pin.getForce() / pin.getShearArea();
            assertEquals(expectedShearStress, pin.calculateShearStress());
        }
    }

    public void testShearSafetyFactor() {
        for (Pin pin : pins) {
            Double expectedShearSafetyFactor = SHEAR.stressRatio * pin.getYieldStress() / pin.getShearStress();
            assertEquals(expectedShearSafetyFactor, pin.calculateShearSafetyFactor());
        }
    }
}
