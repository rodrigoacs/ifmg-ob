// 3 - Escreva uma função recursiva que recebe um número e verifica se este é um primo ou não. 
public class q3 {
  public static void main(String[] args) {
    for (int i = 1; i <= 100; i++) {
      if (isPrime(i, 2)) {
        System.out.println(i);
      }
    }
  }

  public static boolean isPrime(int n, int divider) {
    if (n <= 1 || n % divider == 0) {
      return false;
    }
    if (divider * divider > n) {
      return true;
    }
    return isPrime(n, divider + 1);
  }

}
