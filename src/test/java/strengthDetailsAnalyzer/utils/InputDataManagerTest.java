package strengthDetailsAnalyzer.utils;
import org.junit.Test;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Screw;
import strengthDetailsAnalyzer.entity.enums.ScrewType;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static strengthDetailsAnalyzer.utils.response.ResponseStatus.FAIL;
import static strengthDetailsAnalyzer.utils.response.ResponseStatus.SUCCESS;

public class InputDataManagerTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    InputDataManager inputDataManager = new InputDataManager();

    @Test
    public void checkTextData() {
        String correctString = "abc";
        String unCorrectString = "";
        assertEquals(SUCCESS, inputDataManager.checkTextData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkTextData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    public void checkNonZeroNumericalData() {
        String correctString = "12345.6";
        String unCorrectString = "0";
        assertEquals(SUCCESS, inputDataManager.checkNonZeroNumericalData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkNonZeroNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    public void checkZNumericalData() {
        String correctString = "12345";
        String unCorrectString = "1.5";
        assertEquals(SUCCESS, inputDataManager.checkZNumericalData (List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkZNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    public void checkMainNumericalData() {
        String correctString = "12345.5";
        String unCorrectString = "ab1";
        assertEquals(SUCCESS, inputDataManager.checkMainNumericalData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkMainNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    public void checkPositiveNumericalData() {
        String correctString = "12345.5";
        String unCorrectString = "0";
        assertEquals(SUCCESS, inputDataManager.checkPositiveNumericalData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkPositiveNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    public void checkNaturalNumericalData() {
        String correctString = "12345";
        String unCorrectString = "0.1";
        assertEquals(SUCCESS, inputDataManager.checkNaturalNumericalData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkNaturalNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    public void checkInputThreadProperties() {
        Screw correctScrew = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);
        Screw unCorrectScrew = new Screw(detail, 10d, 5d, 7d, ScrewType.METRICAL, 0d, 0d);
        assertEquals(SUCCESS, inputDataManager.checkInputThreadProperties(correctScrew) .getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkInputThreadProperties(unCorrectScrew).getResponseStatus());
    }

    @Test
    public void replaceCommasWithDots() {
        String comma = ",";
        String processedString = inputDataManager.replaceCommasWithDots(comma);
        assertEquals(".", processedString);
    }

    @Test
    public void testReplaceCommasWithDots() {
        List<String> commas = List.of(",");
        List<String> processedStrings = inputDataManager.replaceCommasWithDots(commas);
        assertEquals( List.of("."), processedStrings);
    }
}