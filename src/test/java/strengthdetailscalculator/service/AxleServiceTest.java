package strengthdetailscalculator.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import strengthdetailscalculator.entity.Axle;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Pin;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static strengthdetailscalculator.utils.response.ResponseStatus.SUCCESS;

class AxleServiceTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final String outerDiameter = "10.2";
    private final String internalDiameter = "0";
    private final String numberShearSect = "2";
    private final String supportLength = "50";
    private final List<String> data = List.of(outerDiameter, internalDiameter, numberShearSect, supportLength);
    private final Pin expectedPin = new Pin
            (detail, Double.valueOf(outerDiameter), Double.valueOf(internalDiameter), Double.valueOf(numberShearSect));
    private final Axle expectedAxle = new Axle(expectedPin, Double.valueOf(supportLength));
    private InputDataManager mockInputDataManager = Mockito.mock(InputDataManager.class);
    private DocumentWriter mockDocumentWriter = Mockito.mock(DocumentWriter.class);
    private AxleService axleService = new AxleService(mockDocumentWriter, mockInputDataManager);

    @Test
    void writeSpecifiedDetail() {
        when(mockInputDataManager.checkNonZeroNumericalData(List.of("50.0"))).thenReturn(new Response(SUCCESS));
        Response actualRes = axleService.writeSpecifiedDetail(detail, data);
        verify(mockInputDataManager, times(1)).checkNonZeroNumericalData(List.of("50.0"));
        verify(mockDocumentWriter, times(1)).writeAxle(expectedAxle);
        assertEquals(SUCCESS, actualRes.getResponseStatus());
    }

    @Test
    void build() {
        List<String> data = List.of("10.2", "0", "2", "50");
        Axle actualAxle = axleService.build(detail, data);
        assertEquals(expectedAxle, actualAxle);
    }

    @Test
    void checkData() {
        when(mockInputDataManager.checkMainNumericalData(List.of("0"))).thenReturn(new Response(SUCCESS));
        when(mockInputDataManager.checkNonZeroNumericalData(List.of("10.2"))).thenReturn(new Response(SUCCESS));
        when(mockInputDataManager.checkPositiveNumericalData(List.of("50"))).thenReturn(new Response(SUCCESS));
        axleService.checkData(data);
        verify(mockInputDataManager, times(1)).checkMainNumericalData(List.of("0"));
        verify(mockInputDataManager, times(1)).checkNonZeroNumericalData(List.of("10.2"));
        verify(mockInputDataManager, times(1)).checkPositiveNumericalData(List.of("50"));
    }
}