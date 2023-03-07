package strengthDetailsAnalyzer.entity.interfaces;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Screw;
import strengthDetailsAnalyzer.entity.enums.ScrewType;
import static org.junit.Assert.assertEquals;
import static strengthDetailsAnalyzer.entity.enums.StressConditionType.AXIAL;

public class AxialDeformableTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    protected Screw screw = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);

    @Test
    public void testCalculateAxialStress(){
        Double expectedAxialStress = screw.getForce() / screw.calculateAxialArea();
        assertEquals(expectedAxialStress, screw.calculateAxialStress());
    }

    @Test
    public void testCalculateAxialSafetyFactor(){
        Double expectedAxialSafetyFactor = AXIAL.stressRatio * screw.getYieldStress() / screw.calculateAxialStress();
    }
}