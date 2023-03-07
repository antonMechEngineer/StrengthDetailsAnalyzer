package strengthDetailsAnalyzer.entity.interfaces;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.Axle;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Pin;
import static org.junit.Assert.assertEquals;
import static strengthDetailsAnalyzer.entity.enums.StressConditionType.VON_MISSES;

public class ComplexDeformableTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final Pin pin = new Pin(detail, 20d, 0d, 1d);
    private final Axle axle = new Axle(pin, 100d);

    @Test
    public void calculateVonMissesSafetyFactor() {
        Double expectedVonMissesStressSafetyFactor = VON_MISSES.stressRatio * axle.getYieldStress() /axle.calculateVonMissesStress();
        assertEquals(expectedVonMissesStressSafetyFactor, axle.calculateVonMissesSafetyFactor());
    }
}