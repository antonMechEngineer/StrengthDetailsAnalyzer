package strengthDetailsAnalyzer.controller;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxWeaver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import strengthDetailsAnalyzer.service.AxleService;
import strengthDetailsAnalyzer.service.EarService;
import strengthDetailsAnalyzer.service.PinService;
import strengthDetailsAnalyzer.service.ScrewService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DetailControllerTest {

    @Autowired
    private FxWeaver fxWeaver;

    @Autowired
    private PinService pinService;

    @Autowired
    private AxleService axleService;

    @Autowired
    private EarService earService;

    @Autowired
    private ScrewService screwService;

    private final String name = "name";
    private final String code = "code";
    private final String material = "material";
    private final String yieldStress = "240";
    private final String force = "1000";
    private final String isUserSafetyFactor = "true";
    private final String userSafetyFactor = "1,30";

    private final String pinOuterD = "20";
    private final String pinInternalD = "0";
    private final String pinNumberShearSection = "1";

    private final String axleSupportLength = "50";

    private final String screwMainD = "10";
    private final String screwThreadPitch = "1";
    private final String screwHeightStr = "7";
    private final String screwTypeScrew = "METRICAL";
    private final String isSmallThreadPitch = "true";

    private final String earOuterD = "20";
    private final String earInternalD = "10";
    private final String earThickness = "5";
    private final String earEccentricity = "0";
    private final String earIsSingleConnection = "true";
    private final String earCurrentEarIsLarger = "true";
    private final String earType = "SINGLE";
    private final String earGap = "1.25";

    private final List<String> expectedDetailData = List.of(name, code, material, isUserSafetyFactor);
    private final List<String> expectedNumericalDetailData = List.of(yieldStress, force, userSafetyFactor);
    private final List<String> expectedPinData = List.of(pinOuterD, pinInternalD, pinNumberShearSection);
    private final List<String> expectedAxleData = List.of(pinOuterD, pinInternalD, pinNumberShearSection, axleSupportLength);
    private final List<String> expectedScrewData = List.of(screwMainD, screwHeightStr, screwTypeScrew, isSmallThreadPitch, screwThreadPitch);
    private final List<String> expectedEarData = List.of(earOuterD, earInternalD, earThickness, earEccentricity,earType, earIsSingleConnection, earCurrentEarIsLarger,  earGap);

    @Test
    public void printDoc() {
        ArrayList<String> actualDetailData = new ArrayList<>();
        ArrayList<String> actualNumericalDetailData = new ArrayList<>();
        ArrayList<String> actualPinData = new ArrayList<>();
        ArrayList<String> actualAxleData = new ArrayList<>();
        ArrayList<String> actualScrewData = new ArrayList<>();
        ArrayList<String> actualEarData = new ArrayList<>();
        Platform.startup(() ->
        {
            PinController pinController = buildPinController();
            actualPinData.addAll(pinController.getData());

            actualNumericalDetailData.addAll(pinController.getNumericalDataDetail());
            actualDetailData.addAll(pinController.getDetailData());

            AxleController axleController = buildAxleController();
            actualAxleData.addAll(axleController.getData());

            ScrewController screwController = buildScrewController();
            actualScrewData.addAll(screwController.getData());

            EarController earController = buildEarController();
            actualEarData.addAll(earController.getData());
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
        PinController pinController = new PinController(pinService, fxWeaver);
        TextField name = new TextField(this.name);
        TextField code = new TextField(this.code);
        TextField material = new TextField(this.material);
        TextField yieldStress = new TextField(this.yieldStress);
        TextField force = new TextField(this.force);
        CheckBox isUserSafetyFactor = new CheckBox();
        isUserSafetyFactor.setSelected(Boolean.parseBoolean(String.valueOf(this.isUserSafetyFactor)));
        TextField userSafetyFactor = new TextField(this.userSafetyFactor);
        TextField outerDiameter = new TextField(pinOuterD);
        TextField internalDiameter = new TextField(pinInternalD);
        ComboBox<String> numberSection = new ComboBox<>();
        numberSection.setValue(pinNumberShearSection);

        pinController.setName(name);
        pinController.setCode(code);
        pinController.setMaterial(material);
        pinController.setYieldStress(yieldStress);
        pinController.setForce(force);
        pinController.setIsUsersSafetyFactor(isUserSafetyFactor);
        pinController.setUsersSafetyFactor(userSafetyFactor);
        pinController.setOuterDiameter(outerDiameter);
        pinController.setInternalDiameter(internalDiameter);
        pinController.setNumberShearSection(numberSection);
        return pinController;
    }

    private AxleController buildAxleController() {
        AxleController axleController = new AxleController(fxWeaver, axleService);
        TextField name = new TextField(this.name);
        TextField code = new TextField(this.code);
        TextField material = new TextField(this.material);
        TextField yieldStress = new TextField(this.yieldStress);
        TextField force = new TextField(this.force);
        CheckBox isUserSafetyFactor = new CheckBox();
        isUserSafetyFactor.setSelected(Boolean.parseBoolean(String.valueOf(this.isUserSafetyFactor)));
        TextField userSafetyFactor = new TextField(this.userSafetyFactor);
        TextField outerDiameter = new TextField(pinOuterD);
        TextField internalDiameter = new TextField(pinInternalD);
        TextField supportLength = new TextField(axleSupportLength);
        ComboBox<String> numberSection = new ComboBox<>();
        numberSection.setValue(pinNumberShearSection);

        axleController.setName(name);
        axleController.setCode(code);
        axleController.setMaterial(material);
        axleController.setYieldStress(yieldStress);
        axleController.setForce(force);
        axleController.setIsUsersSafetyFactor(isUserSafetyFactor);
        axleController.setUsersSafetyFactor(userSafetyFactor);
        axleController.setOuterDiameter(outerDiameter);
        axleController.setInternalDiameter(internalDiameter);
        axleController.setNumberShearSection(numberSection);
        axleController.setSupportLength(supportLength);
        return axleController;
    }

    private ScrewController buildScrewController() {
        ScrewController screwController = new ScrewController(screwService, fxWeaver);
        TextField name = new TextField(this.name);
        TextField code = new TextField(this.code);
        TextField material = new TextField(this.material);
        TextField yieldStress = new TextField(this.yieldStress);
        TextField force = new TextField(this.force);
        CheckBox isUserSafetyFactor = new CheckBox();
        isUserSafetyFactor.setSelected(Boolean.parseBoolean(String.valueOf(isUserSafetyFactor)));
        TextField userSafetyFactor = new TextField(this.userSafetyFactor);
        TextField mainD = new TextField(screwMainD);
        TextField threadPitch = new TextField(screwThreadPitch);
        TextField height = new TextField(screwHeightStr);
        CheckBox isSmallThreadPitch = new CheckBox();
        ComboBox<String> typeScrew = new ComboBox<>();
        typeScrew.setValue(screwTypeScrew);
        isSmallThreadPitch.setSelected(true);

        screwController.setName(name);
        screwController.setCode(code);
        screwController.setMaterial(material);
        screwController.setYieldStress(yieldStress);
        screwController.setForce(force);
        screwController.setIsUsersSafetyFactor(isUserSafetyFactor);
        screwController.setUsersSafetyFactor(userSafetyFactor);
        screwController.setMainD(mainD);
        screwController.setThreadPitch(threadPitch);
        screwController.setHeight(height);
        screwController.setScrewType(typeScrew);
        screwController.setIsSmallScrewPitch(isSmallThreadPitch);
        return screwController;
    }

    private EarController buildEarController() {
        EarController earController = new EarController(earService, fxWeaver);
        TextField name = new TextField(this.name);
        TextField code = new TextField(this.code);
        TextField material = new TextField(this.material);
        TextField yieldStress = new TextField(this.yieldStress);
        TextField force = new TextField(this.force);
        CheckBox isUserSafetyFactor = new CheckBox();
        isUserSafetyFactor.setSelected(Boolean.parseBoolean(String.valueOf(isUserSafetyFactor)));
        TextField userSafetyFactor = new TextField(this.userSafetyFactor);
        TextField outerD = new TextField(this.earOuterD);
        TextField internalD = new TextField(this.earInternalD);
        TextField thickness = new TextField(this.earThickness);
        TextField eccentricity = new TextField(this.earEccentricity);
        CheckBox isSingleConnection = new CheckBox();
        isSingleConnection.setSelected(Boolean.parseBoolean(this.earIsSingleConnection));
        CheckBox currentEarIsLarger = new CheckBox();
        currentEarIsLarger.setSelected(Boolean.parseBoolean(this.earCurrentEarIsLarger));
        TextField gap = new TextField(this.earGap);
        ComboBox<String> earType = new ComboBox<>();
        earType.setValue(this.earType);

        earController.setName(name);
        earController.setCode(code);
        earController.setMaterial(material);
        earController.setYieldStress(yieldStress);
        earController.setForce(force);
        earController.setIsUsersSafetyFactor(isUserSafetyFactor);
        earController.setUsersSafetyFactor(userSafetyFactor);
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