package strengthdetailscalculator.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Math.pow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AxleTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final Pin pin1 = new Pin(detail, 20d, 0d, 1d);
    private final Pin pin2 = new Pin(pin1, 2d);
    private final Pin pin3 = new Pin(pin1, 3d);
    private final Pin pin4 = new Pin(pin1, 4d);
    private final Axle axle1 = new Axle(pin1, 100d);
    private final Axle axle2 = new Axle(pin2, 100d);
    private final Axle axle3 = new Axle(pin3, 100d);
    private final Axle axle4 = new Axle(pin4, 100d);
    private final List<Axle> axles = List.of(axle1, axle2, axle3, axle4);

    @Test
    public void calculateBendingResistance() {
        Double expectedBendingResistance = 0.1 * (pow(axle1.getOuterDiameter(), 3) - (pow(axle1.getInternalDiameter(), 3)));
        assertEquals(expectedBendingResistance, axle1.calculateBendingResistance());
    }

    @Test
    public void calculateBendingMoment() {
        Double expectedBendingMoment1 = axle1.getForce() * axle1.getSupportLength();
        assertEquals(expectedBendingMoment1, axle1.calculateBendingMoment());
        Double expectedBendingMoment2 = axle2.getForce() * axle2.getSupportLength() / 4;
        assertEquals(expectedBendingMoment2, axle2.calculateBendingMoment());
        Double expectedBendingMoment3 = axle3.getForce() * axle3.getSupportLength() * 3 / 64;
        assertEquals(expectedBendingMoment3, axle3.calculateBendingMoment());
        Double expectedBendingMoment4 = axle4.getForce() * axle4.getSupportLength() * 3 / 128;
        assertEquals(expectedBendingMoment4, axle4.calculateBendingMoment());
    }

    @Test
    public void calculateVonMisesStress() {
        Double expectedVonMissesStress1 = pow((pow(axle1.calculateBendingStress(), 2) + 3 * pow(axle1.calculateShearStress(), 2)), 0.5);
        assertEquals(expectedVonMissesStress1, axle1.calculateVonMissesStress());
    }
}
