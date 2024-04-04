import java.util.Arrays;

public class Observation {
    private double[] dataFeatures;
    private String label;

    public Observation(double[] dataFeatures, String label){
        this.dataFeatures=dataFeatures;
        this.label=label;
    }

    public double[] getDataFeatures(){
        return dataFeatures;
    }
    public String getLabel(){
        return label;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "dataFeatures=" + Arrays.toString(dataFeatures) +
                ", label='" + label + '\'' +
                '}';
    }
}