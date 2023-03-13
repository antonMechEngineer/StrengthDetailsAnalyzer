package strengthDetailsAnalyzer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.service.WeldService;
import strengthDetailsAnalyzer.utils.response.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Component
@FxmlView("weldScene.fxml")
public class WeldController extends DetailController {

    private final WeldService weldService;
    public static final Integer INDEX_A = 0;
    public static final Integer INDEX_Fx = 1;
    public static final Integer INDEX_Fy = 2;
    public static final Integer INDEX_Fz = 3;
    public static final Integer INDEX_lx = 4;
    public static final Integer INDEX_ly = 5;
    public static final Integer INDEX_lz = 6;
    public static final Integer INDEX_Ix = 7;
    public static final Integer INDEX_Iy = 8;
    public static final Integer INDEX_xMax = 9;
    public static final Integer INDEX_yMax = 10;
    public static final Integer INDEX_r = 11;
    public static final Integer INDEX_DETAIL_MATERIAL = 12;
    public static final Integer INDEX_SORT_DETAIL_MATERIAL = 13;
    public static final Integer INDEX_TYPE_WELD_JOINT = 14;
    public static final Integer INDEX_WELD_MAT_METHOD = 15;
    public static final Integer INDEX_WELD_TECHNOLOGY = 16;
    public static final Integer INDEX_WELD_NUMBER = 17;
    public static final Integer INDEX_WELD_STRESS_CONDITION = 18;
    public static final Integer INDEX_TYPE_CHECKING_WELD = 19;
    public static final Integer INDEX_POST_TEMPERED = 20;
    public static final Integer INDEX_PRE_TEMPERED = 21;
    public static final Integer INDEX_RANGE_THICKNESS_DETAIL = 22;

    @Autowired
    public WeldController(FxWeaver fxWeaver, WeldService weldService) {
        super(fxWeaver);
        this.weldService = weldService;
    }

    @FXML
    private TextField A;

    @FXML
    private TextField Fx;

    @FXML
    private TextField Fy;

    @FXML
    private TextField Fz;

    @FXML
    private TextField lx;

    @FXML
    private TextField ly;

    @FXML
    private TextField lz;

    @FXML
    private TextField Ix;

    @FXML
    private TextField Iy;

    @FXML
    private TextField xMax;

    @FXML
    private TextField yMax;

    @FXML
    private TextField r;

    @FXML
    private ComboBox<String> detailMaterial;

    @FXML
    private ComboBox<String> sortDetailMaterial;

    @FXML
    private Button printButton;

    @FXML
    private ComboBox<String> typeWeldJoint;

    @FXML
    private ComboBox<String> weldMatMethod;

    @FXML
    private ComboBox<String> weldTechnology;

    @Override
    protected ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add(INDEX_A, A.getText());
        data.add(INDEX_Fx, Fx.getText());
        data.add(INDEX_Fy, Fy.getText());
        data.add(INDEX_Fz, Fz.getText());
        data.add(INDEX_lx, lx.getText());
        data.add(INDEX_ly, ly.getText());
        data.add(INDEX_lz, lz.getText());
        data.add(INDEX_Ix, Ix.getText());
        data.add(INDEX_Iy, Iy.getText());
        data.add(INDEX_xMax, xMax.getText());
        data.add(INDEX_yMax, yMax.getText());
        data.add(INDEX_r, r.getText());
        data.add(INDEX_DETAIL_MATERIAL, detailMaterial.getValue());
        data.add(INDEX_SORT_DETAIL_MATERIAL, sortDetailMaterial.getValue());
        data.add(INDEX_TYPE_WELD_JOINT, typeWeldJoint.getValue());
        data.add(INDEX_WELD_MAT_METHOD, weldMatMethod.getValue());
        data.add(INDEX_WELD_TECHNOLOGY, weldTechnology.getValue());
        data.add(INDEX_WELD_NUMBER, weldNumber.getValue().toString());
        data.add(INDEX_WELD_STRESS_CONDITION, weldStressCondition.getValue());
        data.add(INDEX_TYPE_CHECKING_WELD, typeCheckingWeld.getValue());
        data.add(INDEX_POST_TEMPERED, postTempered.getValue());
        data.add(INDEX_PRE_TEMPERED, preTempered.getValue());
        data.add(INDEX_RANGE_THICKNESS_DETAIL, rangeThicknessDetail.getValue());
        return data;
    }

    @FXML
    private ComboBox<Integer> weldNumber;

    @FXML
    private ComboBox<String> weldStressCondition;

    @FXML
    private ComboBox<String> typeCheckingWeld;

    @FXML
    private ComboBox<String> postTempered;

    @FXML
    private ComboBox<String> preTempered;

    @FXML
    private ComboBox<String> rangeThicknessDetail;


    public void printDoc(ActionEvent event) throws IOException {
        ArrayList<String> data = getData();
        Response response = weldService.write(getDetailData(), getNumericalDataDetail(), data);
        processResponse(response, event);
    }

    @FXML
    private void getDetailMaterial() {
        sortDetailMaterial.setDisable(true);
        rangeThicknessDetail.setDisable(true);
        preTempered.setDisable(true);
        weldMatMethod.setDisable(true);
        postTempered.setDisable(true);
        printButton.setDisable(true);

        sortDetailMaterial.getItems().clear();
        rangeThicknessDetail.getItems().clear();
        preTempered.getItems().clear();
        weldMatMethod.getItems().clear();
        postTempered.getItems().clear();
        sortDetailMaterial.getItems().clear();

        if (detailMaterial.getItems().size() == 0) {
            detailMaterial.getItems().addAll(weldService.getMaterials());
        } else {
            detailMaterial.getItems();
        }
        if (sortDetailMaterial != null) {
            sortDetailMaterial.setDisable(false);
        }
        tryGetAccessToPrintDoc();
    }

    @FXML
    private void getSort() {
        preTempered.setDisable(true);
        weldMatMethod.setDisable(true);
        postTempered.setDisable(true);
        printButton.setDisable(true);
        rangeThicknessDetail.getItems().clear();
        preTempered.getItems().clear();
        weldMatMethod.getItems().clear();
        postTempered.getItems().clear();
        if (sortDetailMaterial.getItems().size() == 0) {
            sortDetailMaterial.getItems().addAll(weldService.getSorts(detailMaterial.getValue()));
        } else {
            sortDetailMaterial.getItems();
        }
        rangeThicknessDetail.setDisable(false);
    }

    @FXML
    private void getThickness() {
        preTempered.setDisable(true);
        weldMatMethod.setDisable(true);
        postTempered.setDisable(true);
        printButton.setDisable(true);
        preTempered.getItems().clear();
        weldMatMethod.getItems().clear();
        postTempered.getItems().clear();
        if (rangeThicknessDetail.getItems().isEmpty() && sortDetailMaterial.getValue() != null) {
            rangeThicknessDetail.getItems().addAll(weldService.getThickness(detailMaterial.getValue(), sortDetailMaterial.getValue()));
        } else {
            rangeThicknessDetail.getItems();
        }
        preTempered.setDisable(false);
    }

    @FXML
    private void getPreTempered() {
        weldMatMethod.setDisable(true);
        postTempered.setDisable(true);
        printButton.setDisable(true);
        weldMatMethod.getItems().clear();
        postTempered.getItems().clear();
        if (preTempered.getItems().isEmpty()) {
            preTempered.getItems().addAll(weldService.getPreTempered(detailMaterial.getValue(),
                    sortDetailMaterial.getValue(), rangeThicknessDetail.getValue()));
        } else {
            preTempered.getItems();
        }
        weldMatMethod.setDisable(false);
    }

    @FXML
    private void getWeldMatMethod() {
        postTempered.setDisable(true);
        postTempered.getItems().clear();
        if (weldMatMethod.getItems().isEmpty()) {
            weldMatMethod.getItems().addAll(weldService.getMatMethod(detailMaterial.getValue(),
                    sortDetailMaterial.getValue(), rangeThicknessDetail.getValue(), preTempered.getValue()));
        } else {
            weldMatMethod.getItems();
        }
        postTempered.setDisable(false);
        tryGetAccessToPrintDoc();
    }

    @FXML
    private void getPostTempered() {
        printButton.setDisable(true);
        if (postTempered.getItems().isEmpty()) {
            postTempered.getItems().addAll(weldService.getPostTempered(detailMaterial.getValue(),
                    sortDetailMaterial.getValue(), rangeThicknessDetail.getValue(), preTempered.getValue(), weldMatMethod.getValue()));
        } else {
            postTempered.getItems();
        }
    }


    @FXML
    private void getTypeWeldJoint() {
        weldNumber.getItems().clear();
        printButton.setDisable(true);
        weldNumber.setDisable(false);
        if (typeWeldJoint.getItems().size() == 0) {
            typeWeldJoint.getItems().addAll(weldService.getTypeWeldJoints());
        } else {
            typeWeldJoint.getItems();
        }
    }

    @FXML
    private void getNumberWeldJoint() {
        if (weldNumber.getItems().isEmpty()) {
            weldNumber.getItems().addAll(weldService.getWeldNumbers(typeWeldJoint.getValue()));
        } else {
            weldNumber.getItems();
        }
    }

    @FXML
    private void getWeldTechnology() {
        if (weldTechnology.getItems().isEmpty()) {
            weldTechnology.getItems().addAll(weldService.getWeldTechnologies());
        } else {
            weldTechnology.getItems();
        }
    }

    @FXML
    private void getCheckWeldTypes() {
        if (typeCheckingWeld.getItems().isEmpty()) {
            typeCheckingWeld.getItems().addAll(weldService.getTypesCheckingWeld());
        } else {
            typeCheckingWeld.getItems();
        }
        tryGetAccessToPrintDoc();
    }

    @FXML
    private void getStressConditions() {
        if (weldStressCondition.getItems().isEmpty()) {
            weldStressCondition.getItems().addAll(weldService.getStressConditions());
        } else {
            weldStressCondition.getItems();
        }
        tryGetAccessToPrintDoc();
    }

    private void tryGetAccessToPrintDoc() {
        if (detailMaterial.getValue() != null
                && sortDetailMaterial.getValue() != null
                && weldMatMethod.getValue() != null
                && typeWeldJoint.getValue() != null
                && weldTechnology.getValue() != null
                && weldNumber.getValue() != null
                && weldStressCondition.getValue() != null) {
            printButton.setDisable(false);
        }
    }
}


