public class ListaEncadeada {

  public static void main(String[] args) {
    ListaEncadeada.LinkedList<Integer> lista = new ListaEncadeada().new LinkedList<Integer>();
    lista.addTail(2);
    lista.addTail(33);
    lista.addTail(99);
    lista.addTail(1000);
    System.out.println("A lista possui: " + lista.countNodes(lista.getLista()) + " nó(s)");
    System.out.println(lista.removeNode(33));

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

    public int countNodes(Item<E> head) {
      int count = 0;
      Item<E> current = head;
      while (current != null) {
        count++;
        current = current.proximo;
      }
      return count;
    }

    public E removeNode(E item) {
      System.out.println("Removendo o elemento " + item + " da lista");
      if (lista == null) {
        return null;
      }

      if (lista.elemento.equals(item)) {
        lista = lista.proximo;
        size--;
        return (lista != null) ? lista.elemento : null;
      }

      Item<E> ponteiroAnterior = lista;
      Item<E> ponteiro = lista.proximo;

      while (ponteiro != null) {
        if (ponteiro.elemento.equals(item)) {
          ponteiroAnterior.proximo = ponteiro.proximo;
          size--;
          return (ponteiro.proximo != null) ? ponteiro.proximo.elemento : null;
        }
        ponteiroAnterior = ponteiro;
        ponteiro = ponteiro.proximo;
      }
      return null;
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

    public class Item<T> {
      T elemento;
      Item<T> proximo;

      Item(T item, Item<T> prox) {
        elemento = item;
        proximo = prox;
      }
    }
  }
}
