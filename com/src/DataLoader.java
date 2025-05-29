import java.io.*;
import java.util.*;

public class DataLoader {
    public static List<Double> t = new ArrayList<>();
    public static List<Double> f = new ArrayList<>();

    public static void load(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine(); // Pular o cabeçalho
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.trim().split("\\s+");
            t.add(Double.parseDouble(parts[0]));
            f.add(Double.parseDouble(parts[1]));
        }
        br.close();
    }
}