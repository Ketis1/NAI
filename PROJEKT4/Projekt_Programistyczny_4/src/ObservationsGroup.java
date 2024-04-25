import java.util.*;

public class ObservationsGroup {
    private List<Observation> observations;
    private double[] centroid;

    public ObservationsGroup(int dataVectorLenght) {
        observations=new ArrayList<>();
        centroid=new double[dataVectorLenght];
    }
    public void addObservation(Observation observation){
        observations.add(observation);
    }
    public void clearObservations(){
        observations.clear();
    }


    public void calculateCentroid(){
        //utworzenie nowego pustego wektora, przyszlego centroidu
        double[] newCentroid=centroid;
        for (int i =0; i<newCentroid.length;i++){
            newCentroid[i]=0;
        }

        //obliczanie sredniej arytmetycznej
        for (Observation obs:observations){
            for (int i =0; i<newCentroid.length;i++){
                newCentroid[i]+=obs.getDataFeatures()[i];
            }
        }
        for (int i =0; i<newCentroid.length;i++){
            newCentroid[i]=newCentroid[i]/(double)observations.size();
        }
    }


    public List<Observation> getObservations() {
        return observations;
    }


    public double[] getCentroid() {
        return centroid;
    }


    @Override
    public String toString() {
        String res="";
        res+="\n======================\n";
        res+="\nLiczba wszystkich obserwacji: " + observations.size();
        res+="\nSumOfSquaredDistances: "+calculateSumOfSquaredDistances();
        res += "\nCentroid: " + Arrays.toString(centroid);
        res+="\nSzczegóły dla każdej etykiety:";
        Map<String, Integer> observationsDetails=new HashMap<>();
        for (Observation obs:observations){
            observationsDetails.put(obs.getLabel(),observationsDetails.getOrDefault(obs.getLabel(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : observationsDetails.entrySet()) {
            String label = entry.getKey();
            int count = entry.getValue();
            double percentage = ((double) count / observations.size()) * 100;
            res+="\nDla '" + label + "': " + count + " obserwacji (" + percentage + "%)";
        }
        res+="\n\n======================\n";
        return res;
    }
    public double calculateSumOfSquaredDistances() {
        double sum = 0;

        // iterowanie przez wszystkie pary wektorow
        for (int i = 0; i < observations.size(); i++) {
            double[] vector1 = observations.get(i).getDataFeatures();
            for (int j = i + 1; j < observations.size(); j++) {
                double[] vector2 = observations.get(j).getDataFeatures();


                double distance = Observation.calculateDistance(vector1, vector2);
                sum += distance * distance;
            }
        }

        return sum;
    }
}
