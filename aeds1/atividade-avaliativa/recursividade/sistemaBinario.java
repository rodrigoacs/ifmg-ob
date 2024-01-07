import java.util.ArrayList;
import java.util.List;

public class sistemaBinario {

  public static void main(String[] args) {
    int nBits = 3;
    List<String> binarios = gerarNumBinario(nBits);
    for (String binario : binarios) {
      System.out.println(binario + "\t" + Integer.parseInt(binario, 2));
    }
  }

  public static List<String> gerarNumBinario(int nBits) {
    List<String> result = new ArrayList<>();
    gerarNumBinarioRecursivo("", nBits, result);
    return result;
  }

  private static void gerarNumBinarioRecursivo(String prefix, int nBits, List<String> result) {
    if (nBits == 0) {
      result.add(prefix);
    } else {
      gerarNumBinarioRecursivo(prefix + "0", nBits - 1, result);
      gerarNumBinarioRecursivo(prefix + "1", nBits - 1, result);
    }
  }
}
