package strengthdetailscalculator.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Ear;
import strengthdetailscalculator.entity.enums.EarType;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.response.Response;
import strengthdetailscalculator.utils.response.ResponseStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class EarServiceTest{

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final Ear expectedEar = new Ear(detail, 20.0, 10.0, 5.0, 0.0, true, EarType.SINGLE, false, 0.0);
    private final ArrayList<String> unPreparedData = new ArrayList<>(Arrays.asList("20,0", "10", "5", "0", null, "true", "false", null));
    private final ArrayList<String> preparedData = new ArrayList<>(Arrays.asList("20.0", "10", "5", "0", "", "true", "false", "0"));
    private InputDataManager mockInputDataManager = Mockito.mock(InputDataManager.class);
    private DocumentWriter mockDocumentWriter = Mockito.mock(DocumentWriter.class);
    EarService earService = new EarService(mockDocumentWriter, mockInputDataManager);

    @Test
    void writeSpecifiedDetail() {
        Response actualResponse = earService.writeSpecifiedDetail(detail, preparedData);
        verify(mockDocumentWriter, times(1)).writeEar(expectedEar);
        assertEquals(ResponseStatus.SUCCESS, actualResponse.getResponseStatus());
    }

    @Test
    void build() {
        assertEquals(expectedEar, earService.build(detail, preparedData));
    }

    @Test
    void checkData() {
        List<String> nonZeroProperties = List.of("20,0", "10", "5");
        List<String> availableZeroProperties = List.of("0");
        when(mockInputDataManager.checkPositiveNumericalData(nonZeroProperties)).thenReturn(new Response(ResponseStatus.SUCCESS));
        when(mockInputDataManager.checkMainNumericalData(availableZeroProperties)).thenReturn(new Response(ResponseStatus.SUCCESS));
        earService.checkData(unPreparedData);
        verify(mockInputDataManager, times(1)).checkMainNumericalData(availableZeroProperties);
        verify(mockInputDataManager, times(1)).checkPositiveNumericalData(nonZeroProperties);
    }

    @Test
    void prepareData() {
        List<String> expectedData = List.of("20.0", "10", "5", "0", "", "true", "false", "0");
        when(mockInputDataManager.prepareNullableString(any(), any())).thenReturn(List.of("20,0", "10", "5", "0", "", "true", "false", "0"));
        when(mockInputDataManager.replaceCommasWithDots(anyList())).thenReturn(List.of("20.0", "10", "5", "0", "", "true", "false", "0"));
        List<String> actualData = earService.prepareData(unPreparedData);
        assertEquals(expectedData, actualData);
    }
}