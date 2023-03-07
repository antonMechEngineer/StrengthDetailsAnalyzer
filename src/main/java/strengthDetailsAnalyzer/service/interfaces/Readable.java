package strengthDetailsAnalyzer.service.interfaces;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public interface Readable {
    default String read(String path) {
        String line;
        StringBuilder fileString = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = br.readLine()) != null) {
                fileString.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileString.toString();
    }
}
