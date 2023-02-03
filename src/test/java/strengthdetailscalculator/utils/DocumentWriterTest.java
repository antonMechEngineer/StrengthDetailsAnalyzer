package strengthdetailscalculator.utils;

import org.junit.jupiter.api.Test;
import pl.jsolve.templ4docx.core.Docx;
import strengthdetailscalculator.entity.*;
import strengthdetailscalculator.entity.enums.EarType;
import strengthdetailscalculator.entity.enums.ScrewType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static strengthdetailscalculator.utils.DocumentWriter.*;

class DocumentWriterTest {

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final Pin pin = new Pin(detail, 20d, 0d, 1d);
    private final Axle axle = new Axle(pin, 100d);
    private final Screw screw = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);
    private final Ear ear = new Ear(detail, 20.0, 10.0, 5.0, 0.0, true, EarType.SINGLE, true, 1.25);

    private static final String PATH_CORRECT_SCREW = "src/test/resources/correctDocs/screw.docx";
    private static final String PATH_CORRECT_PIN = "src/test/resources/correctDocs/pin.docx";
    private static final String PATH_CORRECT_AXLE = "src/test/resources/correctDocs/axle.docx";
    private static final String PATH_CORRECT_EAR = "src/test/resources/correctDocs/ear.docx";

    private final DocumentWriter documentWriter = new DocumentWriter();

    @Test
    void writeScrew() {
        documentWriter.writeScrew(screw);
        checkDoc(PATH_CORRECT_SCREW, PATH_SCREW_RES);
    }

    @Test
    void writePin() {
        documentWriter.writePin(pin);
        checkDoc(PATH_CORRECT_PIN, PATH_PIN_RES);
    }

    @Test
    void writeAxle() {
        documentWriter.writeAxle(axle);
        checkDoc(PATH_CORRECT_AXLE, PATH_AXLE_RES);
    }

    @Test
    void writeEar(){
        documentWriter.writeEar(ear);
        checkDoc(PATH_CORRECT_EAR, PATH_EAR_RES);
    }

    private void checkDoc(String pathCorrectDetail, String pathResDetail){
        Docx expectedDocx = new Docx(pathCorrectDetail);
        String expectedTextFromDocx = expectedDocx.readTextContent();
        Docx actualDocx = new Docx(pathResDetail);
        String actualTextFromDocx = actualDocx.readTextContent();
        assertEquals(expectedTextFromDocx, actualTextFromDocx);
    }

}