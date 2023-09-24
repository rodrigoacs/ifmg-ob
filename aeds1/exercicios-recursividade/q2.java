// 2 - Escreva uma função que recebe um array e retorna o maior elemento do array de maneira recursiva. 
public class q2 {
  public static void main(String[] args) {
    int[] array = { 34, 12, 78, 45, 90, 23, 56, 87, 10, 67, 42, 31, 55, 76, 89, 3, 24, 60, 8, 17, 92, 5, 37, 70, 2, 19,
        51, 64, 21, 49, 88, 11, 38, 81, 6, 29, 74, 15, 98, 30, 72, 9, 44, 61, 26, 68, 40, 73, 7, 59, 32, 80, 27, 69, 41,
        84, 14, 77, 20, 53, 96, 35, 63, 1, 58, 85, 18, 71, 25, 48, 86, 16, 52, 95, 28, 66, 43, 99, 4, 57, 36, 65, 22,
        50, 91, 13, 75, 33, 62, 47, 94, 39, 82, 47 };

    System.out.println(largestElement(array, 0));
  }

  public static int largestElement(int[] array, int index) {
    if (index == array.length - 1) {
      return array[index];
    }

    int largest = largestElement(array, index + 1);

    return array[index] > largest ? array[index] : largest;
  }
}
