import java.util.ArrayList;
import java.util.List;

public class q1 {
  public static void main(String[] args) {
    int n = 5;
    decompose(n, new ArrayList<>(), n);
  }

  public static void decompose(int n, List<Integer> decomposition, int max) {
    if (n == 0) {
      printList(decomposition);
      return;
    }

    for (int i = Math.min(n, max); i > 0; i--) {
      decomposition.add(i);
      decompose(n - i, decomposition, i);
      decomposition.remove(decomposition.size() - 1);
    }
  }

  public static void printList(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i));
      if (i < list.size() - 1) {
        System.out.print(" + ");
      }
    }
    System.out.println();
  }
}
