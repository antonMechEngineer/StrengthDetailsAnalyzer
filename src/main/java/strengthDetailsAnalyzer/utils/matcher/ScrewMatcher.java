package strengthDetailsAnalyzer.utils.matcher;

import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Screw;
import strengthDetailsAnalyzer.utils.matcher.interfaces.AxialReportGenerated;
import strengthDetailsAnalyzer.utils.matcher.interfaces.ShearReportGenerated;
import java.util.HashMap;

@Component
public class ScrewMatcher extends DetailMatcher implements ShearReportGenerated, AxialReportGenerated {

    public static final String MAIN_D = "${d}";
    public static final String GOST = "${gost}";
    public static final String INTERNAL_D = "${d2}";
    public static final String MIN_D = "${d3}";
    public static final String THREAD_PITCH = "${p}";
    public static final String HEIGHT = "${height}";
    public static final String Kh = "${kh}";
    public static final String Kp = "${kp}";
    public static final String FORCE = "${force}";

    public HashMap<String, String> getMapTemplateScrew(Screw screw) {
        HashMap<String, String> mapTemplate = getMapTemplateDetail(screw);
        insertScrewProperties(mapTemplate, screw);
        insertShearResults(mapTemplate, screw);
        insertShearConclusion(mapTemplate, screw);
        insertAxialResult(mapTemplate, screw);
        insertAxialConclusion(mapTemplate, screw);
        return mapTemplate;
    }

    private void insertScrewProperties(HashMap<String, String> mapTemplate, Screw screw){
        mapTemplate.put(MAIN_D, String.format( "%,.0f", screw.getMainD()));
        mapTemplate.put(GOST, screw.getScrewType().gost);
        mapTemplate.put(INTERNAL_D, String.format( "%,.3f", screw.getInternalD()));
        mapTemplate.put(MIN_D, String.format( "%,.3f", screw.getMinD()));
        mapTemplate.put(THREAD_PITCH, String.format( "%,.2f", screw.getThreadPitch()));
        mapTemplate.put(HEIGHT, String.format( "%,.1f", screw.getHeight()));
        mapTemplate.put(Kh, String.format( "%,.2f", screw.calculateKh()));
        mapTemplate.put(Kp, String.format( "%,.2f", screw.getScrewType().kp));
        mapTemplate.put(FORCE, String.format( "%,.0f", screw.getForce()));
    }

}
