// 4 - Escreva uma função recursiva que recebe dois números min e max que definem o intervalo. Imprima todos os número pares e ímpares da seguinte forma, por exemplo:
// 	min = 1 e max = 6
// 	Números pares: 2 4 6
// 	Números ímpares: 1 3 5

import java.util.ArrayList;

public class q4 {
  public static void main(String[] args) {
    int min = 1;
    int max = 60;
    ArrayList<Integer> pairs = new ArrayList<>();
    ArrayList<Integer> odds = new ArrayList<>();

    printOddOrPair(min, max, pairs, odds);
  }

  public static void printOddOrPair(int min, int max, ArrayList<Integer> pairs, ArrayList<Integer> odds) {
    if (min <= max) {
      if ((min % 2 == 0) == true) {
        pairs.add(min);
      } else {
        odds.add(min);
      }
      printOddOrPair(min + 1, max, pairs, odds);
    } else {
      System.out.println("min = " + (min - pairs.size() - odds.size()) + " e max = " + max);
      System.out.println("Numeros pares: ");
      for (Integer number : pairs) {
        System.out.print(number + " ");
      }
      System.out.println();

      System.out.println("Numeros impares: ");
      for (Integer number : odds) {
        System.out.println(number + " ");
      }
      System.out.println();
    }
  }
}
