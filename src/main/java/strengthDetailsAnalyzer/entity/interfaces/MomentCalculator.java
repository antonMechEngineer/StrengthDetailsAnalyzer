package strengthDetailsAnalyzer.entity.interfaces;

public interface MomentCalculator {
    default Double calculateMoment(Double force, Double length){
        return force * length;
    }
}
