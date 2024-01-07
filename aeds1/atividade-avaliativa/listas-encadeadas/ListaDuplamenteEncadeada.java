public class ListaDuplamenteEncadeada {
  public static void main(String[] args) {
    ListaDuplamenteEncadeada.DoublyLinkedList<Integer> lista = new ListaDuplamenteEncadeada().new DoublyLinkedList<Integer>();
    lista.addTail(3);
    lista.addTail(43);
    lista.addTail(92);
    lista.addTail(123);

    Integer ithElement = lista.getIthNode(lista.getHead(), 3);
    if (ithElement != null) {
      System.out.println(ithElement);
    } else {
      System.out.println("Nó não encontrado.");
    }

    Integer removedElement = lista.removeAt(2);
    if (removedElement != null) {
      System.out.println("Elemento removido: " + removedElement);
    } else {
      System.out.println("Nenhum elemento removido. Índice inválido ou lista vazia.");
    }
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

    public DoublyLinkedItem<E> getHead() {
      return head;
    }

    public E getIthNode(DoublyLinkedItem<E> head, int i) {
      int count = 0;
      DoublyLinkedItem<E> current = head;
      while (current != null && count < i) {
        count++;
        current = current.proximo;
      }
      return (current != null) ? current.elemento : null;
    }

    public E removeAt(int i) {
      if (head == null || i < 0 || i >= size) {
        return null;
      }

      DoublyLinkedItem<E> current = head;
      int count = 0;

      while (current != null && count < i) {
        current = current.proximo;
        count++;
      }

      if (current == null) {
        return null;
      }

      if (current == head) {
        head = current.proximo;
        if (head != null) {
          head.anterior = null;
        }
      } else if (current == tail) {
        tail = current.anterior;
        tail.proximo = null;
      } else {
        current.anterior.proximo = current.proximo;
        if (current.proximo != null) {
          current.proximo.anterior = current.anterior;
        }
      }

      size--;
      return current.elemento;
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

    public class DoublyLinkedItem<T> {
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
