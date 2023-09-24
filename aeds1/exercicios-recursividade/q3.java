// 3 - Escreva uma função recursiva que recebe um número e verifica se este é um primo ou não. 
public class q3 {
  public static void main(String[] args) {
    final int INITIAL_DIVISOR = 2;
    System.out.println(isPrime(144, INITIAL_DIVISOR));
  }

  public static boolean isPrime(int n, int divisor) {
    if (n <= 1 || n % divisor == 0) {
      return false;
    }
    if (divisor * divisor > n) {
      return true;
    }
    return isPrime(n, divisor + 1);
  }

}
