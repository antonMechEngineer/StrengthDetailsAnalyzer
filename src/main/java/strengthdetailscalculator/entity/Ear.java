package strengthdetailscalculator.entity;

import lombok.Getter;
import lombok.Setter;
import strengthdetailscalculator.entity.enums.EarType;
import strengthdetailscalculator.entity.interfaces.AxialDeformable;

import static java.lang.Math.*;

@Getter
@Setter
public class Ear extends Detail implements AxialDeformable {
    private final Double outerD;
    private final Double internalD;
    private final Double thickness;
    private final Double eccentricity;
    private final EarType earType;
    private final Boolean isSingleShearedConnection;
    private final Boolean currentEarIsLarger;
    private final Double gap;
    private final Double axialArea;
    private final Double axialStress;
    private final Double axialSafetyFactor;
    private static final Double DEFAULT_ALFA = 1.0;
    private static final Double DEFAULT_K = 0.35;

    public Ear(Detail detail, Double outerD, Double internalD, Double thickness, Double eccentricity,
               Boolean isSingleShearedConnection, EarType earType, Boolean currentEarIsLarger, Double gap) {
        super(detail.getName(), detail.getCode(), detail.getMaterial(), detail.getYieldStress(), detail.getForce());
        this.outerD = outerD;
        this.internalD = internalD;
        this.thickness = thickness;
        this.eccentricity = eccentricity;
        this.isSingleShearedConnection = isSingleShearedConnection;
        this.earType = earType;
        this.currentEarIsLarger = currentEarIsLarger;
        this.gap = gap;
        this.axialArea = calculateAxialArea();
        this.axialStress = calculateAxialStress();
        this.axialSafetyFactor = calculateAxialSafetyFactor();
    }


    @Override
    public Double getAxialStress() {
        return force / calculateAxialArea();
    }

    @Override
    public Double calculateAxialArea() {
        return thickness * (outerD - internalD) * calculateK() * calculateAlfa();
    }

    public Double calculateK() {
        if (isSingleShearedConnection.booleanValue()) {
            return earType.val;
        } else {
            if (currentEarIsLarger) {
                return 1 / (4.5 - 2 * gap / thickness);
            } else {
                return DEFAULT_K;
            }
        }
    }

    public Double calculateAlfa() {
        Double kD = outerD / internalD;
        Double kC = eccentricity / internalD;
        if ((0.10506 * (pow(kD, 2) - 0.1223 * kD + 0.0163)) < kC) {
            return 1.025 + 0.92 * eccentricity / (internalD * (kD - 1)) - 0.1 * kD;
        } else {
            return DEFAULT_ALFA;
        }
    }

}
