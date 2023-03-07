package strengthDetailsAnalyzer.entity.enums;

public enum StrengthCondition {
    SAFETY(">", "Условие прочности выполняется."),
    FAIL("<", "Условие прочности не выполняется.");
    public String sign;
    public String conclusion;
    StrengthCondition(String sign, String conclusion){
        this.conclusion = conclusion;
        this.sign = sign;
    }
}
