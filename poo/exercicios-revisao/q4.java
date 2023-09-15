import java.util.Scanner;

public class q4 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    while (in.hasNextInt()) {
      int n = in.nextInt();
      int m = in.nextInt();
      int[][] matriz = new int[n][m];

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          matriz[i][j] = in.nextInt();
          if (matriz[i][j] == 1) {
            matriz[i][j] = 9;
          }
        }
      }

      int qtdePaes = 0;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          qtdePaes = 0;
          if (matriz[i][j] == 9) {
            System.out.print(matriz[i][j]);
            continue;
          }
          // acima
          if (i != 0 && matriz[i - 1][j] == 9) {
            qtdePaes++;
          }
          // direita
          if (j != m - 1 && matriz[i][j + 1] == 9) {
            qtdePaes++;
          }
          // abaixo
          if (i != n - 1 && matriz[i + 1][j] == 9) {
            qtdePaes++;
          }
          // esquerda
          if (j != 0 && matriz[i][j - 1] == 9) {
            qtdePaes++;
          }
          matriz[i][j] = qtdePaes;
          System.out.print(matriz[i][j]);
        }
        System.out.println();
      }
    }

    in.close();
  }
}