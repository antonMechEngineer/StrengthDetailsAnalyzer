package strengthDetailsAnalyzer.entity;
import org.junit.Test;

import java.util.List;
import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static org.junit.Assert.assertEquals;
public class PinTest  {

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final Pin pin1 = new Pin(detail, 20d, 0d, 1d);
    private final Pin pin2 = new Pin(pin1, 2d);
    private final Pin pin3 = new Pin(pin1, 3d);
    private final Pin pin4 = new Pin(pin1, 4d);
    private final List<Pin> pins = List.of(pin1, pin2, pin3, pin4);

    @Test
    public void testCalculateShearArea() {
        for (Pin pin : pins) {
            Double expectedArea = pin.getNumberShearSection() * PI * (pow(pin.getOuterDiameter(), 2) - pow(pin.getInternalDiameter(), 2)) / 4;
            assertEquals(expectedArea, pin.calculateShearArea());
        }
    }
}
