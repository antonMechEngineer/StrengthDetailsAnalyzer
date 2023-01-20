package strengthdetailscalculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import strengthdetailscalculator.entity.DetailTest;
import strengthdetailscalculator.utils.DocumentWriter;
import strengthdetailscalculator.utils.InputDataManager;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class PinServiceTest extends DetailTest {

    private PinService pinService = new PinService();

    List<String> unCorrectData = List.of("0", "0", "2");
    private final List<String> correctData = List.of("10,2", "0", "2");

    @Mock
    InputDataManager mockInputDataManager;

    @Mock
    DocumentWriter mockDocumentWriter;

    @BeforeEach
    public void setUp(){
        openMocks(this);

    }

    @Test
    void writeSpecifiedDetail() {


    }

    @Test
    void build() {
    }

    @Test
    void checkData() {
    }

    @Test
    void prepareData() {
        List<String> expectedData = List.of("10.2", "0", "2");
        List<String> correctingData1 = List.of("10,2", "0", "2");
        List<String> correctingData2 = List.of("10,2", "0", "2");
        when(mockInputDataManager.replaceCommasWithDots(correctData)).thenReturn(expectedData);

        assertEquals(expectedData, pinService.prepareData(correctingData1));
        verify(mockInputDataManager, times(1)).replaceCommasWithDots(correctingData2);
    }
}