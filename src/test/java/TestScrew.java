import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;

import static java.lang.Math.*;

public class TestScrew extends TestDetail {
    Screw screw = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);

    public void testCalculateShearArea(){
        Double expectedShearArea = PI * screw.getInternalD() * ScrewType.METRICAL.kp * screw.getHeight() * screw.calculateKh();
        assertEquals(expectedShearArea, screw.getShearArea());
    }

    public void testCalculateAxialArea(){

    }

    public void testCalculateKh() {
        Double expectedKh = 5 * screw.getThreadPitch() / screw.getMainD();
        assertEquals(expectedKh, screw.calculateKh());
    }

}
