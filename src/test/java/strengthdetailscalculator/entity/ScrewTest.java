package strengthdetailscalculator.entity;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.enums.ScrewType;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static strengthdetailscalculator.entity.enums.StressConditionType.AXIAL;
import static strengthdetailscalculator.entity.enums.StressConditionType.SHEAR;

public class ScrewTest extends DetailTest {
    protected Screw screw = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);

    @Test
    public void testCalculateShearArea(){
        Double expectedShearArea = PI * screw.getInternalD() * ScrewType.METRICAL.kp * screw.getHeight() * screw.calculateKh();
        assertEquals(expectedShearArea, screw.getShearArea());
    }
    @Test
    public void testCalculateAxialArea(){
        Double expectedAxialArea = PI * pow(screw.getMinD(), 2) / 4;
        assertEquals(expectedAxialArea, screw.getAxialArea());
    }
    @Test
    public void testCalculateKh() {
        Double expectedKh = 5 * screw.getThreadPitch() / screw.getMainD();
        assertEquals(expectedKh, screw.calculateKh());
    }
    @Test
    public void testCalculateShearStress(){
        Double expectedShearStress = screw.getForce() / screw.calculateShearArea();
        assertEquals(expectedShearStress, screw.calculateShearStress());
    }
    @Test
    public void testCalculateShearSafetyFactor(){
        Double expectedShearSafetyFactor =
                SHEAR.stressRatio * screw.getYieldStress() / screw.calculateShearStress();
        assertEquals(expectedShearSafetyFactor, screw.calculateShearSafetyFactor());
    }
    @Test
    public void testCalculateAxialStress(){
        Double expectedAxialStress = screw.getForce() / screw.calculateAxialArea();
        assertEquals(expectedAxialStress, screw.calculateAxialStress());
    }
    @Test
    public void testCalculateAxialSafetyFactor(){
        Double expectedAxialSafetyFactor =
                AXIAL.stressRatio * screw.getYieldStress() / screw.calculateAxialStress();
    }

}
