public class monteCarlo {
  public static void main(String[] args) {
    long nDardos = 100000000;
    long nAcertos = 0;

    double x, y;
    for (int i = 0; i < nDardos; i++) {
      x = Math.random();
      y = Math.random();
      if (Math.sqrt(x * x + y * y) < 1.0) {
        nAcertos++;
      }

    }
    System.out.println("Pi = " + 4.0 * nAcertos / nDardos);
  }
}
