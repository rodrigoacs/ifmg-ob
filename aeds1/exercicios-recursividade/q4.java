// 4 - Escreva uma função recursiva que recebe dois números min e max que definem o intervalo. Imprima todos os número pares e ímpares da seguinte forma, por exemplo:
// 	min = 1 e max = 6
// 	Números pares: 2 4 6
// 	Números ímpares: 1 3 5
public class q4 {
  public static void main(String[] args) {
    int min = 1;
    int max = 60;

    System.out.print("Numeros pares: ");
    printOddOrPair(min, max, true);
    System.out.println();

    System.out.print("Numeros impares: ");
    printOddOrPair(min, max, false);
    System.out.println();
  }

  public static void printOddOrPair(int min, int max, boolean isPair) {
    if (min <= max) {
      if ((min % 2 == 0) == isPair) {
        System.out.print(min + " ");
      }
      printOddOrPair(min + 1, max, isPair);
    }
  }
}
