package strengthDetailsAnalyzer.utils;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Component
public class DataLoader {

    private static final String PATH_METRICAL_SCREW = "src/main/resources/data/screw/metricalScrewData.csv";
    private static final String PATH_TRAPEZOIDAL_SCREW = "src/main/resources/data/screw/trapezoidalScrewData.csv";

    private final HashMap<Double, HashMap<Double, List<Double>>> metricalScrews = new HashMap<>();
    private final HashMap<Double, HashMap<Double, List<Double>>> trapezoidalScrews = new HashMap<>();

    public HashMap<Double, HashMap<Double, List<Double>>> getMetricalScrews() {
        if (metricalScrews.isEmpty()) {
            loadDataFromCSVFile(PATH_METRICAL_SCREW);
        }
        return metricalScrews;
    }

    public HashMap<Double, HashMap<Double, List<Double>>> getTrapezoidalScrews() {
        if (trapezoidalScrews.isEmpty()) {
            loadDataFromCSVFile(PATH_TRAPEZOIDAL_SCREW);
        }
        return trapezoidalScrews;
    }

    private void loadDataFromCSVFile(String currentDataPath) {
        try {
            Scanner scanner = new Scanner(new File(currentDataPath));
            scanner.nextLine();
            while (scanner.hasNext()) {
                String lineString = scanner.nextLine();
                String[] arrayLine = lineString.split(",");
                switch (currentDataPath) {
                    case PATH_TRAPEZOIDAL_SCREW:
                        HashMap<Double, HashMap<Double, List<Double>>> parsedTrapScrewLine = parseScrewLine(arrayLine);
                        insertScrewInStore(parsedTrapScrewLine, trapezoidalScrews);
                        break;
                    case PATH_METRICAL_SCREW:
                        HashMap<Double, HashMap<Double, List<Double>>> parsedMetrScrewLine = parseScrewLine(arrayLine);
                        insertScrewInStore(parsedMetrScrewLine, metricalScrews);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + currentDataPath);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private HashMap<String, ArrayList<Integer>> parsedWeldTypeJoints(String[] arrayLine) {
        HashMap<String, ArrayList<Integer>> weldTypeJointsLine = new HashMap<>();
        ArrayList<Integer> weldTypeJoints = new ArrayList<>();
        weldTypeJoints.add(Integer.valueOf(arrayLine[1]));
        weldTypeJointsLine.put(arrayLine[0], weldTypeJoints);
        return weldTypeJointsLine;
    }

    private HashMap<String, HashMap<String, String>> parseMaterialLine(String[] arrayLine) {
        HashMap<String, HashMap<String, String>> materialLine = new HashMap<>();
        String material = arrayLine[0];
        String mark = arrayLine[1];
        String weldMaterial = arrayLine[2];
        HashMap<String, String> markData = new HashMap<>();
        markData.put(mark, weldMaterial);
        materialLine.put(material, markData);
        return materialLine;
    }

    private HashMap<Double, HashMap<Double, List<Double>>> parseScrewLine(String[] arrayLine) {
        HashMap<Double, HashMap<Double, List<Double>>> screwLine = new HashMap<>();
        Double mainD = Double.valueOf(arrayLine[0]);
        Double threadPitch = Double.valueOf(arrayLine[1]);
        Double midD = Double.valueOf(arrayLine[2]);
        Double internalD = Double.valueOf(arrayLine[3]);
        Double minD = Double.valueOf(arrayLine[4]);
        HashMap<Double, List<Double>> pitchData = new HashMap<>();
        pitchData.put(threadPitch, Arrays.asList(midD, internalD, minD));
        screwLine.put(mainD, pitchData);
        return screwLine;
    }


    private void insertScrewInStore(HashMap<Double, HashMap<Double, List<Double>>> lineData,
                                    HashMap<Double, HashMap<Double, List<Double>>> currentStore) {
        Double currentMainD = lineData.keySet().stream().findFirst().orElseThrow();
        if (currentStore.containsKey(currentMainD)) {
            HashMap<Double, List<Double>> currentData = lineData.get(currentMainD);
            Double currentThreadPitch = currentData.keySet().stream().findFirst().orElseThrow();
            HashMap<Double, List<Double>> currentScrewData = currentStore.get(currentMainD);
            currentScrewData.put(currentThreadPitch, currentData.get(currentThreadPitch));
        } else {
            currentStore.putAll(lineData);
        }
    }
}
