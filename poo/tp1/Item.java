public class Item {
  private int id;
  private String nome;
  private float preco;
  private int qtde;

  private static int ultimoID = 0;

  Item(String nome, float preco, int qtde) {
    this.id = geraID();
    this.nome = nome;
    this.preco = preco;
    this.qtde = qtde;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public float getPreco() {
    return preco;
  }

  public void setPreco(float preco) {
    this.preco = preco;
  }

  public int getQtde() {
    return qtde;
  }

  public void setQtde(int qtde) {
    this.qtde = qtde;
  }

  public double calculaTotal() {
    return this.qtde * this.preco;
  }

  private int geraID() {
    return ++ultimoID;
  }
}
