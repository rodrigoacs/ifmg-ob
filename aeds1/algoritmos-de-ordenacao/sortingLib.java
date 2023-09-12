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

  public static void arrayPrint(int[] array) {
    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.print("\n");
  }

  public static void main(String[] args) {
    int[] a = { 2, 4, 3, 7, 0, 6, 5, 9, 1, 8 };
    arrayPrint(a);
    selectionSort(a);
    arrayPrint(a);

    int[] b = { 1, 8, 5, 4, 2, 0, 6, 7, 3, 9 };
    arrayPrint(b);
    insertionSort(b);
    arrayPrint(b);
  }
}