package strengthdetailscalculator.utils;

import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;
import strengthdetailscalculator.entity.Axle;
import strengthdetailscalculator.entity.Ear;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.utils.matcher.AxleMatcher;
import strengthdetailscalculator.utils.matcher.EarMatcher;
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
    public static final String PATH_EAR_TMPL = "src/main/resources/templates/template_ear.docx";
    public static final String PATH_EAR_RES = "C:\\Users\\user\\Desktop\\РАСЧЕТ_ПРОУШИНЫ.docx";

    ScrewMatcher screwMatcher = new ScrewMatcher();
    PinMatcher pinMatcher = new PinMatcher();
    AxleMatcher axleMatcher = new AxleMatcher();
    EarMatcher earMatcher = new EarMatcher();

    public void writeScrew(Screw screw) {
        write(screwMatcher.getMapTemplateScrew(screw), PATH_SCREW_TMPL, PATH_SCREW_RES);
    }

    public void writePin(Pin pin) {
        write(pinMatcher.getMapTemplatePin(pin), PATH_PIN_TMPL, PATH_PIN_RES);
    }

    public void writeAxle(Axle axle) {
        write(axleMatcher.getMapTemplateAxle(axle), PATH_AXLE_TMPL, PATH_AXLE_RES);
    }

    public void writeEar(Ear ear){
        write(earMatcher.getMapTemplateEar(ear), PATH_EAR_TMPL, PATH_EAR_RES);
    }

    private void write(HashMap<String, String> mapTemplate, String pathTemp, String pathRes) {
        Docx docx = new Docx(pathTemp);
        printDoc(docx, mapTemplate);
        docx.save(pathRes);
    }

    private void printDoc(Docx docx, HashMap<String, String> mapTemplate) {
        docx.setVariablePattern(new VariablePattern("${", "}"));
        List<String> findVariables = docx.findVariables();
        Variables var = new Variables();
        for (String field : mapTemplate.keySet()) {
            var.addTextVariable(new TextVariable(field, mapTemplate.get(field)));
            docx.fillTemplate(var);
        }
    }
}
