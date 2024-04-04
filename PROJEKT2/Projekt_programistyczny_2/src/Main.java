import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private List<DataPoint> trainData;
    private List<DataPoint> testData;
    public static void main(String[] args) {
        //wczytanie danych

    }
    public void loadTrainSetData(String trainSetFileName) throws FileNotFoundException, IOException {
        this.trainData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(trainSetFileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                double[] features = new double[parts.length-1];

                for (int i = 0; i < parts.length-1; i++) {
                    features[i] = Double.parseDouble(parts[i]);
                }

                String label = parts[parts.length-1];

                trainData.add(new DataPoint(features, label));


            }
        }
    }
    public void loadTestSetData(String testSetFileName) throws FileNotFoundException, IOException {
        this.testData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(testSetFileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                double[] features = new double[parts.length-1];

                for (int i = 0; i < parts.length-1; i++) {
                    features[i] = Double.parseDouble(parts[i]);
                }

                String label = parts[parts.length-1];

                testData.add(new DataPoint(features, label));


            }
        }
    }






}
