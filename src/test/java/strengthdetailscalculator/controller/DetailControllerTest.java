package strengthdetailscalculator.controller;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DetailControllerTest {

    private final String name = "name";
    private final String code = "code";
    private final String material = "material";
    private final String yieldStress = "240";
    private final String force = "1000";

    private final String pinOuterD = "20";
    private final String pinInternalD = "0";
    private final String pinNumberShearSection = "1";

    private final String axleSupportLength = "50";

    private final String screwMainD = "10";
    private final String screwThreadPitch = "1";
    private final String screwHeightStr = "7";
    private final String screwTypeScrew = "METRICAL";

    private final String earOuterD = "20";
    private final String earInternalD = "10";
    private final String earThickness = "5";
    private final String earEccentricity = "0";
    private final String earIsSingleConnection = "true";
    private final String earCurrentEarIsLarger = "true";
    private final String earType = "SINGLE";
    private final String earGap = "1.25";


    private final List<String> expectedDetailData = List.of(name, code, material);
    private final List<String> expectedNumericalDetailData = List.of(yieldStress, force);
    private final List<String> expectedPinData = List.of(pinOuterD, pinInternalD, pinNumberShearSection);
    private final List<String> expectedAxleData = List.of(pinOuterD, pinInternalD, pinNumberShearSection, axleSupportLength);
    private final List<String> expectedScrewData = List.of(screwMainD, screwThreadPitch, screwHeightStr, screwTypeScrew);
    private final List<String> expectedEarData = List.of(earOuterD, earInternalD, earThickness, earEccentricity,earType, earIsSingleConnection, earCurrentEarIsLarger,  earGap);

    private ArrayList<String> actualDetailData = new ArrayList<>();
    private ArrayList<String> actualNumericalDetailData = new ArrayList<>();
    private ArrayList<String> actualPinData = new ArrayList<>();
    private ArrayList<String> actualAxleData = new ArrayList<>();
    private ArrayList<String> actualScrewData = new ArrayList<>();
    private ArrayList<String> actualEarData = new ArrayList<>();

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
            EarController earController = buildEarController();
            actualEarData.addAll(earController.getData());
            actualNumericalDetailData.addAll(pinController.getNumericalDataDetail());
            actualDetailData.addAll(pinController.getDetailData());
        });
        Platform.exit();
        assertEquals(expectedDetailData, actualDetailData);
        assertEquals(expectedNumericalDetailData, actualNumericalDetailData);
        assertEquals(expectedPinData, actualPinData);
        assertEquals(expectedAxleData, actualAxleData);
        assertEquals(expectedScrewData, actualScrewData);
        assertEquals(expectedEarData, actualEarData);
    }

    private PinController buildPinController() {
        PinController pinController = new PinController();
        TextField name = new TextField(this.name);
        TextField code = new TextField(this.code);
        TextField material = new TextField(this.material);
        TextField yieldStress = new TextField(this.yieldStress);
        TextField force = new TextField(this.force);
        TextField outerDiameter = new TextField(pinOuterD);
        TextField internalDiameter = new TextField(pinInternalD);
        ComboBox<String> numberSection = new ComboBox();
        numberSection.setValue(pinNumberShearSection);
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

    private AxleController buildAxleController() {
        AxleController axleController = new AxleController();
        TextField name = new TextField(this.name);
        TextField code = new TextField(this.code);
        TextField material = new TextField(this.material);
        TextField yieldStress = new TextField(this.yieldStress);
        TextField force = new TextField(this.force);
        TextField outerDiameter = new TextField(pinOuterD);
        TextField internalDiameter = new TextField(pinInternalD);
        TextField supportLength = new TextField(axleSupportLength);
        ComboBox<String> numberSection = new ComboBox();
        numberSection.setValue(pinNumberShearSection);
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

    private ScrewController buildScrewController() {
        ScrewController screwController = new ScrewController();
        TextField name = new TextField(this.name);
        TextField code = new TextField(this.code);
        TextField material = new TextField(this.material);
        TextField yieldStress = new TextField(this.yieldStress);
        TextField force = new TextField(this.force);
        TextField mainD = new TextField(screwMainD);
        TextField threadPitch = new TextField(screwThreadPitch);
        TextField height = new TextField(screwHeightStr);
        ComboBox<String> typeScrew = new ComboBox();
        typeScrew.setValue(screwTypeScrew);
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

    private EarController buildEarController() {
        EarController earController = new EarController();
        TextField name = new TextField(this.name);
        TextField code = new TextField(this.code);
        TextField material = new TextField(this.material);
        TextField yieldStress = new TextField(this.yieldStress);
        TextField force = new TextField(this.force);
        TextField outerD = new TextField(this.earOuterD);
        TextField internalD = new TextField(this.earInternalD);
        TextField thickness = new TextField(this.earThickness);
        TextField eccentricity = new TextField(this.earEccentricity);
        CheckBox isSingleConnection = new CheckBox();
        isSingleConnection.setSelected(Boolean.valueOf(this.earIsSingleConnection));
        CheckBox currentEarIsLarger = new CheckBox();
        currentEarIsLarger.setSelected(Boolean.valueOf(this.earCurrentEarIsLarger));
        TextField gap = new TextField(this.earGap);
        ComboBox<String> earType = new ComboBox<>();
        earType.setValue(this.earType);
        earController.setName(name);
        earController.setCode(code);
        earController.setMaterial(material);
        earController.setYieldStress(yieldStress);
        earController.setForce(force);
        earController.setOuterDiameter(outerD);
        earController.setInternalDiameter(internalD);
        earController.setThickness(thickness);
        earController.setEccentricity(eccentricity);
        earController.setIsSingleShearedConnection(isSingleConnection);
        earController.setCurrentEarIsLarger(currentEarIsLarger);
        earController.setGap(gap);
        earController.setEarType(earType);
        return earController;
    }

}

