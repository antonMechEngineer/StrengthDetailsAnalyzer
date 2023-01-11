package strengthdetailscalculator.entity;

import lombok.Getter;
import lombok.Setter;
import strengthdetailscalculator.entity.enums.ScrewType;
import strengthdetailscalculator.entity.interfaces.AxialDeformable;
import strengthdetailscalculator.entity.interfaces.ShearDeformable;
import static java.lang.Math.PI;
import static java.lang.Math.pow;

@Getter
@Setter
public class Screw extends Detail implements ShearDeformable, AxialDeformable {
    private final static Integer NUMBER_ACTIVE_SCREW_PITCHES = 8;
    private final Double mainD;
    private final Double internalD;
    private final Double minD;
    private final Double threadPitch;
    private Double height;
    private final ScrewType screwType;
    private final Double shearArea;
    private final Double shearStress;
    private final Double shearSafetyFactor;
    private final Double axialArea;
    private final Double axialStress;
    private final Double axialSafetyFactor;

    public Screw(String name, String code, String material, Double yieldStress, Double force, Double mainD,
            Double threadPitch, Double height, ScrewType screwType, Double internalD, Double minD) {
        super(name, code, material, yieldStress, force);
        this.mainD = mainD;
        this.threadPitch = threadPitch;
        this.height = height;
        this.screwType = screwType;
        this.internalD = internalD;
        this.minD = minD;
        clarifyLengthScrew();
        this.shearArea = calculateShearArea();
        this.shearStress = calculateShearStress(force);
        this.shearSafetyFactor = calculateShearSafetyFactor(force, yieldStress);
        this.axialArea = calculateAxialArea();
        this.axialStress = calculateAxialStress(force);
        this.axialSafetyFactor = calculateAxialSafetyFactor(force, yieldStress);
    }

    @Override
    public Double calculateShearArea() {
        return PI * screwType.kp * internalD * height * calculateKh();
    }

    @Override
    public Double calculateAxialArea() {
        return PI * pow(minD, 2.0) / 4;
    }

    public Double calculateKh() {
        return 5 * threadPitch / mainD;
    }

    private void clarifyLengthScrew() {
        if (height > threadPitch * NUMBER_ACTIVE_SCREW_PITCHES) {
            height = threadPitch * NUMBER_ACTIVE_SCREW_PITCHES;
        }
    }
}
