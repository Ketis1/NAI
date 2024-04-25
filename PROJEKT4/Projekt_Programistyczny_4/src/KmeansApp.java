import java.util.ArrayList;
import java.util.List;

public class KmeansApp {
    List<Observation> observations;
    List<ObservationsGroup> groups;
    int dataVectorLenght;
    int n;
    int epochs;
    public KmeansApp(int n, int epochs){
        this.n=n;
        this.epochs=epochs;


        observations = Observation.getObservationsFromFile("data/iris.csv");
        dataVectorLenght=observations.get(0).getDataFeatures().length;
        groups = new ArrayList<>();

        //utworzenie grup
        for(int i=0;i<n;i++){
            groups.add(new ObservationsGroup(dataVectorLenght));
        }
        //wypelnienie grup losowymi danymi
        //Collections.shuffle(observations);
        for (int i = 0;i<observations.size();i++){
            groups.get(i%n).addObservation(observations.get(i));
        }
        //obliczenie wstepnych centroidow
        calculateGroupsCentroids();

    }
    public void startClustering(){
        //obliczenie wstepnych centroidow
        calculateGroupsCentroids();

        for (int i=0;i<epochs;i++){
            clearGroups();


            for(int j=0;j<observations.size();j++){
                Observation observation=observations.get(j);



                //wyszukiwanie najblizszej grupy
                double minDistance=Double.MAX_VALUE;
                ObservationsGroup closestGroup = null;

                for (ObservationsGroup og:groups) {
                    double dist = Observation.calculateDistance(og.getCentroid(), observation.getDataFeatures());
                    if (dist<minDistance){
                        minDistance=dist;
                        closestGroup=og;
                    }
                }

                //przydzielenie obserwacji do najblizszej grupy
                closestGroup.addObservation(observation);
            }
            calculateGroupsCentroids();
            printIterationInfo(i+1);
        }
    }





    public void calculateGroupsCentroids(){
        for (ObservationsGroup og:groups) {
            og.calculateCentroid();
        }
    }
    public void clearGroups(){
        for (ObservationsGroup og:groups) { og.clearObservations(); }
    }
    public void printGroups(){
        System.out.println(getGroupsDetails());
    }

    private void printIterationInfo(int iteration){

        System.out.println("\n====================================\n" +
                "\n=========\npo iteracji "+iteration+"\n=========\n" +
                "\nGroups details:" +
                "\n"+getGroupsDetails());
    }
    public String getGroupsDetails(){
        String res="";
        for (ObservationsGroup og:groups) {
            res+=og.toString()+"\n";
        };
        return res;
    }

}
