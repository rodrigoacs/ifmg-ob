import java.util.Scanner;

public class q2 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    int x, y;

    while (in.hasNextInt()) {
      x = in.nextInt();
      y = in.nextInt();

      if (x == 0 || y == 0) {
        break;
      }
      if (x > 0 && y > 0) {
        System.out.println("Primeiro");
      } else if (x < 0 && y > 0) {
        System.out.println("Segundo");
      } else if (x < 0 && y < 0) {
        System.out.println("Terceiro");
      } else if (x > 0 && y < 0) {
        System.out.println("Quarto");
      }
    }

    in.close();
  }
}
