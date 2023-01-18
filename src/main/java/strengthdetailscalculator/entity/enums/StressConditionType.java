package strengthdetailscalculator.entity.enums;

public enum StressConditionType {

    SHEAR(0.6),
    AXIAL(1.0),
    BENDING(1.1),
    VON_MISSES(1.0);
    public Double stressRatio;

    StressConditionType(Double stressRatio) {
        this.stressRatio = stressRatio;
    }
}
