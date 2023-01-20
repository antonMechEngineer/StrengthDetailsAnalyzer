package strengthdetailscalculator.utils;

import org.junit.jupiter.api.Test;
import strengthdetailscalculator.entity.DetailTest;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;

import java.util.List;

import static strengthdetailscalculator.utils.response.ResponseStatus.FAIL;
import static strengthdetailscalculator.utils.response.ResponseStatus.SUCCESS;

class InputDataManagerTest extends DetailTest {

    InputDataManager inputDataManager = new InputDataManager();
    @Test
    void checkTextData() {
        String correctString = "abc";
        String unCorrectString = "";
        assertEquals(SUCCESS, inputDataManager.checkTextData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkTextData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    void checkNonZeroNumericalData() {
        String correctString = "12345.6";
        String unCorrectString = "0";
        assertEquals(SUCCESS, inputDataManager.checkNonZeroNumericalData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkNonZeroNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    void checkZNumericalData() {
        String correctString = "12345";
        String unCorrectString = "1.5";
        assertEquals(SUCCESS, inputDataManager.checkZNumericalData (List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkZNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    void checkMainNumericalData() {
        String correctString = "12345.5";
        String unCorrectString = "ab1";
        assertEquals(SUCCESS, inputDataManager.checkMainNumericalData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkMainNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    void checkPositiveNumericalData() {
        String correctString = "12345.5";
        String unCorrectString = "0";
        assertEquals(SUCCESS, inputDataManager.checkPositiveNumericalData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkPositiveNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    void checkNaturalNumericalData() {
        String correctString = "12345";
        String unCorrectString = "0.1";
        assertEquals(SUCCESS, inputDataManager.checkNaturalNumericalData(List.of(correctString)).getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkNaturalNumericalData(List.of(unCorrectString)).getResponseStatus());
    }

    @Test
    void checkInputThreadProperties() {
        Screw correctScrew = new Screw(detail, 10d, 1d, 7d, ScrewType.METRICAL, 8.917, 8.773);
        Screw unCorrectScrew = new Screw(detail, 10d, 5d, 7d, ScrewType.METRICAL, 8.917, 8.773);
        assertEquals(SUCCESS, inputDataManager.checkInputThreadProperties(correctScrew) .getResponseStatus());
        assertEquals(FAIL, inputDataManager.checkInputThreadProperties(unCorrectScrew).getResponseStatus());
    }

    @Test
    void replaceCommasWithDots() {
        String comma = ",";
        String processedString = inputDataManager.replaceCommasWithDots(comma);
        assertEquals(".", processedString);
    }

    @Test
    void testReplaceCommasWithDots() {
        List<String> commas = List.of(",");
        List<String> processedStrings = inputDataManager.replaceCommasWithDots(commas);
        assertEquals( List.of("."), processedStrings);
    }


}