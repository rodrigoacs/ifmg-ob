import java.util.Random;

public class sortingLib {
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
      int smaller = i;

      for (int j = smaller + 1; j < array.length; j++) {
        if (array[j] < array[smaller]) {
          smaller = j;
        }
      }
      if (smaller != i) {
        int t = array[i];
        array[i] = array[smaller];
        array[smaller] = t;
      }
    }
  }

  public static void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
      int middle = (left + right) / 2;
      mergeSort(arr, left, middle);
      mergeSort(arr, middle + 1, right);
      merge(arr, left, middle, right);
    }
  }

  public static void merge(int[] arr, int left, int middle, int right) {
    int n1 = middle - left + 1;
    int n2 = right - middle;

    int[] leftArray = new int[n1];
    int[] rightArray = new int[n2];

    for (int i = 0; i < n1; i++) {
      leftArray[i] = arr[left + i];
    }
    for (int i = 0; i < n2; i++) {
      rightArray[i] = arr[middle + 1 + i];
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
    for (int i = 0; i < size; i++) {
      array[i] = random.nextInt(100000);
    }
    return array;
  }

  public static void printArray(int[] array) {
    for (int i = 0; i < array.length; i++) {
      System.out.printf("%d ", array[i]);
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int size = 0, forSize = 10;
    // int[] selectionSortArray = new int[forSize];
    // int[] insertionSortArray = new int[forSize];
    // int[] mergeSortArray = new int[forSize];

    for (int i = 0; i < forSize; i++) {
      size += 10000;
      System.out.println("Tamanho do array: " + size);
      int[] a = generateRandomArray(size);
      long startTime = System.currentTimeMillis();
      selectionSort(a);
      long endTime = System.currentTimeMillis();
      // selectionSortArray[i] = (int) (endTime - startTime);
      System.out.println("(Selection Sort): " + (endTime - startTime) + " milissegundos");

      int[] b = generateRandomArray(size);
      startTime = System.currentTimeMillis();
      insertionSort(b);
      endTime = System.currentTimeMillis();
      // insertionSortArray[i] = (int) (endTime - startTime);
      System.out.println("(Insertion Sort): " + (endTime - startTime) + " milissegundos");

      int[] c = generateRandomArray(size);
      startTime = System.currentTimeMillis();
      mergeSort(c, 0, c.length - 1);
      endTime = System.currentTimeMillis();
      // mergeSortArray[i] = (int) (endTime - startTime);
      System.out.println("(Merge Sort): " + (endTime - startTime) + " milissegundos\n");
    }

    // System.out.println("Selection Sort:");
    // printArray(selectionSortArray);
    // System.out.println("Insertion Sort:");
    // printArray(insertionSortArray);
    // System.out.println("Merge Sort:");
    // printArray(mergeSortArray);

  }

}