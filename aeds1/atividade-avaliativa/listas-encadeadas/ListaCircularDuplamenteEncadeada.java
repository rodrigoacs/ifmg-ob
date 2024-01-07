public class ListaCircularDuplamenteEncadeada<E> {

  private DoublyLinkedItem<E> head;
  private int size;

  public ListaCircularDuplamenteEncadeada() {
    this.head = null;
    this.size = 0;
  }

  public void add(E elemento) {
    DoublyLinkedItem<E> novoItem = new DoublyLinkedItem<>(elemento);
    if (head == null) {
      head = novoItem;
      novoItem.proximo = novoItem;
      novoItem.anterior = novoItem;
    } else {
      DoublyLinkedItem<E> tail = head.anterior;
      tail.proximo = novoItem;
      novoItem.anterior = tail;
      novoItem.proximo = head;
      head.anterior = novoItem;
    }
    size++;
  }

  public void printList() {
    if (head == null) {
      System.out.println("A lista está vazia.");
      return;
    }

    DoublyLinkedItem<E> current = head;
    do {
      System.out.print(current.elemento + " ");
      current = current.proximo;
    } while (current != head);
    System.out.println();
  }

  public class DoublyLinkedItem<T> {
    T elemento;
    DoublyLinkedItem<T> anterior, proximo;

    DoublyLinkedItem(T elemento) {
      this.elemento = elemento;
      this.anterior = this;
      this.proximo = this;
    }
  }

  public static void main(String[] args) {
    ListaCircularDuplamenteEncadeada<Integer> lista = new ListaCircularDuplamenteEncadeada<>();
    lista.add(1);
    lista.add(2);
    lista.add(3);

    System.out.println("Elementos da lista:");
    lista.printList();
  }
}
