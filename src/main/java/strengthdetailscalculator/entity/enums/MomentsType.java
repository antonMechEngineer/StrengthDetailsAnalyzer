package strengthdetailscalculator.entity.enums;

public enum MomentsType {
    CONSOLE("Q l"),
    DOUBLE_SUPPORTED_ROTATION("Q l / 4");
    public String equation;
    MomentsType(String equation){
        this.equation = equation;
    }
}