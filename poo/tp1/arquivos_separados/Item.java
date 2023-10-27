package arquivos_separados;
class Item {
  private int id;
  private String nome;
  private float preco;
  private int qtde;

  private static int ultimoID = 0;

  Item(String nome, int qtde, float preco, float desconto) {
    this.id = geraID();
    this.nome = nome;
    this.qtde = qtde;
    this.preco = calculaDesconto(desconto, preco);
  }

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

  public float calculaDesconto(float desconto, float preco) {
    return (preco * (1 - desconto / 100));
  }

  public void alteraPreco(float preco, Pedido pedido) {
    this.setPreco(preco);
    System.out.println("====================================================");
    System.out.println("Preco alterado com sucesso");
    System.out.println("====================================================");
    pausa();
    pedido.imprimirMenu();
  }

  private int geraID() {
    return ++ultimoID;
  }

  public static void pausa() {
    try {
      Thread.sleep(2000);
    } catch (Exception e) {

    }
  }
}
