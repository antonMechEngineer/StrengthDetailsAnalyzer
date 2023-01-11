package strengthdetailscalculator.entity.enums;

public enum ScrewType {
    TRAPEZOIDAL(0.65, "ГОСТ 24737-81"),
    METRICAL(0.75, "ГОСТ 24705-2004");
    public Double kp;
    public String gost;
    ScrewType(Double kp, String gost) {
        this.kp = kp;
        this.gost = gost;
    }
}
