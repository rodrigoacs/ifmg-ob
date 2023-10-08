public class ListaLinearEstatica {

  public static void main(String[] args) {
    Lista lista = new Lista();

    Aluno aluno1 = new Aluno("Joao", 12345);
    Aluno aluno2 = new Aluno("Maria", 67890);
    Aluno aluno3 = new Aluno("Pedro", 54321);
    Aluno aluno4 = new Aluno("Ana", 98765);

    lista.insereAlunoIndice(aluno1, 0);
    lista.insereAlunoIndice(aluno2, 1);
    lista.insereAlunoIndice(aluno3, 2);
    lista.insereAlunoIndice(aluno4, 1);

    lista.imprimeLista();

    System.out.println(lista.cheia() ? "esta cheia" : "nao esta cheia");

    lista.remover(0);

    lista.imprimeLista();

    System.out.println("tamanho da lista " + lista.getnAtualAlunos());

    lista.apagarLista();

    System.out.println(lista.vazia() ? "esta vazia" : "nao esta vazia");
  }

}

class Lista {
  private final int MAX = 100;
  private int nAtualAlunos;
  private Aluno[] lista;

  public int getnAtualAlunos() {
    return nAtualAlunos;
  }

  public Lista() {
    lista = new Aluno[MAX];
    nAtualAlunos = 0;
  }

  /* Retorna true se lista vazia, false caso contrário */
  public boolean vazia() {
    return this.nAtualAlunos == 0;
  }

  /* Retorna true se lista cheia, false caso contrário */
  public boolean cheia() {
    return this.nAtualAlunos == this.MAX;
  }

  public void apagarLista() {
    this.nAtualAlunos = 0;
  }

  /*
   * Insere novo elemento, x, na posição p da Lista.
   * Se L = a1, a2,... an então temos a1, a2,...ap-1 x ap+1 ... an.
   * Devolve true se sucesso, false c.c. (L não tem nenhuma posição p
   * ou Lista cheia). Operação para LISTA NÃO ORDENADA!
   */

  public boolean insereAlunoIndice(Aluno aluno, int indice) {
    if (this.cheia() || indice < 0 || indice > this.nAtualAlunos)
      return false;

    for (int i = nAtualAlunos; i > indice; i--) {
      lista[i] = lista[i - 1];
    }
    lista[indice] = aluno;
    nAtualAlunos++;

    return true;
  }

  public void imprimeLista() {
    if (vazia())
      return;

    for (int i = 0; i < nAtualAlunos; i++) {
      Aluno aluno = lista[i];
      System.out.println(aluno.getNome() + " " + aluno.getCpf());
    }
  }

  public boolean remover(int indice) {
    if (indice >= 0 && indice < nAtualAlunos) {
      for (int i = indice; i < nAtualAlunos - 1; i++) {
        lista[i] = lista[i + 1];
      }
      lista[nAtualAlunos - 1] = null;
      nAtualAlunos--;
      return true;
    }
    return false;
  }
}

class Aluno {

  private String nome;
  private int cpf;

  Aluno(String _nome, int _cpf) {
    nome = _nome;
    cpf = _cpf;
  }

  public int getCpf() {
    return cpf;
  }

  public String getNome() {
    return nome;
  }
}