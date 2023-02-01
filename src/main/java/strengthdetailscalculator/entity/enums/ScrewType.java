package strengthdetailscalculator.entity.enums;

import lombok.ToString;

@ToString
public enum ScrewType {
    TRAPEZOIDAL(0.65, "ГОСТ 24737-81", "Трапецеидальная"),
    METRICAL(0.75, "ГОСТ 24705-2004", "Метрическая");
    public Double kp;
    public String gost;
    public String description;
    ScrewType(Double kp, String gost, String description) {
        this.kp = kp;
        this.gost = gost;
        this.description = description;
    }
}
