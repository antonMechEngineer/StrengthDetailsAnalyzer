package strengthDetailsAnalyzer.utils.matcher;

import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Weld;
import strengthDetailsAnalyzer.entity.enums.StrengthCondition;

import java.util.HashMap;

@Component
public class WeldMatcher extends DetailMatcher {
    public static final String TYPE_WELD_JOINT = "${typeWeldJoint}";
    public static final String WELD_NUMBER = "${weldNumber}";
    public static final String WELD_MAT_METHOD = "${weldMatMethod}";
    public static final String WELD_TECHNOLOGY = "${weldTechnology}";
    public static final String CHECKING_TYPE = "${checkingType}";
    public static final String WELD_STRESS_CONDITION = "${weldStressCondition}";
    public static final String DETAIL_MATERIAL = "${detailMaterial}";
    public static final String DETAIL_SORT_MATERIAL = "${detailSortMaterial}";
    public static final String POST_TEMPERED = "${postTempered}";
    public static final String PRE_TEMPERED = "${preTempered}";
    public static final String RANGE_THICKNESS_DETAIL = "${rangeThicknessDetail}";
    public static final String K1 = "${K1}";
    public static final String K2 = "${K2}";
    public static final String BETA = "${beta}";
    public static final String FX = "${Fx}";
    public static final String FY = "${Fy}";
    public static final String FZ = "${Fz}";
    public static final String LX = "${lx}";
    public static final String LY = "${ly}";
    public static final String LZ = "${lz}";
    public static final String MX = "${Mx}";
    public static final String MY = "${My}";
    public static final String MZ = "${Mz}";
    public static final String IX = "${Ix}";
    public static final String IY = "${Iy}";
    public static final String X_MAX = "${xMax}";
    public static final String Y_MAX = "${yMax}";
    public static final String WX = "${Wx}";
    public static final String WY = "${Wy}";
    public static final String R = "${r}";
    public static final String WK = "${Wk}";
    public static final String KSV = "${Ksv}";
    public static final String A = "${A}";
    public static final String SIGMA_X = "${sigmaX}";
    public static final String SIGMA_Y = "${sigmaY}";
    public static final String SIGMA_Z = "${sigmaZ}";
    public static final String SIGMA = "${sigma}";

    public static final String SAFETY_FACTOR = "${safetyFactor}";
    public static final String SIGN = "${sign}";
    public static final String CONCLUSION = "${conclusion}";

    public HashMap<String, String> getMapTemplateWeld(Weld weld) {
        HashMap<String, String> mapTemplate = getMapTemplateDetail(weld);
        insertWeldProperties(mapTemplate, weld);
        insertConclusion(mapTemplate, weld);
        return mapTemplate;
    }

    private void insertWeldProperties(HashMap<String, String> mapTemplate, Weld weld) {
        mapTemplate.put(TYPE_WELD_JOINT, weld.getTypeWeldJoint());
        mapTemplate.put(WELD_NUMBER, weld.getWeldNumber());
        mapTemplate.put(WELD_MAT_METHOD, weld.getWeldMatMethod());
        mapTemplate.put(WELD_TECHNOLOGY, weld.getWeldTechnology());
        mapTemplate.put(CHECKING_TYPE, weld.getCheckingType());
        mapTemplate.put(WELD_STRESS_CONDITION, weld.getWeldStressCondition());
        mapTemplate.put(DETAIL_MATERIAL, weld.getDetailMaterial());
        mapTemplate.put(DETAIL_SORT_MATERIAL, weld.getDetailSortMaterial());
        mapTemplate.put(POST_TEMPERED, weld.getPostTempered());
        mapTemplate.put(PRE_TEMPERED, weld.getPreTempered());
        mapTemplate.put(RANGE_THICKNESS_DETAIL, weld.getRangeThicknessDetail());
        mapTemplate.put(K1, String.format("%,.2f", weld.getK1()));
        mapTemplate.put(K2, String.format("%,.2f", weld.getK2()));
        mapTemplate.put(BETA, String.format("%,.2f", weld.getBeta()));
        mapTemplate.put(FX, String.format("%,.0f", weld.getFx()));
        mapTemplate.put(FY, String.format("%,.0f", weld.getFy()));
        mapTemplate.put(FZ, String.format("%,.0f", weld.getFz()));
        mapTemplate.put(LX, String.format("%,.0f", weld.getLx()));
        mapTemplate.put(LY, String.format("%,.0f", weld.getLy()));
        mapTemplate.put(LZ, String.format("%,.0f", weld.getLz()));
        mapTemplate.put(MX, String.format("%,.0f", weld.getMx()));
        mapTemplate.put(MY, String.format("%,.0f", weld.getMy()));
        mapTemplate.put(MZ, String.format("%,.0f", weld.getMz()));
        mapTemplate.put(IX, String.format("%,.0f", weld.getIx()));
        mapTemplate.put(IY, String.format("%,.0f", weld.getIy()));
        mapTemplate.put(X_MAX, String.format("%,.0f", weld.getXMax()));
        mapTemplate.put(Y_MAX, String.format("%,.0f", weld.getYMax()));
        mapTemplate.put(WX, String.format("%,.0f", weld.getWx()));
        mapTemplate.put(WY, String.format("%,.0f", weld.getWy()));
        mapTemplate.put(R, String.format("%,.0f", weld.getR()));
        mapTemplate.put(WK, String.format("%,.0f", weld.getWk()));
        mapTemplate.put(KSV, String.format("%,.2f", weld.getKsv()));
        mapTemplate.put(A, String.format("%,.0f", weld.getA()));
        mapTemplate.put(SIGMA_X, String.format("%,.0f", weld.getSigmaX()));
        mapTemplate.put(SIGMA_Y, String.format("%,.0f", weld.getSigmaY()));
        mapTemplate.put(SIGMA_Z, String.format("%,.0f", weld.getSigmaZ()));
        mapTemplate.put(SIGMA, String.format("%,.0f", weld.getSigma()));
        mapTemplate.put(SAFETY_FACTOR, String.format("%, .0f", weld.getSafetyFactor()));
    }

    private void insertConclusion(HashMap<String, String> mapTemplate, Weld weld) {
        if (weld.getSafetyFactor() > weld.getMinSafetyFactor()) {
            mapTemplate.put(SIGN, StrengthCondition.SAFETY.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.SAFETY.conclusion);
        } else {
            mapTemplate.put(SIGN, StrengthCondition.FAIL.sign);
            mapTemplate.put(CONCLUSION, StrengthCondition.FAIL.conclusion);
        }
    }
}

