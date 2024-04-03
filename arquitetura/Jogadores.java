import java.util.ArrayList;

public class Jogadores extends Thread {
  private long nDardos;
  private long nAcertos;

  public Jogadores(long nDardos) {
    this.nDardos = nDardos;
    this.nAcertos = 0;
  }

  @Override
  public void run() {
    double x, y;
    for (int i = 0; i < nDardos; i++) {
      x = Math.random();
      y = Math.random();
      if (Math.sqrt(x * x + y * y) < 1.0) {
        nAcertos++;
      }
    }
  }

  public long getAcertos() {
    return nAcertos;
  }

}
