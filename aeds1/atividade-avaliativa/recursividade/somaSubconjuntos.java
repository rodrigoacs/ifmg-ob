import java.util.ArrayList;
import java.util.List;

public class somaSubconjuntos {

  public static void main(String[] args) {
    int[] conjunto = { 3, 5, 1, 8, -4 };
    int valorAlvo = 4;
    System.out.println(existeSubconjunto(conjunto, valorAlvo));
    if (!imprimeSubconjunto(conjunto, valorAlvo)) {
      System.out.println("Não existe subconjunto com a soma igual a " + valorAlvo);
    }
  }

  public static boolean existeSubconjunto(int[] conjunto, int valorAlvo) {
    return verificaSubconjunto(conjunto, valorAlvo, 0);
  }

  private static boolean verificaSubconjunto(int[] conjunto, int valorAlvo, int inicio) {
    if (valorAlvo == 0) {
      return true;
    }
    if (inicio == conjunto.length || valorAlvo < 0) {
      return false;
    }
    return verificaSubconjunto(conjunto, valorAlvo - conjunto[inicio], inicio + 1)
        || verificaSubconjunto(conjunto, valorAlvo, inicio + 1);
  }

  public static boolean imprimeSubconjunto(int[] conjunto, int valorAlvo) {
    return verificaEImprimeSubconjunto(conjunto, valorAlvo, 0, new ArrayList<>());
  }

  private static boolean verificaEImprimeSubconjunto(int[] conjunto, int valorAlvo, int inicio,
      List<Integer> subconjuntoAtual) {
    if (valorAlvo == 0) {
      System.out.println(subconjuntoAtual);
      return true;
    }
    if (inicio == conjunto.length || valorAlvo < 0) {
      return false;
    }

    subconjuntoAtual.add(conjunto[inicio]);
    boolean inclui = verificaEImprimeSubconjunto(conjunto, valorAlvo - conjunto[inicio], inicio + 1, subconjuntoAtual);

    subconjuntoAtual.remove(subconjuntoAtual.size() - 1);
    boolean exclui = verificaEImprimeSubconjunto(conjunto, valorAlvo, inicio + 1, subconjuntoAtual);

    return inclui || exclui;
  }
}
