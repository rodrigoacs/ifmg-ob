public class ListaEncadeada {

  public static void main(String[] args) {
    ListaEncadeada.LinkedList<Integer> listaA = new ListaEncadeada().new LinkedList<Integer>();

    // Adicionando elementos na cabeça da lista
    listaA.addHead(2);
    listaA.addHead(33);
    listaA.addTail(99);
    listaA.addTail(1000);
    System.out.println("ListaA:");
    listaA.printLinkedList();

    System.out.println("\nChecando se um elemento existe na listaA");
    boolean exist = listaA.elementExist(99);
    System.out.println("O elemento 99 existe? " + exist);
    exist = listaA.elementExist(100);
    System.out.println("O elemento 100 existe? " + exist);

    // merge de lista
    ListaEncadeada.LinkedList<Integer> listaB = new ListaEncadeada().new LinkedList<Integer>();
    listaB.addHead(1);
    listaB.addTail(3);
    listaB.addTail(50);
    listaB.addTail(60);

    System.out.println("\nListaB:");
    listaB.printLinkedList();

    System.out.println("\nChecando se duas listas sao iguais");
    boolean equals = listaA.equals(listaB.getLista());
    System.out.println("As listas sao iguais? " + equals);

    System.out.println("\nMerge de listas");
    listaA.mergeLists(listaB.getLista());
    listaA.printLinkedList();

    System.out.println("\nRemovendo um elemento da lista");
    listaA.removeNode(99);
    listaA.printLinkedList();
  }

  class LinkedList<E> {
    private Item<E> lista;
    private int size;

    public LinkedList() {
      lista = null;
    }

    public Item<E> getLista() {
      return lista;
    }

    public int getSize() {
      return size;
    }

    public void removeNode(E item) {
      System.out.println("Removendo o elemento " + item + " da lista");
      if (lista == null) {
        return;
      }

      if (lista.elemento.equals(item)) {
        lista = lista.proximo;
        size--;
        return;
      }

      Item<E> ponteiroAnterior = lista;
      Item<E> ponteiro = lista.proximo;

      while (ponteiro != null) {
        if (ponteiro.elemento.equals(item)) {
          ponteiroAnterior.proximo = ponteiro.proximo;
          size--;
          return;
        }
        ponteiroAnterior = ponteiro;
        ponteiro = ponteiro.proximo;
      }
    }

    public boolean equals(Item<E> listaA) {
      Item<E> ponteiro = lista;
      Item<E> ponteiroA = listaA;
      for (; ponteiro != null && ponteiroA != null; ponteiro = ponteiro.proximo, ponteiroA = ponteiroA.proximo) {
        if (ponteiro.elemento != ponteiroA.elemento) {
          return false;
        }
      }
      return true;
    }

    public void mergeLists(Item<E> listaA) {
      Item<E> ponteiro = lista;
      for (; ponteiro.proximo != null; ponteiro = ponteiro.proximo) {
      }
      ponteiro.proximo = listaA;
    }

    public void addHead(E item) {
      if (lista == null) {
        lista = new Item<E>(item, null);
      } else {
        Item<E> novoItem = new Item<E>(item, lista);
        lista = novoItem;
      }
      size++;
    }

    public void addTail(E item) {
      if (lista == null) {
        lista = new Item<E>(item, null);
      } else {
        Item<E> ponteiro = lista;
        for (; ponteiro.proximo != null; ponteiro = ponteiro.proximo) {
        }
        ponteiro.proximo = new Item<E>(item, null);
      }
      size++;
    }

    public boolean elementExist(E item) {
      if (lista == null)
        return false;
      Item<E> ponteiro = lista;
      do {
        if (ponteiro.elemento == item)
          return true;
        ponteiro = ponteiro.proximo;
      } while (ponteiro != null);
      return false;
    }

    public void printLinkedList() {
      Item<E> ponteiro = lista;
      StringBuilder output = new StringBuilder("[");
      boolean firstItem = true;

      while (ponteiro != null) {
        if (!firstItem) {
          output.append(", ");
        }
        output.append(ponteiro.elemento);
        firstItem = false;
        ponteiro = ponteiro.proximo;
      }

      output.append("]");
      System.out.println("Imprimindo a Lista encadeada");
      System.out.println(output.toString());
    }

    public static class Item<T> {
      T elemento;
      Item<T> proximo;

      Item(T item, Item<T> prox) {
        elemento = item;
        proximo = prox;
      }
    }
  }
}
