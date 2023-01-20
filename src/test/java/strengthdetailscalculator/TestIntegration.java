package strengthdetailscalculator;

import strengthdetailscalculator.utils.InputDataManager;
import strengthdetailscalculator.utils.response.ResponseStatus;

public class TestIntegration {

    private final InputDataManager inputDataManager = new InputDataManager();
    private ResponseStatus currentActualResponse;

    public void testCheckTextData() {
        String unCorrectString = "";
        String correctString = "abc";
//      TextField textField = new TextField();
//      textField.setText(correctString);
//      currentActualResponse = inputDataManager.checkTextData(List.of(textField)).getResponseStatus();
//      Platform.startup(() ->
//        {
//            TextField textField = new TextField();
//            textField.setText(correctString);
//            currentActualResponse = inputDataManager.checkTextData(List.of(textField)).getResponseStatus();
//        });
//        Platform.exit();
        //checkTextField(correctString);
//        assertEquals(SUCCESS, SUCCESS);
        //checkTextField(unCorrectString);
        //assertEquals(FAIL, currentActualResponse);
    }

//    public void testCheckNonZeroNumericalData() throws InterruptedException {
//        String unCorrectString = "";
//        Platform.startup(() ->
//        {
//            TextField textField = new TextField();
//            textField.setText(unCorrectString);
//            currentActualResponse = inputDataManager.checkTextData(List.of(textField)).getResponseStatus();
//        });
//        Platform.exit();
//
//        assertEquals(FAIL, currentActualResponse);

//
//    public void testCheckZNumericalData(){}
//
//    public void testCheckMainNumericalData(){}
//
//    public void testCheckPositiveNumericalData(){}
//
//    public void testCheckNaturalNumericalData(){}
//
//    public void testCheckNullValue(){
//
//    }
//
//    public void testCheckInputThreadProperties(){}
//
//    public void testCheckInputAxleProperties(){
//
//    }
//
//    public void testPrepareNumericalData(){
//
//    }

    private void checkTextField(String checkingText) {
    }


}



