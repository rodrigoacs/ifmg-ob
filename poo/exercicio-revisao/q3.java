import java.util.Scanner;

public class q3 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    while (in.hasNext()) {
      String[] letrasArray = in.next().split("");
      int n = in.nextInt();

      for (int i = 0; i < n; i++) {
        int posicao = in.nextInt();
        System.out.print(letrasArray[posicao - 1]);
      }
      System.out.println();
    }

    in.close();
  }
}
