public class multipleTest {

  public static void main(String[] args) {
    int size = 0, forSize = 10;
    int[] selectionSortArray = new int[forSize];
    int[] insertionSortArray = new int[forSize];
    int[] mergeSortArray = new int[forSize];
    int[] bubbleSortArray = new int[forSize];
    int[] quickSortArray = new int[forSize];

    for (int i = 0; i < forSize; i++) {
      size += 10000;
      int[] a = sorting.generateRandomArray(size);
      long startTime = System.currentTimeMillis();
      sorting.selectionSort(a);
      long endTime = System.currentTimeMillis();
      selectionSortArray[i] = (int) (endTime - startTime);

      int[] b = sorting.generateRandomArray(size);
      startTime = System.currentTimeMillis();
      sorting.insertionSort(b);
      endTime = System.currentTimeMillis();
      insertionSortArray[i] = (int) (endTime - startTime);

      int[] c = sorting.generateRandomArray(size);
      startTime = System.currentTimeMillis();
      sorting.bubbleSort(c);
      endTime = System.currentTimeMillis();
      bubbleSortArray[i] = (int) (endTime - startTime);

      int[] d = sorting.generateRandomArray(size);
      startTime = System.currentTimeMillis();
      sorting.mergeSort(d, 0, d.length - 1);
      endTime = System.currentTimeMillis();
      mergeSortArray[i] = (int) (endTime - startTime);

      int[] e = sorting.generateRandomArray(size);
      startTime = System.currentTimeMillis();
      sorting.quickSort(e, 0, e.length - 1);
      endTime = System.currentTimeMillis();
      quickSortArray[i] = (int) (endTime - startTime);

    }

    System.out.println("Selection Sort:");
    sorting.printArray(selectionSortArray);
    System.out.println("Insertion Sort:");
    sorting.printArray(insertionSortArray);
    System.out.println("Bubble Sort:");
    sorting.printArray(bubbleSortArray);
    System.out.println("Merge Sort:");
    sorting.printArray(mergeSortArray);
    System.out.println("Quick Sort:");
    sorting.printArray(quickSortArray);

  }
}
