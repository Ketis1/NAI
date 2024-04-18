import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static List<Observation> testData;
    public static List<Observation> trainngData;
    private static List<String> testFileNames;
    public static void main(String[] args) {

        trainngData=new ArrayList<>();
        testData=new ArrayList<>();
        String englishFolderPath = "data/training/english/";
        String spanishFolderPath = "data/training/spanish/";
        String polishFolderPath = "data/training/polish/";
        loadTrainingData(englishFolderPath, "english");
        loadTrainingData(spanishFolderPath, "spanish");
        loadTrainingData(polishFolderPath, "polish");



        int epochs = 10000;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("podaj liczbe epok uczenia perceptronu");
//        epochs = scanner.nextInt();
        int numOfLetters=26;
        Perceptron englishPerceptron = new Perceptron(0.1,numOfLetters);
        Perceptron polishPerceptron = new Perceptron(0.1,numOfLetters);
        Perceptron spanishPerceptron = new Perceptron(0.1,numOfLetters);
        for (int ep=0;ep<epochs;ep++){
            for (Observation obs :trainngData){

                Collections.shuffle(trainngData);

                //uczenie perceptronu ktory powininen zwracac 1 jesli to jest jego jezyk
                englishPerceptron.learn(obs.getDataFeatures(), obs.getLanguage()=="english"?1:0);
                polishPerceptron.learn(obs.getDataFeatures(), obs.getLanguage()=="polish"?1:0);
                spanishPerceptron.learn(obs.getDataFeatures(), obs.getLanguage()=="spanish"?1:0);
            }
        }

        List<Perceptron> inputLayer = new ArrayList<>();
        List<Perceptron> middleLayer = new ArrayList<>();
        List<Perceptron> outputLayer = new ArrayList<>();

    }



    private static List<String> loadTraingFilesNamesFromFolder(String folderPath) {
        List<String> testFileNames = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    testFileNames.add(file.getName());
                    System.out.println(file.getName());
                }
            }
        }

        return testFileNames;
    }

    private static void loadTrainingData(String folderPath, String language){
        List<String> filesNames= loadTraingFilesNamesFromFolder(folderPath);
        for(String fileName:filesNames){
            System.out.println(folderPath+fileName);
            trainngData.add(new Observation(language,folderPath+fileName));
        }
    }
}