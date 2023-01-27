package strengthdetailscalculator.controller;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import strengthdetailscalculator.utils.response.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DetailControllerTest {

    private final String nameStr = "name";
    private final String codeStr = "code";
    private final String materialStr = "material";
    private final String yieldStressStr = "240";
    private final String forceStr = "1000";

    private final String outerDStr = "20";
    private final String internalDStr = "0";
    private final String numberSectionStr = "1";

    private final String supportLengthStr = "50";

    private final String mainDStr = "10";
    private final String threadPitchStr = "1";
    private final String heightStr = "7";
    private final String typeScrewStr = "METRICAL";

    private ResponseStatus currentActualResponse;
    private final List<String> expectedDetailData = List.of(nameStr, codeStr, materialStr);
    private final List<String> expectedNumericalDetailData = List.of(yieldStressStr, forceStr);
    private final List<String> expectedPinData = List.of(outerDStr, internalDStr, numberSectionStr);
    private final List<String> expectedAxleData = List.of(outerDStr, internalDStr, numberSectionStr, supportLengthStr);
    private final List<String> expectedScrewData = List.of(mainDStr, threadPitchStr, heightStr, typeScrewStr);

    private ArrayList<String> actualDetailData = new ArrayList<>();
    private ArrayList<String> actualNumericalDetailData = new ArrayList<>();
    private ArrayList<String> actualPinData = new ArrayList<>();
    private ArrayList<String> actualAxleData = new ArrayList<>();
    private ArrayList<String> actualScrewData = new ArrayList<>();

    @Test
    public void printDoc() {
        Platform.startup(() ->
        {
            PinController pinController = buildPinController();
            actualPinData.addAll(pinController.getData());
            AxleController axleController = buildAxleController();
            actualAxleData.addAll(axleController.getData());
            ScrewController screwController = buildScrewController();
            actualScrewData.addAll(screwController.getData());
            actualNumericalDetailData.addAll(pinController.getNumericalDataDetail());
            actualDetailData.addAll(pinController.getDetailData());
        });
        Platform.exit();
        assertEquals(expectedDetailData, actualDetailData);
        assertEquals(expectedNumericalDetailData, actualNumericalDetailData);
        assertEquals(expectedPinData, actualPinData);
        assertEquals(expectedAxleData, actualAxleData);
        assertEquals(expectedScrewData, actualScrewData);
    }

    private PinController buildPinController() {
        PinController pinController = new PinController();
        TextField name = new TextField(nameStr);
        TextField code = new TextField(codeStr);
        TextField material = new TextField(materialStr);
        TextField yieldStress = new TextField(yieldStressStr);
        TextField force = new TextField(forceStr);
        TextField outerDiameter = new TextField(outerDStr);
        TextField internalDiameter = new TextField(internalDStr);
        ComboBox<String> numberSection = new ComboBox();
        numberSection.setValue(numberSectionStr);
        pinController.setName(name);
        pinController.setCode(code);
        pinController.setMaterial(material);
        pinController.setYieldStress(yieldStress);
        pinController.setForce(force);
        pinController.setOuterDiameter(outerDiameter);
        pinController.setInternalDiameter(internalDiameter);
        pinController.setNumberShearSection(numberSection);
        return pinController;
    }

    private AxleController buildAxleController(){
        AxleController axleController = new AxleController();
        TextField name = new TextField(nameStr);
        TextField code = new TextField(codeStr);
        TextField material = new TextField(materialStr);
        TextField yieldStress = new TextField(yieldStressStr);
        TextField force = new TextField(forceStr);
        TextField outerDiameter = new TextField(outerDStr);
        TextField internalDiameter = new TextField(internalDStr);
        TextField supportLength = new TextField(supportLengthStr);
        ComboBox<String> numberSection = new ComboBox();
        numberSection.setValue(numberSectionStr);
        axleController.setName(name);
        axleController.setCode(code);
        axleController.setMaterial(material);
        axleController.setYieldStress(yieldStress);
        axleController.setForce(force);
        axleController.setOuterDiameter(outerDiameter);
        axleController.setInternalDiameter(internalDiameter);
        axleController.setNumberShearSection(numberSection);
        axleController.setSupportLength(supportLength);
        return axleController;
    }

    private ScrewController buildScrewController(){
        ScrewController screwController = new ScrewController();
        TextField name = new TextField(nameStr);
        TextField code = new TextField(codeStr);
        TextField material = new TextField(materialStr);
        TextField yieldStress = new TextField(yieldStressStr);
        TextField force = new TextField(forceStr);
        TextField mainD = new TextField(mainDStr);
        TextField threadPitch = new TextField(threadPitchStr);
        TextField height = new TextField(heightStr);
        ComboBox<String> typeScrew = new ComboBox();
        typeScrew.setValue(typeScrewStr);
        screwController.setName(name);
        screwController.setCode(code);
        screwController.setMaterial(material);
        screwController.setYieldStress(yieldStress);
        screwController.setForce(force);
        screwController.setMainD(mainD);
        screwController.setThreadPitch(threadPitch);
        screwController.setHeight(height);
        screwController.setScrewType(typeScrew);
        return screwController;
    }


}

