import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static int capacity;
    private static int numberOfItems;
    private static int[] values;
    private static int[] weights;

    public static void main(String[] args) {
        //FILE PATH IS GIVEN AS A PROGRAM ARGUMENT
        if(args.length < 1){
            System.out.println("You need to give file path as program argument dummy!!!");
        }

        //reading data from file
        String filePath = args[0];
        try {
            loadFile(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
        int[] bestCombo = new int[numberOfItems];
        int maxValue=0;

        //max of possible combinations
        int totalCombinations = (1 << numberOfItems);

        int progressStep = totalCombinations / 1000;
        for (int i=0;i<totalCombinations;i++){
            int[] currentCombination = getCombination(i);
            int currentWeight = calculateWeight(currentCombination);
            int currentBenefit = calculateValue(currentCombination);

            if (currentWeight<= capacity && currentBenefit >maxValue){
                maxValue = currentBenefit;
                bestCombo = Arrays.copyOf(currentCombination,numberOfItems);
                System.out.println("Iteration: " + i + " Best total value: " + maxValue + " Current Combination: " + Arrays.toString(currentCombination));
            }

            //print every 1000th iteration
//            if (i % 1000 == 0 && i>0) {
//                System.out.println("Iteration: " + i + " Best total value: " + maxValue + " Current Combination: " + Arrays.toString(currentCombination));
//            }

            if (i % progressStep == 0) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                double progress = (100.0 * i) / totalCombinations;

                System.out.printf("Progress: %.1f%% - Best Benefit: %d - Current Combination: %s%n", progress, maxValue, Arrays.toString(currentCombination));
            }
        }
        System.out.println("Best combination: " + Arrays.toString(bestCombo) + "    YIPPIE!!!");




    }
    private static void loadFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        capacity=Integer.parseInt(tokenizer.nextToken());
        numberOfItems=Integer.parseInt(tokenizer.nextToken());

        values = new int[numberOfItems];
        weights = new int[numberOfItems];

        tokenizer = new StringTokenizer(reader.readLine(), ",");
        for (int i = 0; i < numberOfItems; i++) {
            values[i] = Integer.parseInt(tokenizer.nextToken());
        }

        tokenizer = new StringTokenizer(reader.readLine(), ",");
        for (int i = 0; i < numberOfItems; i++) {
            weights[i] = Integer.parseInt(tokenizer.nextToken());
        }

        reader.close();
        System.out.println("data loaded from "+filePath);
        System.out.println("capacity: "+capacity);
        System.out.println("number of items: "+numberOfItems);
        System.out.println("values: "+Arrays.toString(values));
        System.out.println("weights: "+Arrays.toString(weights));
    }

    private static int[] getCombination(int num){
        int[] combination = new int[numberOfItems];
        for (int i=0;i<numberOfItems;i++){
            combination[i]= (num>>i)&1;
        }
        return combination;
    }

    private static int calculateWeight(int [] combination){
        int totalWeight=0;
        for (int i=0;i<numberOfItems;i++){
            if (combination[i]==1){
                totalWeight+=weights[i];
            }
        }
        return totalWeight;
    }

    private static int calculateValue(int[] combination){
        int totalBenefit=0;
        for(int i=0;i<numberOfItems;i++){
            if (combination[i]==1){
                totalBenefit += values[i];
            }
        }
        return totalBenefit;
    }
}