package strengthdetailscalculator.entity.enums;

public enum EarType {
    STEEL_CENTRAL(1d),
    STEEL_SIDE(0.9),
    ALUMINUM_SIDE(0.75);

    public Double val;

    EarType(Double val){
        this.val = val;
    }

}
