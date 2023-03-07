package strengthDetailsAnalyzer.entity;
import lombok.Getter;
import strengthDetailsAnalyzer.entity.interfaces.MomentCalculator;
import strengthDetailsAnalyzer.entity.interfaces.ResistanceCalculator;
import strengthDetailsAnalyzer.entity.interfaces.StressCalculator;
import static java.lang.Math.*;

@Getter
public class Weld extends Detail implements ResistanceCalculator, StressCalculator, MomentCalculator {

    public static final Double DEFAULT_MIN_SAFETY_FACTOR = 1.0;


    private final Double Fx;
    private final Double Fy;
    private final Double Fz;

    private final Double lx;
    private final Double ly;
    private final Double lz;

    private final Double Mx;
    private final Double My;
    private final Double Mz;

    private final Double Ix;
    private final Double Iy;
    private final Double xMax;
    private final Double yMax;
    private final Double Wx;
    private final Double Wy;
    private final Double Wk;
    private final Double r;
    private final Double A;
    private final String detailMaterial;
    private final String detailSortMaterial;
    private final String weldMatMethod;
    private final String typeWeldJoint;
    private final String weldNumber;
    private final String weldTechnology;
    private final String weldStressCondition;
    private final String checkingType;
    private final String postTempered;
    private final String preTempered;
    private final String rangeThicknessDetail;
    private final Double sigmaX;
    private final Double sigmaY;
    private final Double sigmaZ;
    private final Double sigma;
    private final Double beta;
    private final Double K1;
    private final Double K2;
    private final Double Ksv;
    private final Double safetyFactor;

    public Weld(Detail detail,
                Double A,
                Double Fx, Double Fy, Double Fz,
                Double lx, Double ly, Double lz,
                Double Ix, Double Iy, Double xMax, Double yMax, Double r,
                String detailMaterial, String detailSortMaterial, String weldMatMethod, String typeWeldJoint,
                String weldNumber, String weldTechnology, String weldStressCondition, String checkingType,
                String postTempered, String preTempered, String rangeThicknessDetail, Double beta, Double K1, Double K2) {
        super(detail.getName(), detail.getCode(), detail.getMaterial(), detail.getYieldStress(), detail.getMinSafetyFactor());
        this.Fx = Fx;
        this.Fy = Fy;
        this.Fz = Fz;
        this.lx = lx;
        this.ly = ly;
        this.lz = lz;
        this.r = r;
        this.xMax = xMax;
        this.yMax = yMax;
        this.Ix = Ix;
        this.Iy = Iy;
        this.A = A;
        this.detailMaterial = detailMaterial;
        this.detailSortMaterial = detailSortMaterial;
        this.weldMatMethod = weldMatMethod;
        this.typeWeldJoint = typeWeldJoint;
        this.weldNumber = weldNumber;
        this.weldTechnology = weldTechnology;
        this.weldStressCondition = weldStressCondition;
        this.checkingType = checkingType;
        this.postTempered = postTempered;
        this.preTempered = preTempered;
        this.rangeThicknessDetail = rangeThicknessDetail;
        this.Mx = calculateMoment(Fx, lx);
        this.My = calculateMoment(Fy, ly);
        this.Mz = calculateMoment(Fz, lz);
        this.Wx = calculateResistance(Ix, yMax);
        this.Wy = calculateResistance(Iy, xMax);
        this.Wk = calculateResistance(Ix + Iy, r);
        this.beta = beta;
        this.K1 = K1;
        this.K2 = K2;
        this.Ksv = calculateKsv();
        this.sigmaX = calculateSigmaX();
        this.sigmaY = calculateSigmaY();
        this.sigmaZ = calculateSigmaZ();
        this.sigma =calculateSigma();
        this.safetyFactor = calculateSafetyFactor();
    }


    private Double calculateKsv() {
        return K1 * K2;
    }

    private Double calculateSafetyFactor() {
        return calculateKsv() * beta * this.getYieldStress() / sigma;
    }

    private Double calculateSigmaX() {
        return calculateStress(Fx, A) + calculateStress(Mz, Wk);
    }

    private Double calculateSigmaY() {
        return calculateStress(Fy, A) + calculateStress(Mz, Wk);
    }

    private Double calculateSigmaZ() {
        return calculateStress(Fz, A) + calculateStress(Mx, Wx) + calculateStress(My, Wy);
    }

    private Double calculateSigma() {
        return sqrt(pow(sigmaX, 2) + pow(sigmaY, 2) + pow(sigmaZ, 2));
    }

}
