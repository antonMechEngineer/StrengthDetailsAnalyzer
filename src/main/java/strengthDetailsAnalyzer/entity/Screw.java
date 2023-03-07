package strengthDetailsAnalyzer.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import strengthDetailsAnalyzer.entity.enums.ScrewType;
import strengthDetailsAnalyzer.entity.interfaces.AxialDeformable;
import strengthDetailsAnalyzer.entity.interfaces.ShearDeformable;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Screw extends Detail implements ShearDeformable, AxialDeformable {
    private final static Integer NUMBER_ACTIVE_SCREW_PITCHES = 8;
    private final Double force;
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


    public Screw (Detail detail, Double mainD, Double threadPitch, Double height,
                  ScrewType screwType, Double internalD, Double minD, Double force){
        super(detail.getName(), detail.getCode(), detail.getMaterial(), detail.getYieldStress(), detail.getMinSafetyFactor());
        this.mainD = mainD;
        this.threadPitch = threadPitch;
        this.height = height;
        this.screwType = screwType;
        this.internalD = internalD;
        this.minD = minD;
        this.force = force;
        clarifyLengthScrew();
        this.shearArea = calculateShearArea();
        this.shearStress = calculateShearStress();
        this.shearSafetyFactor = calculateShearSafetyFactor();
        this.axialArea = calculateAxialArea();
        this.axialStress = calculateAxialStress();
        this.axialSafetyFactor = calculateAxialSafetyFactor();
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
