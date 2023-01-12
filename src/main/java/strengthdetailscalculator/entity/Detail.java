package strengthdetailscalculator.entity;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Detail {
    protected final String name;
    protected final String code;
    protected final String material;
    protected final Double yieldStress;
    protected final Double force;
    protected final Double minSafetyFactor = 1.0;

    public Detail(String name, String code, String material, Double yieldStress, Double force) {
        this.name = name;
        this.code = code;
        this.material = material;
        this.yieldStress = yieldStress;
        this.force = force;
    }
    public Detail(List<TextField> textData, List<TextField> numericalData){
        this.name = textData.get(0).getText();
        this.code = textData.get(1).getText();
        this.material = textData.get(2).getText();
        this.yieldStress =  Double.valueOf(numericalData.get(0).getText());
        this.force = Double.valueOf(numericalData.get(1).getText());

    }


}
