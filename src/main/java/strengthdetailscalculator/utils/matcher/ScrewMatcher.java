package strengthdetailscalculator.utils.matcher;

import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.utils.matcher.interfaces.AxialReportGenerated;
import strengthdetailscalculator.utils.matcher.interfaces.ShearReportGenerated;

import java.util.HashMap;

public class ScrewMatcher extends DetailMatcher implements ShearReportGenerated, AxialReportGenerated {

    private static final String MAIN_D = "${d}";
    private static final String GOST = "${gost}";
    private static final String INTERNAL_D = "${d2}";
    private static final String MIN_D = "${d3}";
    private static final String THREAD_PITCH = "${p}";
    private static final String HEIGHT = "${height}";
    private static final String Kh = "${kh}";
    private static final String Kp = "${kp}";

    public HashMap<String, String> getMapTemplateScrew(Screw screw) {
        HashMap<String, String> mapTemplate = getMapTemplateDetail(screw);
        mapTemplate.put(MAIN_D, String.format( "%,.0f", screw.getMainD()));
        mapTemplate.put(GOST, screw.getScrewType().gost);
        mapTemplate.put(INTERNAL_D, String.format( "%,.3f", screw.getInternalD()));
        mapTemplate.put(MIN_D, String.format( "%,.3f", screw.getMinD()));
        mapTemplate.put(THREAD_PITCH, String.format( "%,.2f", screw.getThreadPitch()));
        mapTemplate.put(HEIGHT, String.format( "%,.1f", screw.getHeight()));
        mapTemplate.put(Kh, String.format( "%,.2f", screw.calculateKh()));
        mapTemplate.put(Kp, String.format( "%,.2f", screw.getScrewType().kp));
        mapTemplate.put(SHEAR_AREA, String.format( "%,.2f", screw.calculateShearArea()));
        mapTemplate.put(SHEAR_STRESS, String.format( "%,.0f", screw.getShearStress()));
        mapTemplate.put(SHEAR_SAFETY_FACTOR , String.format( "%,.2f", screw.getShearSafetyFactor()));
        mapTemplate.put(AXIAL_AREA, String.format( "%,.2f", screw.calculateAxialArea()));
        mapTemplate.put(AXIAL_STRESS, String.format( "%,.0f", screw.getAxialStress()));
        mapTemplate.put(AXIAL_SAFETY_FACTOR,  String.format( "%,.2f", screw.getAxialSafetyFactor()));
        insertShearConclusion(mapTemplate, screw);
        insertAxialConclusion(mapTemplate, screw);
        return mapTemplate;
    }
}
