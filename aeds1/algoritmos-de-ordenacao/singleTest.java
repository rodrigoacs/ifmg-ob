public class singleTest {
  public static void main(String[] args) {
    int size = 10000;

    System.out.println("Tamanho do array: " + size);
    int[] a = sorting.generateRandomArray(size);
    long startTime = System.currentTimeMillis();
    sorting.selectionSort(a);
    long endTime = System.currentTimeMillis();
    System.out.println("(Selection Sort): " + (endTime - startTime) + " milissegundos");

    int[] b = sorting.generateRandomArray(size);
    startTime = System.currentTimeMillis();
    sorting.insertionSort(b);
    endTime = System.currentTimeMillis();
    System.out.println("(Insertion Sort): " + (endTime - startTime) + " milissegundos");

    int[] c = sorting.generateRandomArray(size);
    startTime = System.currentTimeMillis();
    sorting.bubbleSort(c);
    endTime = System.currentTimeMillis();
    System.out.println("(Bubble Sort): " + (endTime - startTime) + " milissegundos");

    int[] d = sorting.generateRandomArray(size);
    startTime = System.currentTimeMillis();
    sorting.mergeSort(d, 0, d.length - 1);
    endTime = System.currentTimeMillis();
    System.out.println("(Merge Sort): " + (endTime - startTime) + " milissegundos\n");

  }
}
