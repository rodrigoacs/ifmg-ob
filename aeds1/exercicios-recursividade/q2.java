// 2 - Escreva uma função que recebe um array e retorna o maior elemento do array de maneira recursiva. 

import java.util.HashSet;
import java.util.Random;

public class q2 {
  public static void main(String[] args) {
    int size = 100;

    int[] array = generateRandomArray(size);

    long startTime = System.currentTimeMillis();
    System.out.println(largestElementMath(array, 0));
    long endTime = System.currentTimeMillis();
    System.out.println("(largestElementMath): " + (endTime - startTime) + " milissegundos com " + size + " elementos");

    startTime = System.currentTimeMillis();
    System.out.println(largestElementTern(array, 0));
    endTime = System.currentTimeMillis();
    System.out.println("(largestElementTern): " + (endTime - startTime) + " milissegundos com " + size + " elementos");
  }

  public static int[] generateRandomArray(int size) {
    int[] array = new int[size];
    Random random = new Random();
    HashSet<Integer> uniqueValues = new HashSet<>();

    for (int i = 0; i < size; i++) {
      int value;
      do {
        value = random.nextInt(size);
      } while (uniqueValues.contains(value));

      uniqueValues.add(value);
      array[i] = value;
    }
    return array;
  }

  public static int largestElementMath(int[] array, int index) {
    if (index == array.length - 1) {
      return array[index];
    }
    return Math.max(array[index], largestElementMath(array, index + 1));
  }

  public static int largestElementTern(int[] array, int index) {
    if (index == array.length - 1) {
      return array[index];
    }

    int largest = largestElementTern(array, index + 1);

    return array[index] > largest ? array[index] : largest;
  }
}
