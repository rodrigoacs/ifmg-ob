public class TrabalhadorSoma extends Thread {
  private static long contadorGlobal = 0L;

  private long gSoma;

  public TrabalhadorSoma(long soma) {
    this.gSoma = soma;
  }

  @Override
  public void run() {
    synchronized (TrabalhadorSoma.class) {
      for (long i = 0; i < gSoma; i++) {
        contadorGlobal++;
      }
    }
  }

  public static long getContadorGlobal() {
    return contadorGlobal;
  }
}
