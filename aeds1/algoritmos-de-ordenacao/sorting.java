import java.util.HashSet;
import java.util.Random;

public class sorting {

  public static void bubbleSort(int[] arr) {
    int n = arr.length;
    boolean swapped;

    for (int i = 0; i < n - 1; i++) {
      swapped = false;

      for (int j = 0; j < n - 1 - i; j++) {
        if (arr[j] > arr[j + 1]) {
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
          swapped = true;
        }
      }
      if (!swapped) {
        break;
      }
    }
  }

  public static void insertionSort(int[] array) {
    int i, aux, j;
    for (j = 1; j < array.length; j++) {
      aux = array[j];
      for (i = j - 1; (i >= 0) && (array[i] > aux); i--) {
        array[i + 1] = array[i];
      }
      array[i + 1] = aux;
    }
  }

  public static void selectionSort(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      int minIndex = i;

      for (int j = minIndex + 1; j < array.length; j++) {
        if (array[j] < array[minIndex]) {
          minIndex = j;
        }
      }
      if (minIndex != i) {
        int temp = array[i];
        array[i] = array[minIndex];
        array[minIndex] = temp;
      }
    }
  }

  public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
      int mid = (left + right) / 2;
      mergeSort(arr, left, mid);
      mergeSort(arr, mid + 1, right);
      merge(arr, left, mid, right);
    }
  }

  public static void merge(int[] arr, int left, int mid, int right) {
    int n1 = mid - left + 1;
    int n2 = right - mid;

    int[] leftArray = new int[n1];
    int[] rightArray = new int[n2];

    for (int i = 0; i < n1; i++) {
      leftArray[i] = arr[left + i];
    }
    for (int i = 0; i < n2; i++) {
      rightArray[i] = arr[mid + 1 + i];
    }

    int i = 0, j = 0, k = left;

    while (i < n1 && j < n2) {
      if (leftArray[i] <= rightArray[j]) {
        arr[k] = leftArray[i];
        i++;
      } else {
        arr[k] = rightArray[j];
        j++;
      }
      k++;
    }

    while (i < n1) {
      arr[k] = leftArray[i];
      i++;
      k++;
    }

    while (j < n2) {
      arr[k] = rightArray[j];
      j++;
      k++;
    }
  }

  public static int[] generateRandomArray(int size) {
    int[] array = new int[size];
    Random random = new Random();
    HashSet<Integer> uniqueValues = new HashSet<>();

    for (int i = 0; i < size; i++) {
      int value;
      do {
        value = random.nextInt(100000); // Valor aleatório entre 0 e 99,999
      } while (uniqueValues.contains(value));

      uniqueValues.add(value);
      array[i] = value;
    }
    return array;
  }

  public static void printArray(int[] array) {
    for (int i = 0; i < array.length; i++) {
      System.out.printf("%d ", array[i]);
    }
    System.out.println();
  }
}