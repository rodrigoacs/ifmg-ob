import java.util.HashSet;
import java.util.Random;

public class ordenacao {
  public static void main(String[] args) {
    // a saída do código segue um modelo de csv
    // para facilitar a importação no
    // excel para criação dos gráficos
    int size = 10000;
    int growthRate = 10000;
    int tests = 10;

    System.out.println("tamanho;selection;insertion;merge;quick");
    for (int i = 0; i < tests; i++) {
      System.out.print(size + ";");
      int[] arrayBase = generateRandomArray(size);
      long startTime, endTime;

      int[] selectionArray = arrayBase.clone();
      startTime = System.currentTimeMillis();
      selectionSort(selectionArray);
      endTime = System.currentTimeMillis();
      System.out.print((endTime - startTime) + ";");

      int[] insertionArray = arrayBase.clone();
      startTime = System.currentTimeMillis();
      insertionSort(insertionArray);
      endTime = System.currentTimeMillis();
      System.out.print((endTime - startTime) + ";");

      int[] mergeArray = arrayBase.clone();
      startTime = System.currentTimeMillis();
      mergeSort(mergeArray, 0, mergeArray.length - 1);
      endTime = System.currentTimeMillis();
      System.out.print((endTime - startTime) + ";");

      int[] quickArray = arrayBase.clone();
      startTime = System.currentTimeMillis();
      quickSort(quickArray, 0, quickArray.length - 1);
      endTime = System.currentTimeMillis();
      System.out.print((endTime - startTime) + ";");
      size += growthRate;
      System.out.println();
    }
  }

  public static void quickSort(int[] values, int left, int right) {
    while (left < right) {
      int indexPivot = partition(values, left, right);
      if (indexPivot - left < right - indexPivot) {
        quickSort(values, left, indexPivot - 1);
        left = indexPivot + 1;
      } else {
        quickSort(values, indexPivot + 1, right);
        right = indexPivot - 1;
      }
    }
  }

  public static int partition(int[] values, int left, int right) {
    int pivot = values[left];
    int i = left;
    for (int j = left + 1; j <= right; j++) {
      if (values[j] <= pivot) {
        i++;
        swap(values, i, j);
      }
    }
    swap(values, left, i);
    return i;
  }

  public static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  public static void merge(int[] array, int left, int middle, int right) {
    int n1 = middle - left + 1;
    int n2 = right - middle;

    int[] leftArray = new int[n1];
    int[] rightArray = new int[n2];

    for (int i = 0; i < n1; i++) {
      leftArray[i] = array[left + i];
    }
    for (int i = 0; i < n2; i++) {
      rightArray[i] = array[middle + 1 + i];
    }

    int i = 0, j = 0, k = left;

    while (i < n1 && j < n2) {
      if (leftArray[i] <= rightArray[j]) {
        array[k++] = leftArray[i++];
      } else {
        array[k++] = rightArray[j++];
      }
    }

    while (i < n1) {
      array[k++] = leftArray[i++];
    }

    while (j < n2) {
      array[k++] = rightArray[j++];
    }
  }

  public static void mergeSort(int[] array, int left, int right) {
    if (left < right) {
      int middle = left + (right - left) / 2;
      mergeSort(array, left, middle);
      mergeSort(array, middle + 1, right);
      merge(array, left, middle, right);
    }
  }

  public static void insertionSort(int[] array) {
    int n = array.length;
    for (int i = 1; i < n; i++) {
      int key = array[i];
      int j = i - 1;

      while (j >= 0 && array[j] > key) {
        array[j + 1] = array[j];
        j--;
      }

      array[j + 1] = key;
    }
  }

  public static void selectionSort(int[] array) {
    int n = array.length;
    for (int i = 0; i < n - 1; i++) {
      int minIndex = i;
      for (int j = i + 1; j < n; j++) {
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
}