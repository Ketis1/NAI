import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("podaj liczbe grup");
        Scanner scanner= new Scanner(System.in);
        int n=scanner.nextInt();
        System.out.println("podaj liczbe epok");
        int epochs = scanner.nextInt();
        KmeansApp kmeansApp=new KmeansApp(n,epochs);
        kmeansApp.startClustering();
        System.out.println("\n===============\nWYNIK OSTATECZNY\n===============\n");
        kmeansApp.printGroups();





    }

}