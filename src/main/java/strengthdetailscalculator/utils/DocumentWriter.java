package strengthdetailscalculator.utils;

import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;
import strengthdetailscalculator.entity.Axle;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.utils.matcher.AxleMatcher;
import strengthdetailscalculator.utils.matcher.PinMatcher;
import strengthdetailscalculator.utils.matcher.ScrewMatcher;

import java.util.HashMap;
import java.util.List;

public class DocumentWriter {
    public static final String PATH_SCREW_TMPL = "src/main/resources/templates/template_screw.docx";
    public static final String PATH_SCREW_RES = "C:\\Users\\user\\Desktop\\РАСЧЕТ_РЕЗЬБЫ.docx";
    public static final String PATH_PIN_TMPL = "src/main/resources/templates/template_pin.docx";
    public static final String PATH_PIN_RES = "C:\\Users\\user\\Desktop\\РАСЧЕТ_ШТИФТА.docx";
    public static final String PATH_AXLE_TMPL = "src/main/resources/templates/template_axle.docx";
    public static final String PATH_AXLE_RES = "C:\\Users\\user\\Desktop\\РАСЧЕТ_ОСИ.docx";

    ScrewMatcher screwMatcher = new ScrewMatcher();
    PinMatcher pinMatcher = new PinMatcher();
    AxleMatcher axleMatcher = new AxleMatcher();

    public void writeScrew(Screw screw) {
        HashMap<String, String> mapTemplate = screwMatcher.getMapTemplateScrew(screw);
        Docx docx = new Docx(PATH_SCREW_TMPL);
        printDoc(docx, mapTemplate);
        docx.save(PATH_SCREW_RES);
    }

    public void writePin(Pin pin) {
        HashMap<String, String> mapTemplate = pinMatcher.getMapTemplatePin(pin);
        Docx docx = new Docx(PATH_PIN_TMPL);
        printDoc(docx, mapTemplate);
        docx.save(PATH_PIN_RES);
    }

    public void writeAxle(Axle axle) {
        HashMap<String, String> mapTemplate = axleMatcher.getMapTemplateAxle(axle);
        Docx docx = new Docx(PATH_AXLE_TMPL);
        printDoc(docx, mapTemplate);
        docx.save(PATH_AXLE_RES);
    }

    private void printDoc(Docx docx, HashMap<String, String> mapTemplate){
        docx.setVariablePattern(new VariablePattern("${", "}"));
        List<String> findVariables = docx.findVariables();
        Variables var = new Variables();
        for (String field : mapTemplate.keySet()) {
            var.addTextVariable(new TextVariable(field, mapTemplate.get(field)));
            docx.fillTemplate(var);
        }
    }
}
