package strengthDetailsAnalyzer.service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Pin;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static strengthDetailsAnalyzer.utils.response.ResponseStatus.SUCCESS;

@RunWith(SpringJUnit4ClassRunner.class)
public class PinServiceTest {

    @MockBean
    private InputDataManager mockInputDataManager;

    @MockBean
    private DocumentWriter mockDocumentWriter;

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final String outerDiameter = "10.2";
    private final String internalDiameter = "0";
    private final String numberShearSect = "2";
    private final List<String> data = List.of(outerDiameter, internalDiameter, numberShearSect);
    private final Pin expectedPin = new Pin
            (detail, Double.valueOf(outerDiameter), Double.valueOf(internalDiameter), Double.valueOf(numberShearSect));
    private PinService pinService;

    @Before
    public void init(){
        pinService = new PinService(mockInputDataManager, mockDocumentWriter);
    }

    @Test
    public void writeSpecifiedDetail() {
        when(mockDocumentWriter.writePin(expectedPin)).thenReturn(new Response(SUCCESS));
        Response actualRes = pinService.writeSpecifiedDetail(detail, data);
        verify(mockDocumentWriter, times(1)).writePin(expectedPin);
        assertEquals(SUCCESS, actualRes.getResponseStatus());
    }

    @Test
    public void build() {
        List<String> data = List.of("10.2", "0", "2");
        Pin actualPin = pinService.build(detail, data);
        assertEquals(expectedPin, actualPin);
    }

    @Test
    public void checkData() {
        when(mockInputDataManager.checkMainNumericalData(List.of("0"))).thenReturn(new Response(SUCCESS));
        when(mockInputDataManager.checkNonZeroNumericalData(List.of("10.2"))).thenReturn(new Response(SUCCESS));
        pinService.checkData(data);
        verify(mockInputDataManager, times(1)).checkMainNumericalData(List.of("0"));
        verify(mockInputDataManager, times(1)).checkNonZeroNumericalData(List.of("10.2"));
    }

    @Test
    public void prepareData() {
        pinService.prepareData(data);
        verify(mockInputDataManager, times(1)).replaceCommasWithDots(data);
    }
}