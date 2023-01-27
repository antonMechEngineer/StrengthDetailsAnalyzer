package strengthdetailscalculator.entity.interfaces;
import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static strengthdetailscalculator.entity.enums.StressConditionType.AXIAL;

class AxialDeformableTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    protected Screw screw = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);

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