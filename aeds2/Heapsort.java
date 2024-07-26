public class Heapsort<T extends Comparable<T>> {

  // Função para trocar dois elementos no array
  static <T> void troca(T[] v, int a, int b) {
    T temp = v[a];
    v[a] = v[b];
    v[b] = temp;
  }

  // Função para ajustar o heap a partir da raiz para baixo
  static <T extends Comparable<T>> void refazCimaBaixo(T[] v, int raiz, int fim) {
    int filho = 2 * raiz + 1; // Filho da esquerda

    while (filho <= fim) {
      // Se existir filho à direita e ele for maior que o filho à esquerda
      if (filho < fim && v[filho].compareTo(v[filho + 1]) < 0)
        filho++;

      // Se o filho for maior que o pai, troca-os
      if (v[raiz].compareTo(v[filho]) < 0) {
        System.out.println("Troca: " + v[raiz] + " com " + v[filho]);
        troca(v, raiz, filho);
        raiz = filho;
        filho = 2 * raiz + 1;
      } else {
        break;
      }
    }
  }

  // Função para construir o heap
  static <T extends Comparable<T>> void constroiHeap(T[] v, int n) {
    for (int i = n / 2 - 1; i >= 0; i--) {
      refazCimaBaixo(v, i, n - 1);
    }
  }

  // Função principal de heapsort
  static <T extends Comparable<T>> void heapsort(T[] v, int n) {
    constroiHeap(v, n);

    // Ordena o array
    for (int i = n - 1; i > 0; i--) {
      troca(v, 0, i);
      refazCimaBaixo(v, 0, i - 1);
    }
  }

  public static void main(String[] args) {
    Integer[] array = { 38, 1, 4, 55, 8, 9, 0 };
    int tamanho = array.length;

    System.out.print("Array antes do heapsort: ");
    for (Integer item : array) {
      System.out.print(item + " ");
    }
    System.out.println();

    // Chama o heapsort para ordenar o array
    heapsort(array, tamanho);

    System.out.print("Array ordenado pelo heapsort: ");
    for (Integer item : array) {
      System.out.print(item + " ");
    }
    System.out.println();
  }
}
