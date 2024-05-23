import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static int capacity; // knapsack capacity
    private static int numberOfItems;
    private static int[] values;
    private static int[] weights;
    private static JTextArea progressTextArea;
    private static JProgressBar progressBar;
    private static JLabel bestCombinationLabel; // best vector label

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createGUI);

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

        new Thread(Main::runKnapsackBruteForce).start();
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Knapsack Brute Force");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        progressTextArea = new JTextArea();
        progressTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(progressTextArea);
        progressBar = new JProgressBar(0, 1000);
        progressBar.setStringPainted(true);
        bestCombinationLabel = new JLabel("Best combination: ");

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(progressBar, BorderLayout.SOUTH);
        frame.add(bestCombinationLabel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private static void runKnapsackBruteForce() {
        int[] bestCombination = new int[numberOfItems]; //best vector
        int maxPossibleValue = 0; //best total value
        int totalCombinations = (1 << numberOfItems); //max number of combinations
        int progressStep = totalCombinations / 1000; //step of printing progress

        for (int i = 0; i < totalCombinations; i++) {
            int[] currentCombination = getCombination(i);
            int currentWeight = calculateWeight(currentCombination);
            int currentBenefit = calculateTotalValue(currentCombination);

            if (currentWeight <= capacity && currentBenefit > maxPossibleValue) {
                maxPossibleValue = currentBenefit;
                bestCombination = Arrays.copyOf(currentCombination, numberOfItems);
                System.out.println("FOUND NEW BEST COMBO Iteration: " + i + " Best total value: " + maxPossibleValue + " Current Combination: " + Arrays.toString(currentCombination));
            }

            if (i % progressStep == 0) {
                updateProgress(i / progressStep, maxPossibleValue, currentCombination, bestCombination);
            }
        }

        updateProgress(1000, maxPossibleValue, bestCombination, bestCombination); // to make sure progress bar reaches 100%

        //without this best combination wouldnt print as a last thing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        progressTextArea.append("Best combination: " + Arrays.toString(bestCombination) + "\n");
    }

    //updating progess on GUI
    private static void updateProgress(int progress, int maxValue, int[] currentCombination, int[] bestCombination) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(progress);
            progressTextArea.append(String.format("Progress: %.1f%% - Best total value: %d - Current Combination: %s%n", progress / 10.0, maxValue, Arrays.toString(currentCombination)));
            bestCombinationLabel.setText("Best combination: " + Arrays.toString(bestCombination));
        });
    }

    private static void loadFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        capacity = Integer.parseInt(tokenizer.nextToken());
        numberOfItems = Integer.parseInt(tokenizer.nextToken());

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
    }

    private static int[] getCombination(int num) {
        int[] combination = new int[numberOfItems];
        for (int i = 0; i < numberOfItems; i++) {
            combination[i] = (num >> i) & 1;
        }
        return combination;
    }

    private static int calculateWeight(int[] combination) {
        int totalWeight = 0;
        for (int i = 0; i < numberOfItems; i++) {
            if (combination[i] == 1) {
                totalWeight += weights[i];
            }
        }
        return totalWeight;
    }

    private static int calculateTotalValue(int[] combination) {
        int totalBenefit = 0;
        for (int i = 0; i < numberOfItems; i++) {
            if (combination[i] == 1) {
                totalBenefit += values[i];
            }
        }
        return totalBenefit;
    }
}


