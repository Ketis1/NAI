import java.io.File;
import java.util.*;

public class Main {
    public static List<Observation> testData;
    public static List<Observation> trainngData;
    public static void main(String[] args) {

        trainngData=new ArrayList<>();
        testData=new ArrayList<>();
        String englishFolderPath = "data/training/english/";
        String spanishFolderPath = "data/training/spanish/";
        String polishFolderPath = "data/training/polish/";
        loadTrainingData(englishFolderPath, "english");
        loadTrainingData(spanishFolderPath, "spanish");
        loadTrainingData(polishFolderPath, "polish");



        int epochs = 100000;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("podaj liczbe epok uczenia perceptronu");
//        epochs = scanner.nextInt();
        int numOfLetters=26;
        Map<String,Perceptron> perceptronMap= new HashMap<>();
        perceptronMap.put("english",new Perceptron(0.1,numOfLetters));
        perceptronMap.put("polish",new Perceptron(0.1,numOfLetters));
        perceptronMap.put("spanish",new Perceptron(0.1,numOfLetters));


        for (int ep=0;ep<epochs;ep++){
            for (Observation obs :trainngData){

                Collections.shuffle(trainngData);

//                //uczenie perceptronu ktory powininen zwracac 1 jesli to jest jego jezyk

                for (Map.Entry<String,Perceptron> entry:perceptronMap.entrySet()){
                    entry.getValue().learn(obs.getDataFeatures(), obs.getLanguage()== entry.getKey()?1:0);
                }

            }
        }


        //tutaj mozna ustawic jaki plik sie testuje, ewentualnie zaimplementowac podawanie jezyka i sciezki przez uzytkownika
        Observation input = new Observation("spanish","data/test/spanish/5.txt");

        double max = -10000;
        String language="empty";
        for (Map.Entry<String,Perceptron> entry:perceptronMap.entrySet()){
            double res= entry.getValue().compute(input.getDataFeatures());
            System.out.println(entry.getKey()+" "+res);
            if (res>max){
                max=res;
                language=entry.getKey();
            }
        }
        System.out.println("\n========\n"+language+"\n========\n");


    }



    private static List<String> loadTraingFilesNamesFromFolder(String folderPath) {
        List<String> testFileNames = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    testFileNames.add(file.getName());
                }
            }
        }

        return testFileNames;
    }

    private static void loadTrainingData(String folderPath, String language){
        List<String> filesNames= loadTraingFilesNamesFromFolder(folderPath);
        for(String fileName:filesNames){
            trainngData.add(new Observation(language,folderPath+fileName));
        }
    }
}