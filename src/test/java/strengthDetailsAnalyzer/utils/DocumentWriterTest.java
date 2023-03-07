package strengthDetailsAnalyzer.utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jsolve.templ4docx.core.Docx;
import strengthDetailsAnalyzer.entity.*;
import strengthDetailsAnalyzer.entity.enums.EarType;
import strengthDetailsAnalyzer.entity.enums.ScrewType;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DocumentWriterTest {

    @Autowired
    private DocumentWriter documentWriter;

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final Pin pin = new Pin(detail, 20d, 0d, 1d);
    private final Axle axle = new Axle(pin, 100d);
    private final Screw screw = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);
    private final Ear ear = new Ear(detail, 20.0, 10.0, 5.0, 0.0, true, EarType.SINGLE, true, 1.25);

    @Test
    public void writeScrew() {
        documentWriter.writeScrew(screw);
        String pathCorrectScrew = "src/test/resources/correctDocs/screw.docx";
        String pathScrewRes = "C:/Users/user/Desktop/RESULTS/SCREW_name_code.docx";
        checkDoc(pathCorrectScrew, pathScrewRes);
    }

    @Test
    public void writePin() {
        documentWriter.writePin(pin);
        String pathPinRes = "C:/Users/user/Desktop/RESULTS/PIN_name_code.docx";
        String pathCorrectPin = "src/test/resources/correctDocs/pin.docx";
        checkDoc(pathCorrectPin, pathPinRes);
    }

    @Test
    public void writeAxle() {
        documentWriter.writeAxle(axle);
        String pathAxleRes = "C:/Users/user/Desktop/RESULTS/AXLE_name_code.docx";
        String pathCorrectAxle = "src/test/resources/correctDocs/axle.docx";
        checkDoc(pathCorrectAxle, pathAxleRes);
    }

    @Test
    public void writeEar(){
        documentWriter.writeEar(ear);
        String pathEarRes = "C:/Users/user/Desktop/RESULTS/EAR_name_code.docx";
        String pathCorrectEar = "src/test/resources/correctDocs/ear.docx";
        checkDoc(pathCorrectEar, pathEarRes);
    }

    private void checkDoc(String pathCorrectDetail, String pathResDetail){
        Docx expectedDocx = new Docx(pathCorrectDetail);
        String expectedTextFromDocx = expectedDocx.readTextContent();
        Docx actualDocx = new Docx(pathResDetail);
        String actualTextFromDocx = actualDocx.readTextContent();
        assertEquals(expectedTextFromDocx, actualTextFromDocx);
    }

}