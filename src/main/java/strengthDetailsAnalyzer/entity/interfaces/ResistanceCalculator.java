package strengthDetailsAnalyzer.entity.interfaces;

public interface ResistanceCalculator {
    default Double calculateResistance(Double inertia, Double coord){
        return inertia/coord;
    }
}
