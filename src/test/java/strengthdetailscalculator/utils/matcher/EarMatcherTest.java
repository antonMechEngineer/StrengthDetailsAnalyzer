package strengthdetailscalculator.utils.matcher;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Ear;
import strengthdetailscalculator.entity.enums.EarType;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static strengthdetailscalculator.utils.matcher.EarMatcher.*;


class EarMatcherTest {

    private final Double outerD = 20.0;
    private final Double internalD = 10.0;
    private final Double thickness = 5.0;
    private final Double eccentricity = 0.0;
    private final EarType earType = EarType.SINGLE;
    private final Boolean isSingleShearedConnection =  true;
    private final Boolean currentEarIsLarger = true;
    private final Double gap = 1.25;

    private final List<String> data = List.of(outerD.toString(), internalD.toString(), thickness.toString(), eccentricity.toString(),
            earType.description, isSingleShearedConnection.toString(), currentEarIsLarger.toString(), gap.toString());
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final Ear ear = new Ear(detail, outerD, internalD, thickness, eccentricity, isSingleShearedConnection, earType,  currentEarIsLarger, gap);
    private final EarMatcher earMatcher = new EarMatcher();

    @Test
    void getMapTemplateEar() {
        HashMap<String, String> expectedMapTemplate = new HashMap<>();
        insertExpectedEarTemplate(expectedMapTemplate);
        HashMap<String, String> actualMapTemplate = earMatcher.getMapTemplateEar(ear);
        assertEquals(expectedMapTemplate.get(OUTER_DIAMETER), actualMapTemplate.get(OUTER_DIAMETER));
        assertEquals(expectedMapTemplate.get(INTERNAL_DIAMETER), actualMapTemplate.get(INTERNAL_DIAMETER));
        assertEquals(expectedMapTemplate.get(THICKNESS), actualMapTemplate.get(THICKNESS));
        assertEquals(expectedMapTemplate.get(ECCENTRICITY), actualMapTemplate.get(ECCENTRICITY));
        assertEquals(expectedMapTemplate.get(K), actualMapTemplate.get(K));
        assertEquals(expectedMapTemplate.get(KC), actualMapTemplate.get(KC));
        assertEquals(expectedMapTemplate.get(GAP), actualMapTemplate.get(GAP));
        assertEquals(expectedMapTemplate.get(ALFA_EQUATION), actualMapTemplate.get(ALFA_EQUATION));
        assertEquals(expectedMapTemplate.get(ALFA_EXPRESSION), actualMapTemplate.get(ALFA_EXPRESSION));
        assertEquals(expectedMapTemplate.get(K_EQUATION), actualMapTemplate.get(K_EQUATION));
        assertEquals(expectedMapTemplate.get(K_EXPRESSION), actualMapTemplate.get(K_EXPRESSION));
        assertEquals(expectedMapTemplate.get(K_DESCRIPTION), actualMapTemplate.get(K_DESCRIPTION));
    }

    private void insertExpectedEarTemplate(HashMap<String, String> expectedMapTemplate){
        String spaceInterval = " ".repeat(45);
        expectedMapTemplate.put("${eccentricity}", "0");
        expectedMapTemplate.put("${internalD}", "10");
        expectedMapTemplate.put("${KC}", "0,00");
        expectedMapTemplate.put("${alfa}", "0,825");
        expectedMapTemplate.put("${k}", "0,20");
        expectedMapTemplate.put("${outerD}", "20");
        expectedMapTemplate.put("${thickness}", "5");
        expectedMapTemplate.put("${gap}", "1,25");
        expectedMapTemplate.put("${alfaEquation}", "1,025 + 0,92 ∙ c / d ∙ ( D / d – 1) – 0,1 ∙ D / d = " + spaceInterval);
        expectedMapTemplate.put("${alfaExpression}", "1,025 + 0,92 ∙ 0 / 10 ∙ ( 20 / 10 – 1) – 0,1 ∙ 20 / 10) = ");
        expectedMapTemplate.put("${kEquation}", " 1 / ( 4,5 + 2 ∙ s / t ) = ");
        expectedMapTemplate.put("${kExpression}", " 1 / ( 4,5 + 2 ∙ 1,25 / 5 ) = ");
        expectedMapTemplate.put("${kDescription}", "где  s = 1,25 мм – зазор между проушинами.");
    }
}