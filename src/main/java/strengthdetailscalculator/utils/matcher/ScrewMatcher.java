package strengthdetailscalculator.utils.matcher;

import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.utils.matcher.interfaces.AxialConclusionGenerated;
import strengthdetailscalculator.utils.matcher.interfaces.ShearConclusionGenerated;

import java.util.HashMap;

public class ScrewMatcher extends DetailMatcher implements ShearConclusionGenerated, AxialConclusionGenerated {

    private static final String MAIN_D = "${d}";
    private static final String GOST = "${gost}";
    private static final String INTERNAL_D = "${d2}";
    private static final String MIN_D = "${d3}";
    private static final String THREAD_PITCH = "${p}";
    private static final String HEIGHT = "${h}";
    private static final String Kh = "${kh}";
    private static final String Kp = "${kp}";
    private static final String SHEAR_AREA = "${fs}";
    private static final String SHEAR_STRESS = "${tay}";
    private static final String N_SHEAR = "${nshear}";
    private static final String TENSION_AREA = "${fmin}";
    private static final String TENSION_STRESS = "${sigma}";
    private static final String N_TENSION = "${ntension}";
    protected static final String SIGN_2 = "${sign2}";
    protected static final String CONCLUSION_2 = "${conclusion2}";

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
        mapTemplate.put(N_SHEAR, String.format( "%,.2f", screw.getShearSafetyFactor()));
        mapTemplate.put(TENSION_AREA, String.format( "%,.2f", screw.calculateAxialArea()));
        mapTemplate.put(TENSION_STRESS, String.format( "%,.0f", screw.getAxialStress()));
        mapTemplate.put(N_TENSION,  String.format( "%,.2f", screw.getAxialSafetyFactor()));
        insertShearConclusion(mapTemplate, screw);
        insertAxialConclusion(mapTemplate, screw);
        return mapTemplate;
    }
}
