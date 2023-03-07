package strengthDetailsAnalyzer.entity;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.enums.EarType;

import static org.junit.Assert.assertEquals;

public class EarTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final Ear ear1 = new Ear(detail, 20.0, 10.0, 5.0, 0.0, false, EarType.STEEL_CENTRAL, false, 0.0);
    private final Ear ear2 = new Ear(detail, 30.0, 10.0, 5.0, 7.0, false, EarType.STEEL_SIDE, false, 0.0);
    private final Ear ear3 = new Ear(detail, 20.0, 10.0, 5.0, 0.0, false, EarType.ALUMINUM_SIDE, false, 0.0);
    private final Ear ear4 = new Ear(detail, 20.0, 10.0, 5.0, 0.0, true, EarType.SINGLE, false, 0.0);
    private final Ear ear5 = new Ear(detail, 20.0, 10.0, 5.0, 0.0, true, EarType.SINGLE, true, 1.25);

    @Test
    public void calculateAxialArea() {
        Double expectedAxialArea = 41.25;
        assertEquals(expectedAxialArea, ear1.calculateAxialArea());
    }

    @Test
    public void calculateK() {
        Double expectedK1 = 1.0;
        Double expectedK2 = 0.9;
        Double expectedK3 = 0.75;
        Double expectedK4 = 0.35;
        Double expectedK5 = 0.2;
        assertEquals(expectedK1, ear1.calculateK());
        assertEquals(expectedK2, ear2.calculateK());
        assertEquals(expectedK3, ear3.calculateK());
        assertEquals(expectedK4, ear4.calculateK());
        assertEquals(expectedK5, ear5.calculateK());
    }

    @Test
    public void calculateAlfa() {
        Double expectedAlfa1 = 0.825;
        Double expectedAlfa2 = 1.0;
        assertEquals(expectedAlfa1, ear1.calculateAlfa());
        assertEquals(expectedAlfa2, ear2.calculateAlfa());
    }
}