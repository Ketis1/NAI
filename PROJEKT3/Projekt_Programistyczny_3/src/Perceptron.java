import java.util.Random;

public class Perceptron {
    private double alpha;
    private double theta;
    private double[] weights;
    private double bias;
    public Perceptron(double alpha, int number_of_inputs) {
        this.alpha = alpha;
        theta=1;
        weights=new double[number_of_inputs];
        bias = Math.random() * 2 - 1;
        for (int i=0;i< weights.length;i++){
            weights[i]= Math.random() * 2 - 1;//random values [-1, 1]
        }
    }


    public int classify(double data[]){
        if (data.length==weights.length){
            double sum=0;
            for (int i=0;i<weights.length;i++){
                sum += data[i]*weights[i];
            }

            if (sum>=theta){
                return 1;
            }else {
                return 0;
            }
        }else{
            System.out.println("data lenght != weights lenght");;
            throw new IllegalArgumentException("Długość danych musi być równa długości wag");
        }

    }

    public void learn(double data[],int expectedOutput){
        int output = classify(data);
        if (data.length==weights.length){

            if (output!=expectedOutput){ //chyba mozna bez tego ale nie bedzie niepotrzebnie przez petle przechodzic
                for (int i =0;i<weights.length;i++){
                    weights[i]=weights[i] +(expectedOutput-output)*alpha*data[i];
                }
                theta = theta - (expectedOutput - output) * alpha;

            }
        }else{
            System.out.println("data lenght != weights lenght");;
            throw new IllegalArgumentException("Długość danych musi być równa długości wag");
        }


    }

    public double[] getWeights() {
        return weights;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getTheta() {
        return theta;
    }
    public void getInfo(){
        System.out.print("[");
        for (int i = 0; i < weights.length; i++) {
            System.out.print(weights[i]);
            if (i < weights.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }


    public double compute(double[] input){
        double preActivation = -theta;
        for (int i=0;i<weights.length;i++){
            preActivation += input[i]*weights[i];
        }

        double output = sigmoid(preActivation);
        return output;
    }
    public static double sigmoid(double in){
        return 1 / (1 + Math.exp(-in));
    }

}
