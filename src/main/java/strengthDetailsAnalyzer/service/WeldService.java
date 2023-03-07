package strengthDetailsAnalyzer.service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import strengthDetailsAnalyzer.entity.Detail;
import strengthDetailsAnalyzer.entity.Weld;
import strengthDetailsAnalyzer.service.interfaces.Readable;
import strengthDetailsAnalyzer.utils.DocumentWriter;
import strengthDetailsAnalyzer.utils.InputDataManager;
import strengthDetailsAnalyzer.utils.response.Response;
import java.util.*;
import java.util.stream.Collectors;
import static strengthDetailsAnalyzer.controller.WeldController.*;

@Component
public class WeldService extends DetailService implements Readable {

    private final DocumentWriter documentWriter;

    private static final String PATH_WELD_TYPE_JOINT = "src/main/resources/data/weld/K2/jointType.json";
    private static final String PATH_WELD_TECHNOLOGY = "src/main/resources/data/weld/K2/weldTechnology.json";
    private static final String PATH_CHECK_TYPE = "src/main/resources/data/weld/K2/checkType.json";
    private static final String PATH_STRESS_CONDITION = "src/main/resources/data/weld/K2/stressCondition.json";
    private static final String PATH_K1 = "src/main/resources/data/weld/K1.json";
    private static final String PATH_K2 = "src/main/resources/data/weld/K2/K2.json";

    private final JSONObject K1JSON = new JSONObject(read(PATH_K1));
    private final JSONObject K2JSON = new JSONObject(read(PATH_K2));
    private final JSONObject weldTypeJointsJSON = new JSONObject(read(PATH_WELD_TYPE_JOINT));
    private final JSONObject weldTechnologiesJSON = new JSONObject(read(PATH_WELD_TECHNOLOGY));
    private final JSONArray weldCheckTypesJSON = new JSONArray(read(PATH_CHECK_TYPE));
    private final JSONArray stressConditionsJSON = new JSONArray(read(PATH_STRESS_CONDITION));

    public WeldService(DocumentWriter documentWriter, InputDataManager inputDataManager){
        super(inputDataManager);
        this.documentWriter = documentWriter;
    }

    @Override
    protected Response writeSpecifiedDetail(Detail detail, List<String> data) {
        Weld weld = build(detail, data);
        return documentWriter.writeWeld(weld);
    }

    @Override
    protected Response checkData(List<String> data) {
        List<String> numericalData = List.of(data.get(INDEX_A), data.get(INDEX_Fx), data.get(INDEX_Fy), data.get(INDEX_Fz),
                data.get(INDEX_lx), data.get(INDEX_ly), data.get(INDEX_lz), data.get(INDEX_Ix), data.get(INDEX_Iy),
                data.get(INDEX_xMax), data.get(INDEX_yMax), data.get(INDEX_r));
        Response response = inputDataManager.checkPositiveNumericalData(numericalData);
        return response;
    }

    @Override
    protected List<String> prepareData(List<String> data) {
        List<String> replacedDotsData = inputDataManager.replaceCommasWithDots(data);
        ArrayList<String> replacedNullData = inputDataManager.prepareNullableString(replacedDotsData, "0");
        return replacedNullData;
    }

    private Weld build(Detail detail, List<String> data){
        Double A = Double.valueOf(data.get(INDEX_A));
        Double Fx = Double.valueOf(data.get(INDEX_Fx));
        Double Fy = Double.valueOf(data.get(INDEX_Fy));
        Double Fz = Double.valueOf(data.get(INDEX_Fz));
        Double lx = Double.valueOf(data.get(INDEX_lx));
        Double ly = Double.valueOf(data.get(INDEX_ly));
        Double lz = Double.valueOf(data.get(INDEX_lz));
        Double Ix = Double.valueOf(data.get(INDEX_Ix));
        Double Iy = Double.valueOf(data.get(INDEX_Iy));
        Double xMax = Double.valueOf(data.get(INDEX_xMax));
        Double yMax = Double.valueOf(data.get(INDEX_yMax));
        Double r = Double.valueOf(data.get(INDEX_r));
        String detailMaterial = data.get(INDEX_DETAIL_MATERIAL);
        String detailSortMaterial = data.get(INDEX_SORT_DETAIL_MATERIAL);
        String detailWeldMatMethod = data.get(INDEX_WELD_MAT_METHOD);
        String typeWeldJoint = data.get(INDEX_TYPE_WELD_JOINT);
        String weldNumber = data.get(INDEX_WELD_NUMBER);
        String weldTechnology = data.get(INDEX_WELD_TECHNOLOGY);
        String weldStressCondition = data.get(INDEX_WELD_STRESS_CONDITION);
        String checkType = data.get(INDEX_TYPE_CHECKING_WELD);
        String postTempered = data.get(INDEX_POST_TEMPERED);
        String preTempered = data.get(INDEX_PRE_TEMPERED);
        String rangeDetailThickness = data.get(INDEX_RANGE_THICKNESS_DETAIL);
        Double beta = getBeta(weldTechnology);
        Double K1 = getK1(detailMaterial, detailSortMaterial, rangeDetailThickness, preTempered, detailWeldMatMethod, postTempered);
        Double K2 = getK2(typeWeldJoint, weldNumber, weldTechnology, weldStressCondition, detailMaterial, checkType);
        Weld weld = new Weld(detail, A, Fx, Fy, Fz, lx, ly, lz, Ix, Iy, xMax, yMax, r, detailMaterial, detailSortMaterial,
                detailWeldMatMethod, typeWeldJoint, weldNumber, weldTechnology, weldStressCondition, checkType, postTempered,
                preTempered, rangeDetailThickness, beta, K1, K2);
        return weld;
    }


    public Double getK1(String material, String sort, String thickness, String preTempered, String matMethod, String postTempered) {
        return K1JSON.getJSONObject(material).getJSONObject(sort).getJSONObject(thickness).
                getJSONObject(preTempered).getJSONObject(matMethod).getDouble(postTempered);
    }

    public Double getK2(String weldType, String weldNum, String weldTechnology, String stressCond, String mat, String checkT){
        return  Double.valueOf(K2JSON.get(weldType + weldNum  + stressCond + mat + weldTechnology + checkT).toString());
    }

    public Double getBeta(String weldTechnology){
        return Double.valueOf(weldTechnologiesJSON.get(weldTechnology).toString());
    }

    public Set<String> getMaterials() {
        return K1JSON.names().toList().stream().map(Object::toString).collect(Collectors.toSet());
    }

    public Set<String> getSorts(String material) {
        return K1JSON.getJSONObject(material).names().toList().stream().map(Object::toString).collect(Collectors.toSet());
    }

    public Set<String> getThickness(String material, String sort) {
        return  K1JSON.getJSONObject(material).getJSONObject(sort).
                names().toList().stream().map(Object::toString).collect(Collectors.toSet());
    }

    public Set<String> getPreTempered(String material, String sort, String thickness) {
        return K1JSON.getJSONObject(material).getJSONObject(sort).getJSONObject(thickness).
                names().toList().stream().map(Object::toString).collect(Collectors.toSet());
    }

    public Set<String> getMatMethod(String material, String sort, String thickness, String preTempered) {
        return K1JSON.getJSONObject(material).getJSONObject(sort).getJSONObject(thickness).
                getJSONObject(preTempered).names().toList().stream().map(Object::toString).collect(Collectors.toSet());
    }

    public Set<String> getPostTempered(String material, String sort, String thickness, String preTempered, String matMethod) {
        return K1JSON.getJSONObject(material).getJSONObject(sort).getJSONObject(thickness).
                getJSONObject(preTempered).getJSONObject(matMethod).
                names().toList().stream().map(Object::toString).collect(Collectors.toSet());
    }

    public Set<String> getTypeWeldJoints() {
        return weldTypeJointsJSON.names().toList().stream().map(Object::toString).collect(Collectors.toSet());
    }

    public Set<Integer> getWeldNumbers(String weldJoint) {
        return weldTypeJointsJSON.getJSONArray(weldJoint).toList().stream().
                map(o -> Integer.valueOf(o.toString())).collect(Collectors.toSet());
    }

    public Set<String> getWeldTechnologies() {
        return weldTechnologiesJSON.keySet();
    }

    public Set<String> getTypesCheckingWeld() {
        return weldCheckTypesJSON.toList().stream().map(Object::toString).collect(Collectors.toSet());
    }

    public Set<String> getStressConditions() {
        return stressConditionsJSON.toList().stream().map(Object::toString).collect(Collectors.toSet());
    }



}
