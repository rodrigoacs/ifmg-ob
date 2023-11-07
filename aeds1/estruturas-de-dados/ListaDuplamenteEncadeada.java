public class ListaDuplamenteEncadeada {
  public static void main(String[] args) {
    ListaDuplamenteEncadeada.DoublyLinkedList<Integer> lista = new ListaDuplamenteEncadeada().new DoublyLinkedList<Integer>();
    lista.addHead(3);
    lista.addHead(43);
    lista.addTail(92);
    lista.addTail(123);
    lista.printLinkedList();

    System.out.println("Removendo 3...");
    lista.removeNode(3);
    lista.printLinkedList();
  }

  public class DoublyLinkedList<E> {

    private DoublyLinkedItem<E> head;
    private DoublyLinkedItem<E> tail;
    private int size;

    public DoublyLinkedList() {
      head = null;
      tail = null;
      size = 0;
    }

    public void addHead(E item) {
      DoublyLinkedItem<E> newItem = new DoublyLinkedItem<>(item);
      if (head == null) {
        head = newItem;
        tail = newItem;
      } else {
        newItem.proximo = head;
        head.anterior = newItem;
        head = newItem;
      }
      size++;
    }

    public void addTail(E item) {
      DoublyLinkedItem<E> newItem = new DoublyLinkedItem<>(item);
      if (tail == null) {
        head = newItem;
        tail = newItem;
      } else {
        newItem.anterior = tail;
        tail.proximo = newItem;
        tail = newItem;
      }
      size++;
    }

    public void removeNode(E item) {
      DoublyLinkedItem<E> current = head;
      while (current != null) {
        if (current.elemento.equals(item)) {
          if (current == head) {
            head = current.proximo;
            if (head != null) {
              head.anterior = null;
            }
          } else if (current == tail) {
            tail = current.anterior;
            if (tail != null) {
              tail.proximo = null;
            }
          } else {
            current.anterior.proximo = current.proximo;
            current.proximo.anterior = current.anterior;
          }
          size--;
          return;
        }
        current = current.proximo;
      }
    }

    public void printLinkedList() {
      DoublyLinkedItem<E> current = head;
      StringBuilder output = new StringBuilder("[");
      boolean firstItem = true;

      while (current != null) {
        if (!firstItem) {
          output.append(", ");
        }
        output.append(current.elemento);
        firstItem = false;
        current = current.proximo;
      }

      output.append("]");
      System.out.println("Imprimindo a Lista Duplamente Encadeada");
      System.out.println(output.toString());
    }

    public int getSize() {
      return size;
    }

    public static class DoublyLinkedItem<T> {
      T elemento;
      DoublyLinkedItem<T> anterior;
      DoublyLinkedItem<T> proximo;

      DoublyLinkedItem(T item) {
        elemento = item;
        anterior = null;
        proximo = null;
      }
    }
  }
}
