// 1 - Escreva uma função que receba um array (vetor) e imprima seus elementos.
public class q1 {
  public static void main(String[] args) {
    int[] array = { 1, 2, 3, 4, 5 };
    printArray(array, 0);
  }

  public static void printArray(int[] array, int index) {
    if (index < array.length) {
      System.out.println(array[index]);
      printArray(array, index + 1);
    }
  }
}