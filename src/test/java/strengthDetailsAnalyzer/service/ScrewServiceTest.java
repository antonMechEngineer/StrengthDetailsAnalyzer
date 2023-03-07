package strengthDetailsAnalyzer.service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Screw;
import strengthDetailsAnalyzer.entity.enums.ScrewType;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static strengthDetailsAnalyzer.utils.response.ResponseStatus.SUCCESS;

@RunWith(SpringJUnit4ClassRunner.class)
public class ScrewServiceTest {

    @MockBean
    private InputDataManager mockInputDataManager;

    @MockBean
    private DocumentWriter mockDocumentWriter;

    private final Detail detail = new Detail("name", "code", "сталь", 240d, 1000d, 1.3);
    private final String mainD = "10";
    private final String threadPitch = "1";
    private final String height = "7";
    private final ScrewType screwType = ScrewType.METRICAL;
    private final String midD = "9.35";
    private final String internalD = "8.917";
    private final String minD = "8.773";
    private final String isSmallPitch = "true";
    private final List<String> data = List.of(mainD, height, screwType.description, isSmallPitch, threadPitch);
    private final Screw expectedScrew = new Screw(detail, Double.valueOf(mainD), Double.valueOf(threadPitch),
            Double.valueOf(height), screwType, Double.valueOf(internalD), Double.valueOf(minD));
    private ScrewService screwService;

    @Before
    public void init(){
        screwService = new ScrewService(mockDocumentWriter, mockInputDataManager);
    }

    @Test
    public void writeSpecifiedDetail() {
        when(mockDocumentWriter.writeScrew(expectedScrew)).thenReturn(new Response(SUCCESS));
        when(mockInputDataManager.checkInputThreadProperties(any())).thenReturn(new Response(SUCCESS));
        Response actualRes = screwService.writeSpecifiedDetail(detail, data);
        verify(mockDocumentWriter, times(1)).writeScrew(expectedScrew);
        assertEquals(SUCCESS, actualRes.getResponseStatus());
    }

    @Test
    public void checkData() {
        when(mockInputDataManager.checkPositiveNumericalData(List.of("10", "7", "1"))).thenReturn(new Response(SUCCESS));
        screwService.checkData(data);
        verify(mockInputDataManager, times(1)).checkPositiveNumericalData(List.of("10", "7", "1"));
    }

    @Test
    public void prepareData() {
        screwService.prepareData(data);
        verify(mockInputDataManager, times(1)).replaceCommasWithDots(data);
    }
}