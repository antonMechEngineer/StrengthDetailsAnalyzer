package strengthDetailsAnalyzer.service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Ear;
import strengthDetailsAnalyzer.entity.enums.EarType;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static strengthDetailsAnalyzer.utils.response.ResponseStatus.SUCCESS;

@RunWith(SpringJUnit4ClassRunner.class)
public class EarServiceTest {

    @MockBean
    private InputDataManager mockInputDataManager;

    @MockBean
    private DocumentWriter mockDocumentWriter;

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final Ear expectedEar = new Ear(detail, 20.0, 10.0, 5.0, 0.0, true, EarType.SINGLE, false, 0.0);
    private final ArrayList<String> unPreparedData = new ArrayList<>(Arrays.asList("20,0", "10", "5", "0", null, "true", "false", null));
    private final ArrayList<String> preparedData = new ArrayList<>(Arrays.asList("20.0", "10", "5", "0", "", "true", "false", "0"));
    private EarService earService;

    @Before
    public void init(){
        earService = new EarService(mockDocumentWriter, mockInputDataManager);
    }

    @Test
    public void writeSpecifiedDetail() {
        when(mockDocumentWriter.writeEar(expectedEar)).thenReturn(new Response(SUCCESS));
        Response actualResponse = earService.writeSpecifiedDetail(detail, preparedData);
        verify(mockDocumentWriter, times(1)).writeEar(expectedEar);
        assertEquals(SUCCESS, actualResponse.getResponseStatus());
    }

    @Test
    public void build() {
        assertEquals(expectedEar, earService.build(detail, preparedData));
    }

    @Test
    public void checkData() {
        List<String> nonZeroProperties = List.of("20,0", "10", "5");
        List<String> availableZeroProperties = List.of("0");
        when(mockInputDataManager.checkPositiveNumericalData(nonZeroProperties)).thenReturn(new Response(SUCCESS));
        when(mockInputDataManager.checkMainNumericalData(availableZeroProperties)).thenReturn(new Response(SUCCESS));
        earService.checkData(unPreparedData);
        verify(mockInputDataManager, times(1)).checkMainNumericalData(availableZeroProperties);
        verify(mockInputDataManager, times(1)).checkPositiveNumericalData(nonZeroProperties);
    }

    @Test
    public void prepareData() {
        List<String> expectedData = List.of("20.0", "10", "5", "0", "", "true", "false", "0");
        when(mockInputDataManager.prepareNullableString(any(), any(), any())).thenReturn(List.of("20,0", "10", "5", "0", "", "true", "false", "0"));
        when(mockInputDataManager.replaceCommasWithDots(anyList())).thenReturn(List.of("20.0", "10", "5", "0", "", "true", "false", "0"));
        List<String> actualData = earService.prepareData(unPreparedData);
        assertEquals(expectedData, actualData);
    }
}