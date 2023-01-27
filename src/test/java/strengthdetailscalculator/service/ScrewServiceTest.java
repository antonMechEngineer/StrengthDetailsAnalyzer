package strengthdetailscalculator.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import strengthdetailscalculator.entity.Detail;
import strengthdetailscalculator.entity.Screw;
import strengthdetailscalculator.entity.enums.ScrewType;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.response.Response;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static strengthdetailscalculator.utils.response.ResponseStatus.SUCCESS;

class ScrewServiceTest {
    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d);
    private final String mainD = "10";
    private final String threadPitch = "1";
    private final String height = "7";
    private final ScrewType screwType = ScrewType.METRICAL;
    private final String midD = "9.35";
    private final String internalD = "8.917";
    private final String minD = "8.773";
    private final List<String> data = List.of(mainD, threadPitch, height, screwType.name);
    private final Screw expectedScrew = new Screw(detail, Double.valueOf(mainD), Double.valueOf(threadPitch),
            Double.valueOf(height), screwType, Double.valueOf(internalD), Double.valueOf(minD));
    private InputDataManager mockInputDataManager = Mockito.mock(InputDataManager.class);
    private DocumentWriter mockDocumentWriter = Mockito.mock(DocumentWriter.class);
    private final ScrewService screwService = new ScrewService(mockDocumentWriter, mockInputDataManager);

    @Test
    void writeSpecifiedDetail() {
        when(mockInputDataManager.checkInputThreadProperties(expectedScrew)).thenReturn(new Response(SUCCESS));
        Response actualRes = screwService.writeSpecifiedDetail(detail, data);
        verify(mockDocumentWriter, times(1)).writeScrew(expectedScrew);
        assertEquals(SUCCESS, actualRes.getResponseStatus());
    }

    @Test
    void checkData() {
        when(mockInputDataManager.checkPositiveNumericalData(List.of("10", "1", "7"))).thenReturn(new Response(SUCCESS));
        screwService.checkData(data);
        verify(mockInputDataManager, times(1)).checkPositiveNumericalData(List.of("10", "1", "7"));
    }

    @Test
    void prepareData() {
        screwService.prepareData(data);
        verify(mockInputDataManager, times(1)).replaceCommasWithDots(data);
    }
}