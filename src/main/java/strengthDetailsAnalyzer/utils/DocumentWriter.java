package strengthDetailsAnalyzer.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;
import strengthDetailsAnalyzer.controller.interfaces.Alerted;
import strengthDetailsAnalyzer.entity.*;
import strengthDetailsAnalyzer.utils.matcher.*;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;

import java.util.HashMap;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Component
public class DocumentWriter implements Alerted {
    public static final String PATH_SCREW_TMPL = "src/main/resources/templates/template_screw.docx";
    public static final String PATH_PIN_TMPL = "src/main/resources/templates/template_pin.docx";
    public static final String PATH_AXLE_TMPL = "src/main/resources/templates/template_axle.docx";
    public static final String PATH_EAR_TMPL = "src/main/resources/templates/template_ear.docx";
    public static final String  PATH_WELD_TMPL = "src/main/resources/templates/template_weld.docx";

    @Value("${paths.result.screw}")
    private String pathScrewRes;

    @Value("${paths.result.ear}")
    private String pathEarRes;

    @Value("${paths.result.axle}")
    private String pathAxleRes;

    @Value("${paths.result.pin}")
    private String pathPinRes;

    @Value("${paths.result.weld}")
    private String pathWeldRes;

    private final ScrewMatcher screwMatcher;
    private final PinMatcher pinMatcher;
    private final AxleMatcher axleMatcher;
    private final EarMatcher earMatcher;
    private final WeldMatcher weldMatcher;

    public Response writeScrew(Screw screw) {
        return write(screwMatcher.getMapTemplateScrew(screw), PATH_SCREW_TMPL, getDocFileName(screw, pathScrewRes));
    }

    public Response writePin(Pin pin) {
        return write(pinMatcher.getMapTemplatePin(pin), PATH_PIN_TMPL, getDocFileName(pin, pathPinRes));
    }

    public Response writeAxle(Axle axle) {
        return write(axleMatcher.getMapTemplateAxle(axle), PATH_AXLE_TMPL, getDocFileName(axle, pathAxleRes));
    }

    public Response writeEar(Ear ear) {
        return write(earMatcher.getMapTemplateEar(ear), PATH_EAR_TMPL, getDocFileName(ear, pathEarRes));
    }

    public Response writeWeld(Weld weld){
        return write(weldMatcher.getMapTemplateWeld(weld), PATH_WELD_TMPL, getDocFileName(weld, pathWeldRes));
    }

    private String getDocFileName(Detail detail, String filePrefix){
        return filePrefix + detail.getName() + "_" + detail.getCode()+ ".docx";
    }


    private Response write(HashMap<String, String> mapTemplate, String pathTemp, String pathRes) {
        Docx docx = new Docx(pathTemp);
        printDoc(docx, mapTemplate);
        try {
            docx.save(pathRes);

        } catch (Exception e) {
            return new Response(ResponseStatus.FAIL, "Убедитесь, что файл куда вы пытаетесь записать закрыт.");
        }
        return new Response(ResponseStatus.SUCCESS);
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
