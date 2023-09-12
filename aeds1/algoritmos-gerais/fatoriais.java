public class fatoriais {

  public static int fatorial(int n) {
    int r;
    for (r = 1; n > 1; n = n - 1) {
      r *= n;
    }
    return r;
  }

  public static int fatorialRecursivo(int n) {
    if (n == 0) {
      return 1;
    } else {
      return n * fatorialRecursivo(n - 1);
    }
  }

  public static void main(String[] args) {
    System.out.println(fatorial(5));
    System.out.println(fatorialRecursivo(5));
  }
}
