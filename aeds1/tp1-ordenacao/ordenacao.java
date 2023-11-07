import java.util.HashSet;
import java.util.Random;

public class ordenacao {
  public static void main(String[] args) {
    int[] sizes = { 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000 };
    String[] scenarios = { "Aleatorio", "Ordenado Crescente", "Ordenado Decrescente" };
    String[] algorithms = { "Selection", "Insertion", "Merge", "Quick" };

    System.out.println("Teste de desempenho dos algoritmos de ordenacao");
    System.out.println("Cenários: Aleatório, Ordenado Crescente e Ordenado Decrescente");
    System.out.println("Entradas: 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000");
    System.out.println("Algoritmos: Selection, Insertion, Merge, Quick");

    for (String scenario : scenarios) {
      System.out.println(scenario);
      System.out.println("Tamanho;Selection;Insertion;Merge;Quick");
      for (int size : sizes) {
        System.out.print(size + ";");
        int[] arrayBase = {};
        switch (scenario) {
          case "Aleatorio":
            arrayBase = generateRandomArray(size);
            break;
          case "Ordenado Crescente":
            arrayBase = generateSortedArray(size);
            break;
          case "Ordenado Decrescente":
            arrayBase = generateReverseSortedArray(size);
            break;
        }
        for (String algorithm : algorithms) {
          int[] array = arrayBase.clone();
          long startTime = System.currentTimeMillis();
          switch (algorithm) {
            case "Selection":
              selectionSort(array);
              break;
            case "Insertion":
              insertionSort(array);
              break;
            case "Merge":
              mergeSort(array, 0, array.length - 1);
              break;
            case "Quick":
              quickSort(array, 0, array.length - 1);
              break;
          }
          long endTime = System.currentTimeMillis();
          System.out.print((endTime - startTime) + ";");
        }
        System.out.println();
      }
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

  public static int[] generateSortedArray(int size) {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = i;
    }
    return array;
  }

  public static int[] generateReverseSortedArray(int size) {
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = size - i;
    }
    return array;
  }
}