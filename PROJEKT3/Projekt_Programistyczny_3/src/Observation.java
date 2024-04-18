import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Observation {
    private double[] dataFeatures;
    private String language;
    private String filePath;

    public Observation(String language, String filePath) {
        this.language = language;
        this.filePath = filePath;
        dataFeatures= new double[26];//hard coded dla alfabetu
        loadData();
    }
    private void loadData(){
        for (int i = 0; i < dataFeatures.length; i++) {
            dataFeatures[i] = 0;
        }

        try {
            int totalLetters = 0;
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\s", "").toLowerCase();
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        totalLetters++;
                        dataFeatures[c - 'a']++;
                    }
                }
            }
            //obliczanie proporcji
            for (int i=0;i< dataFeatures.length;i++){
                dataFeatures[i]=dataFeatures[i]/totalLetters;
            }
//            for (double element : dataFeatures) {
//                System.out.println(element);
//            }



        } catch (IOException e) {
            System.out.println("nie udalo sie wczytac danych");
            e.printStackTrace();
        }


    }


    public double[] getDataFeatures() {
        return dataFeatures;
    }

    public String getLanguage() {
        return language;
    }
}
