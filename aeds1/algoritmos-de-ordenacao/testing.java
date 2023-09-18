public class testing {
  public static void main(String[] args) {
    int size = 0, forSize = 1;
    // int[] selectionSortArray = new int[forSize];
    // int[] insertionSortArray = new int[forSize];
    // int[] mergeSortArray = new int[forSize];
    // int[] bubbleSortArray = new int[forSize];

    for (int i = 0; i < forSize; i++) {
      size += 100000;
      System.out.println("Tamanho do array: " + size);
      int[] a = sorting.generateRandomArray(size);
      long startTime = System.currentTimeMillis();
      sorting.selectionSort(a);
      long endTime = System.currentTimeMillis();
      // selectionSortArray[i] = (int) (endTime - startTime);
      System.out.println("(Selection Sort): " + (endTime - startTime) + " milissegundos");

      int[] b = sorting.generateRandomArray(size);
      startTime = System.currentTimeMillis();
      sorting.insertionSort(b);
      endTime = System.currentTimeMillis();
      // insertionSortArray[i] = (int) (endTime - startTime);
      System.out.println("(Insertion Sort): " + (endTime - startTime) + " milissegundos");

      int[] c = sorting.generateRandomArray(size);
      startTime = System.currentTimeMillis();
      sorting.bubbleSort(c);
      endTime = System.currentTimeMillis();
      // bubbleSortArray[i] = (int) (endTime - startTime);
      System.out.println("(Bubble Sort): " + (endTime - startTime) + " milissegundos");

      int[] d = sorting.generateRandomArray(size);
      startTime = System.currentTimeMillis();
      sorting.mergeSort(d, 0, d.length - 1);
      endTime = System.currentTimeMillis();
      // mergeSortArray[i] = (int) (endTime - startTime);
      System.out.println("(Merge Sort): " + (endTime - startTime) + " milissegundos\n");

    }

    // System.out.println("Selection Sort:");
    // printArray(selectionSortArray);
    // System.out.println("Insertion Sort:");
    // printArray(insertionSortArray);
    // System.out.println("Bubble Sort:");
    // printArray(bubbleSortArray);
    // System.out.println("Merge Sort:");
    // printArray(mergeSortArray);

  }
}
