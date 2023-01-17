import strengthdetailscalculator.entity.Pin;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static strengthdetailscalculator.entity.enums.StressConditionType.SHEAR;

public class TestPin extends TestDetail {

    private final Pin pin = new Pin(detail, 20d, 0d, 2d);

    public void testCalculateShearArea(){
        Double expectedArea = pin.getNumberShearSection() * PI * (pow(pin.getOuterDiameter(), 2) - pow(pin.getInternalDiameter(), 2)) / 4;
        assertEquals(expectedArea, pin.calculateShearArea());
    }

    public void testCalculateShearStress(){
        Double expectedShearStress = pin.getForce()/pin.getShearArea();
        assertEquals(expectedShearStress, pin.calculateShearStress(pin.getForce()));
    }

    public void testShearSafetyFactor(){
        Double expectedShearSafetyFactor = SHEAR.stressRatio * pin.getYieldStress()/pin.getShearStress();
        assertEquals(expectedShearSafetyFactor, pin.calculateShearSafetyFactor(pin.getForce(), pin.getYieldStress()));
    }
}
