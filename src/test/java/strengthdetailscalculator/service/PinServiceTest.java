package strengthdetailscalculator.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import strengthdetailscalculator.entity.DetailTest;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.response.Response;

import java.util.List;

import static org.mockito.Mockito.*;
import static strengthdetailscalculator.utils.response.ResponseStatus.SUCCESS;


class PinServiceTest extends DetailTest {
    private final String outerDiameter = "10.2";
    private final String internalDiameter = "0";
    private final String numberShearSect = "2";
    private final List<String> data = List.of(outerDiameter, internalDiameter, numberShearSect);
    Pin expectedPin = new Pin
            (detail, Double.valueOf(outerDiameter), Double.valueOf(internalDiameter), Double.valueOf(numberShearSect));

    private InputDataManager mockInputDataManager = Mockito.mock(InputDataManager.class);
    private DocumentWriter mockDocumentWriter = Mockito.mock(DocumentWriter.class);
    private PinService pinService = new PinService(mockDocumentWriter, mockInputDataManager);

    @Test
    void writeSpecifiedDetail() {
        Response actualRes = pinService.writeSpecifiedDetail(detail, data);
        verify(mockDocumentWriter, times(1)).writePin(expectedPin);
        assertEquals(SUCCESS, actualRes.getResponseStatus());
    }

    @Test
    void build() {
        List<String> data = List.of("10.2", "0", "2");
        Pin actualPin = pinService.build(detail, data);
        assertEquals(expectedPin, actualPin);
    }

    @Test
    void checkData() {
        when(mockInputDataManager.checkMainNumericalData(List.of("0"))).thenReturn(new Response(SUCCESS));
        when(mockInputDataManager.checkNonZeroNumericalData(List.of("10.2"))).thenReturn(new Response(SUCCESS));
        pinService.checkData(data);
        verify(mockInputDataManager, times(1)).checkMainNumericalData(List.of("0"));
        verify(mockInputDataManager, times(1)).checkNonZeroNumericalData(List.of("10.2"));
    }

    @Test
    void prepareData() {
        List<String> correctingData = List.of("10,2", "0", "2");
        pinService.prepareData(correctingData);
        verify(mockInputDataManager, times(1)).replaceCommasWithDots(correctingData);
    }
}