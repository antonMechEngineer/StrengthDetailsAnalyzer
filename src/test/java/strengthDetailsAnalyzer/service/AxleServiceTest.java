package strengthDetailsAnalyzer.service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import strengthDetailsAnalyzer.entity.Axle;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Pin;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static strengthDetailsAnalyzer.utils.response.ResponseStatus.SUCCESS;

@RunWith(SpringJUnit4ClassRunner.class)
public class AxleServiceTest {

    @MockBean
    private InputDataManager mockInputDataManager;

    @MockBean
    private DocumentWriter mockDocumentWriter;

    AxleService axleService;

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final String outerD = "10.2";
    private final String internalD = "0";
    private final String numberShearSect = "2";
    private final String supportLength = "50";
    private final ArrayList<String> data = new ArrayList<>(Arrays.asList(outerD, internalD, numberShearSect, supportLength));
    private final Pin expectedPin = new Pin
            (detail, Double.valueOf(outerD), Double.valueOf(internalD), Double.valueOf(numberShearSect));
    private final Axle expectedAxle = new Axle(expectedPin, Double.valueOf(supportLength));

    @Before
    public void init(){
        axleService = new AxleService(mockInputDataManager, mockDocumentWriter);
    }

    @Test
    public void writeSpecifiedDetail() {
        when(mockInputDataManager.checkNonZeroNumericalData(List.of("50.0"))).thenReturn(new Response(SUCCESS));
        when(mockDocumentWriter.writeAxle(expectedAxle)).thenReturn(new Response(SUCCESS));
        Response actualRes = axleService.writeSpecifiedDetail(detail, data);
        verify(mockInputDataManager, times(1)).checkNonZeroNumericalData(List.of("50.0"));
        verify(mockDocumentWriter, times(1)).writeAxle(expectedAxle);
        assertEquals(SUCCESS, actualRes.getResponseStatus());
    }

    @Test
    public void build() {
        List<String> data = List.of("10.2", "0", "2", "50");
        Axle actualAxle = axleService.build(detail, data);
        assertEquals(expectedAxle, actualAxle);
    }

    @Test
    public void checkData() {
        when(mockInputDataManager.checkMainNumericalData(List.of("0"))).thenReturn(new Response(SUCCESS));
        when(mockInputDataManager.checkNonZeroNumericalData(List.of("10.2"))).thenReturn(new Response(SUCCESS));
        when(mockInputDataManager.checkPositiveNumericalData(List.of("50"))).thenReturn(new Response(SUCCESS));
        axleService.checkData(data);
        verify(mockInputDataManager, times(1)).checkMainNumericalData(List.of("0"));
        verify(mockInputDataManager, times(1)).checkNonZeroNumericalData(List.of("10.2"));
        verify(mockInputDataManager, times(1)).checkPositiveNumericalData(List.of("50"));
    }
}