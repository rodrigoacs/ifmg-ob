// 2 - Escreva uma função que recebe um array e retorna o maior elemento do array de maneira recursiva. 
public class q2 {
  public static void main(String[] args) {
    int[] array = { 5, 4, 3, 2, 1, 9, 7, 8, 6 };
    System.out.println(largestElement(array, 0));
  }

  public static int largestElement(int[] array, int index) {
    if (index == array.length - 1) {
      return array[index];
    }
    return Math.max(array[index], largestElement(array, index + 1));
  }
}
