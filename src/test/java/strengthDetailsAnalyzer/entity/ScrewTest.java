package strengthDetailsAnalyzer.entity;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.enums.ScrewType;
import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;
import static strengthDetailsAnalyzer.entity.enums.StressConditionType.SHEAR;

public class ScrewTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final Screw screw = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);

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
}
