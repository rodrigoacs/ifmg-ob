import java.util.ArrayList;

public class JogarDardoThread {
  public static void main(String[] args) {
    long nDardos = 100000000L;
    int nThreads = Runtime.getRuntime().availableProcessors();
    long nAcertos = 0;
    ArrayList<Jogadores> jogadores = new ArrayList<Jogadores>();

    for (int i = 0; i < nThreads; i++) {
      Jogadores j = new Jogadores(nDardos / nThreads);
      jogadores.add(j);
      j.start();
    }

    for (Jogadores j : jogadores) {
      try {
        j.join();
        nAcertos += j.getAcertos();
      } catch (InterruptedException e) {
        System.out.println(e);
      }
    }

    System.out.println(nThreads + " Threads: ");
    System.out.println("Pi = " + 4.0 * nAcertos / nDardos);
    System.out.print("Tempo de execução(s): ");
  }

}