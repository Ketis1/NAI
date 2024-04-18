import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Observation> testData;
    public static List<Observation> trainngData;
    private static List<String> testFileNames;
    public static void main(String[] args) {


        int epochs = 10000;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("podaj liczbe epok uczenia perceptronu");
//        epochs = scanner.nextInt();


        testData=new ArrayList<>();
        String englishFolderPath = "data/test/english/";
        String spanishFolderPath = "data/test/spanish/";
        String polishFolderPath = "data/test/polish/";
        loadTestData(englishFolderPath, "english");
        loadTestData(spanishFolderPath, "spanish");
        loadTestData(polishFolderPath, "polish");
    }



    private static List<String> loadTestFileNamesFromFolder(String folderPath) {
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

    private static void loadTestData(String folderPath, String language){
        List<String> filesNames= loadTestFileNamesFromFolder(folderPath);
        for(String fileName:filesNames){
            System.out.println(folderPath+fileName);
            testData.add(new Observation(language,folderPath+fileName));
        }
    }
}