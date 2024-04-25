import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Observation {
    private double[] dataFeatures;
    private String label;

    public Observation(double[] dataFeatures, String label) {
        this.dataFeatures = dataFeatures;
        this.label = label;
    }

    public double[] getDataFeatures() {
        return dataFeatures;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "dataFeatures=" + Arrays.toString(dataFeatures) +
                ", label='" + label + '\'' +
                '}';
    }

    public static List<Observation> getObservationsFromFile(String filePath){
        List<Observation> observations = new ArrayList<>();
        try{
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line=reader.readLine()) != null){
                String[] parts = line.split(",");
                double[] features = new double[parts.length-1];

                for (int i = 0; i < parts.length-1; i++) {
                    features[i] = Double.parseDouble(parts[i]);
                }

                String label = parts[parts.length-1];

                observations.add(new Observation(features,label));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return observations;

    }

    public static double calculateDistance(double[] v1, double[] v2){
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("vectors are not the same lenght");
        }
        double sumOfSquares = 0;
        for (int i = 0; i < v1.length; i++) {
            double diff = v1[i] - v2[i];
            sumOfSquares += diff * diff;
        }

        return Math.sqrt(sumOfSquares);


    }
}
