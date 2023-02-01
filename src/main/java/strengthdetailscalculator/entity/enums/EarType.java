package strengthdetailscalculator.entity.enums;

public enum EarType {
    STEEL_CENTRAL(1d, "Стальная и центральная"),
    STEEL_SIDE(0.9, "Стальная и боковая"),
    ALUMINUM_SIDE(0.75, "Алюминиевая и боковая"),
    SINGLE("Одиночная");

    public Double val;
    public String description;

    EarType(Double val , String description) {
        this.val = val;
        this.description = description;
    }
    EarType(String description){
        this.description = description;
    }
}
