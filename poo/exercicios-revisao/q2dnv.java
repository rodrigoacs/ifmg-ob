import java.util.Scanner;

public class q2dnv {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int mes;
    int dia;
    int[] diasFinais = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 25 };
    int totalDias = 0;

    while (in.hasNextInt()) {
      mes = in.nextInt();
      dia = in.nextInt();

      totalDias = 0;

      for (int i = mes; i < 12; i++) {
        totalDias += diasFinais[i];
      }
      totalDias += (diasFinais[mes - 1] - dia);

      if (totalDias == 0) {
        System.out.println("E natal!");
      } else if (totalDias == 1) {
        System.out.println("E vespera de natal!");
      } else if (totalDias < 0) {
        System.out.println("Ja passou!");
      } else {
        System.out.println("Faltam " + totalDias + " dias para o natal!");
      }
    }
    in.close();
  }
}
