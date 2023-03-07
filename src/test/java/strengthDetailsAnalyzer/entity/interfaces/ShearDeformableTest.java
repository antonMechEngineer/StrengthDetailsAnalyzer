package strengthDetailsAnalyzer.entity.interfaces;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Pin;
import static org.junit.Assert.assertEquals;
import static strengthDetailsAnalyzer.entity.enums.StressConditionType.SHEAR;

public class ShearDeformableTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    protected final Pin pin = new Pin(detail, 20d, 0d, 1d);

    @Test
    public void calculateShearStress() {
        Double expectedShearStress = pin.getForce() / pin.getShearArea();
        assertEquals(expectedShearStress, pin.calculateShearStress());
    }

    @Test
    public void calculateShearSafetyFactor() {
        Double expectedShearSafetyFactor = SHEAR.stressRatio * pin.getYieldStress() / pin.getShearStress();
        assertEquals(expectedShearSafetyFactor, pin.calculateShearSafetyFactor());
    }
}