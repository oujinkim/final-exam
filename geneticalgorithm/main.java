package geneticalgorithm;

public class main {
    public static void main(String[] args) {
        geneticalgorithm ga = new geneticalgorithm();
        double solution = ga.solve(20, x-> 17524*x*x-22379*x+7520.2); //y=ax+b에서 모수 a의 손실함수
//        double solution = ga.solve(10,x-> 22*x*x - 1623.8*x + 30337);//y=ax+b에서 모수 b의 손실함수
        System.out.println(solution);
    }
}