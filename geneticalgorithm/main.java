package geneticalgorithm;

public class main {
    public static void main(String[] args) {
        geneticalgorithm ga = new geneticalgorithm();
        double solution = ga.solve(4, x-> - x*x+38*x+80);
        System.out.println(solution);
    }
}