package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TspFileReader {

    public static ArrayList<TspPoint> points = new ArrayList<TspPoint>();
    private static int dims;

    public TspFileReader(String problem){
        readProblemFromFile(problem);
    }

    public int getDims() {
        return dims;
    }

    public ArrayList<TspPoint> getPoints() {
        return points;
    }

    private static void readProblemFromFile(String filePath) {
        String currentDirectory = System.getProperty("user.dir");
        filePath = currentDirectory + "/" + filePath;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean shouldRead = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("DIMENSION")){
                    String[] parts = line.split("\\s+");
                    dims=Integer.parseInt(parts[2]);
                }
                if (shouldRead && !line.equals("EOF")) {
                    points.add(new TspPoint(line));
                }
                if (line.equals("NODE_COORD_SECTION") && !shouldRead) {
                    shouldRead = true;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading TSP file: " + e.getMessage());
        }
    }
}
