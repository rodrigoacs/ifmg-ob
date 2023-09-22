// 5 - Escreva uma função recursiva para calcular a potência e de um número n. 
public class q5 {
  public static void main(String[] args) {
    System.out.println(power(2, 4));
  }

  public static int power(int e, int n) {
    return e == 1 ? n : n * power(e - 1, n);
  }
}
