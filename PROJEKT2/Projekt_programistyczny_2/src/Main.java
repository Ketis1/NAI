import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static List<Observation> trainData;
    private static List<Observation> testData;
    private static Map<String, Integer> classToLabelMap = new HashMap<>();
    private static Map<Integer, String> labelToClassMap = new HashMap<>();

    public Main() throws IOException {
        this.loadTestSetData("data/Test-set.csv");
        this.loadTrainSetData("data/Train-set.csv");

        classToLabelMap.put("Iris-setosa", 0);
        classToLabelMap.put("Iris-versicolor", 1);

        labelToClassMap.put(0,"Iris-setosa");
        labelToClassMap.put(1,"Iris-versicolor");
    }
    public static void main(String[] args) throws IOException {
        Main main = new Main();

        //interface
        Scanner scanner = new Scanner(System.in);
        double alpha;
        while (true){
            System.out.println("podaj alfe (stala uczenia) w przedziale (0,1)");
            alpha = scanner.nextDouble(); //(0,1)
            if (alpha>0 && alpha<1){
                break;
            }else {
                System.out.println("niepoprawna wartosc :(");
            }
        }

        System.out.println("podaj liczbe epok uczenia perceptronu");
        int epochs = scanner.nextInt();
        try {
            int numberOfInputs = trainData.get(0).getDataFeatures().length;
            Perceptron perceptron = new Perceptron(alpha,numberOfInputs);

            //learning
            for (int ep=0;ep<epochs;ep++){
                Collections.shuffle(trainData);//zeby nie uczyl sie rozpoznawac tylko jednej klasy
                for(Observation obs:trainData){
                    perceptron.learn(obs.getDataFeatures(), classToLabelMap.get(obs.getLabel()));
                }
            }

            //testing
            int sum=0;
            int sumOfSetosa=0;
            int sumOfVersicolor=0;
            int correct=0;
            int correctSetosa=0;
            int correctVersicolor=0;

            for (Observation obs:testData){
                int out = perceptron.classify(obs.getDataFeatures());
                if (classToLabelMap.get(obs.getLabel())==out){
                    //System.out.println("test: "+classToLabelMap.get(obs.getLabel())+"=="+out);
                    correct++;

                    if (out==0){
                        correctSetosa++;
                    }else {
                        correctVersicolor++;
                    }
                }
                sum++;
                if(classToLabelMap.get(obs.getLabel())==0){
                    sumOfSetosa++;
                }else {
                    sumOfVersicolor++;
                }
            }
//            System.out.println(correct);
//            System.out.println(sum);
            System.out.println("accuracy: "+100*(double)correct/sum+"%");
            System.out.println("accuracy of classifying setosa: "+100*(double)correctSetosa/sumOfSetosa+"%");
            System.out.println("accuracy of classifying versicolor: "+100*(double)correctVersicolor/sumOfVersicolor+"%");

            //wczytywanie wektorow uzytkownika
            while (true){
                System.out.println("Enter "+numberOfInputs+ " comma-separated values for the features of the vector to classify (e.g., 5.1,3.5,1.4,0.2): or type stop to exit");
                String userInput = scanner.nextLine();
                if (userInput.equals("stop")){
                    break;
                }
                String[] parts = userInput.split(",");
                if (parts.length!=numberOfInputs){
                    System.out.println("wrong input");
                    continue;
                }
                double[] vector = new double[numberOfInputs];
                for (int i=0;i<vector.length;i++){
                    vector[i]=Double.parseDouble(parts[i]);
                }
                int out = perceptron.classify(vector);
                System.out.println("Predicted label for the input vector: " + labelToClassMap.get(out));
            }






        } catch (IndexOutOfBoundsException e) {
            System.out.println("brak wczytanych danych");
            e.printStackTrace();
        }



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

                trainData.add(new Observation(features, label));


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

                testData.add(new Observation(features, label));


            }
        }
    }






}
