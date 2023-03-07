package strengthDetailsAnalyzer.entity.enums;

public enum MomentsType {
    CONSOLE("Q ∙ l"),
    DOUBLE_SUPPORTED("Q ∙ l / 4"),
    TRIPLE_SUPPORTED("3 ∙ Q ∙ l / 64"),
    QUAD_SUPPORTED("3 ∙ Q ∙ l / 128");

    public String equation;
    MomentsType(String equation){
        this.equation = equation;
    }
}