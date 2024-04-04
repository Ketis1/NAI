public class DataPoint {
    private double[] dataFeatures;
    private String label;

    public DataPoint(double[] dataFeatures, String label){
        this.dataFeatures=dataFeatures;
        this.label=label;
    }

    public double[] getDataFeatures(){
        return dataFeatures;
    }
    public String getLabel(){
        return label;
    }


}