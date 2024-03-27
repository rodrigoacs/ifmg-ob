import java.util.ArrayList;

public class JogadorDardo extends Thread {
  private long nDardos;
  private long nAcertos;

  public JogadorDardo(long nDardos) {
    this.nDardos = nDardos;
    this.nAcertos = 0;
  }

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

  public static void main(String[] args) {
    long nDardos = 100000000;
    int nThreads = 14;
    long nAcertos = 0;
    System.out.println(Runtime.getRuntime().availableProcessors());
    ArrayList<JogadorDardo> jogadores = new ArrayList<JogadorDardo>();
    
    for (int i = 0; i < nThreads; i++) {
      JogadorDardo j = new JogadorDardo(nDardos / nThreads);
      j.start();
      jogadores.add(j);
    }
    System.out.println(Thread.activeCount());
    
    jogadores.forEach(j -> {
      try {
        j.join();
      } catch (Exception e) {
        System.out.println(e);
      }
    });

    for (JogadorDardo jogadorDardo : jogadores) {
      nAcertos += jogadorDardo.getAcertos();
    }

    System.out.println("Pi = " + 4.0 * nAcertos / nDardos);
  }
}
