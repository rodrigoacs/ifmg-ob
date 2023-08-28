public class insertionSort {
  public static void main(String[] args) {
    int[] vetor = { 5, 3, 2, 4, 7, 1, 0, 6 };

    for (int i = 0; i < vetor.length; i++) {
      int aux = vetor[i];
      int j = i;
      while ((j > 0) && (vetor[j - 1] > aux)) {
        vetor[j] = vetor[j - 1];
        j -= 1;
      }
      vetor[j] = aux;
    }

    for (int i = 0; i < vetor.length; i++) {
      System.out.print(vetor[i] + " ");
    }
  }
}
