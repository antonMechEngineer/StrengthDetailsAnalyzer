package strengthDetailsAnalyzer.entity.interfaces;

public interface StressCalculator {
    default Double calculateStress(Double forceImpact, Double materialResistance) {
        return forceImpact / materialResistance;

    }
}
