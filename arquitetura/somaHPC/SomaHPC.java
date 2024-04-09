import java.util.ArrayList;

public class SomaHPC {
  public static void main(String[] args) {
    // tempo inicial
    long tempoInicial = System.currentTimeMillis();

    long totalSoma = 10000000000L;

    int cores = Runtime.getRuntime().availableProcessors();
    // int cores = 1;

    ArrayList<TrabalhadorSoma> trabalhadores = new ArrayList<TrabalhadorSoma>();

    for (int i = 0; i < cores; i++) {
      TrabalhadorSoma trabalhador = new TrabalhadorSoma((int) (totalSoma / cores));
      trabalhador.start();
      trabalhadores.add(trabalhador);
    }

    for (TrabalhadorSoma trabalhador : trabalhadores) {
      try {
        trabalhador.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // tempo final
    long tempoFinal = System.currentTimeMillis();

    System.out.println("Tempo total: " + (tempoFinal - tempoInicial) + "ms");
    System.out.println("Contador global: " + TrabalhadorSoma.getContadorGlobal());

  }
}
