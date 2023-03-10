package strengthDetailsAnalyzer.utils.matcher;

import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Ear;
import strengthDetailsAnalyzer.utils.matcher.interfaces.AxialReportGenerated;
import java.util.HashMap;
import static strengthDetailsAnalyzer.entity.Ear.DEFAULT_ALFA;
import static strengthDetailsAnalyzer.entity.enums.EarType.*;

@Component
public class EarMatcher extends DetailMatcher implements AxialReportGenerated {

    public static final String OUTER_DIAMETER = "${outerD}";
    public static final String INTERNAL_DIAMETER = "${internalD}";
    public static final String ECCENTRICITY = "${eccentricity}";
    public static final String THICKNESS = "${thickness}";
    public static final String EAR_TYPE = "${earType}";
    public static final String GAP = "${gap}";
    public static final String EAR_POSITION = "${earPosition}";
    public static final String ALFA = "${alfa}";
    public static final String K = "${k}";
    public static final String KC = "${KC}";
    public static final String ALFA_EQUATION = "${alfaEquation}";
    public static final String ALFA_EXPRESSION = "${alfaExpression}";
    public static final String K_EQUATION = "${kEquation}";
    public static final String K_EXPRESSION = "${kExpression}";
    public static final String K_DESCRIPTION = "${kDescription}";
    public static final String FORCE = "${force}";

    public HashMap<String, String> getMapTemplateEar(Ear ear) {
        HashMap<String, String> mapTemplate = getMapTemplateDetail(ear);
        insertEarProperties(mapTemplate, ear);
        insertAxialResult(mapTemplate, ear);
        insertAxialConclusion(mapTemplate, ear);
        return mapTemplate;
    }

    private void insertEarProperties(HashMap<String, String> mapTemplate, Ear ear) {
        mapTemplate.put(OUTER_DIAMETER, String.format("%,.0f", ear.getOuterD()));
        mapTemplate.put(INTERNAL_DIAMETER, String.format("%,.0f", ear.getInternalD()));
        mapTemplate.put(ECCENTRICITY, String.format("%,.0f", ear.getEccentricity()));
        mapTemplate.put(THICKNESS, String.format("%,.0f", ear.getThickness()));
        mapTemplate.put(EAR_TYPE, ear.getEarType().name());
        mapTemplate.put(ALFA, String.format("%,.2f", ear.calculateAlfa()));
        mapTemplate.put(K, String.format("%,.2f", ear.calculateK()));
        mapTemplate.put(KC, String.format("%,.2f", ear.getKC()));
        mapTemplate.put(EAR_POSITION, ear.getEarType().name());
        mapTemplate.put(FORCE, String.format( "%,.0f", ear.getForce()));
        insertAlfaDescription(mapTemplate, ear);
        insertKDescription(mapTemplate, ear);
    }

    private void insertAlfaDescription(HashMap<String, String> mapTemplate, Ear ear) {
        Double currentAlfa = ear.calculateAlfa();
        String formOuterD = String.format("%,.0f", ear.getOuterD());
        String formInternalD = String.format("%,.0f", ear.getInternalD());
        String formEccentricity = String.format("%,.0f", ear.getEccentricity());
        String alfaExpression = "";
        String spaceInterval = " ".repeat(45);
        if (currentAlfa.equals(DEFAULT_ALFA)) {
            mapTemplate.put(ALFA_EQUATION, "");
            mapTemplate.put(ALFA_EXPRESSION, alfaExpression);
        } else {
            alfaExpression = "1,025 + 0,92 ??? " + formEccentricity + " / " + formInternalD + " ??? " + "( " +
                    formOuterD + " / " + formInternalD + " ??? 1) ??? 0,1 ??? " + formOuterD + " / " + formInternalD + ") = ";
            mapTemplate.put(ALFA_EQUATION, "1,025 + 0,92 ??? c / d ??? ( D / d ??? 1) ??? 0,1 ??? D / d = " + spaceInterval);
            mapTemplate.put(ALFA_EXPRESSION, alfaExpression);
        }
    }

    private void insertKDescription(HashMap<String, String> mapTemplate, Ear ear) {
        Double currentK = ear.calculateK();
        String kEquation = "";
        String kExpression = "";
        String kDescription = "?????????????????????? ?????????????????????? ";
        if (!ear.getIsSingleShearedConnection()) {
            mapTemplate.put(K_EQUATION, kEquation);
            mapTemplate.put(K_EXPRESSION, kExpression);
        }
        if (currentK.equals(STEEL_CENTRAL.val)) {
            mapTemplate.put(K_DESCRIPTION, kDescription + "??.??. ???????????????? " + STEEL_CENTRAL.description.toLowerCase() + ".");
        } else if (currentK.equals(STEEL_SIDE.val)) {
            mapTemplate.put(K_DESCRIPTION, kDescription + "??.??. ???????????????? " + STEEL_SIDE.description.toLowerCase() + ".");
        } else if (currentK.equals(ALUMINUM_SIDE.val)) {
            mapTemplate.put(K_DESCRIPTION, kDescription + "??.??. ???????????????? " + ALUMINUM_SIDE.description.toLowerCase() + ".");
        } else {
            if (ear.getCurrentEarIsLarger()) {
                kEquation = " 1 / ( 4,5 + 2 ??? s / t ) = ";
                kExpression = " 1 / ( 4,5 + 2 ??? " + String.format("%,.2f", ear.getGap()) + " / " + String.format("%,.0f", ear.getThickness()) + " ) = ";
                kDescription = "??????  s = " + String.format("%,.2f", ear.getGap()) + " ???? ??? ?????????? ?????????? ????????????????????.";
                mapTemplate.put(K_EQUATION, kEquation);
                mapTemplate.put(K_EXPRESSION, kExpression);
                mapTemplate.put(K_DESCRIPTION, kDescription);
                mapTemplate.put(GAP, String.format("%,.2f", ear.getGap()));
            } else {
                mapTemplate.put(K_EQUATION, kEquation);
                mapTemplate.put(K_EXPRESSION, kExpression);
                mapTemplate.put(K_DESCRIPTION, kDescription + "?????? ???????????????? ?????????????? ?????????????? ?? ?????????????????????? ????????????????????.");
            }
        }
    }

}
