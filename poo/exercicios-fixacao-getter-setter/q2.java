public class q2 {
  public static void main(String[] args) {
    Item item = new Item();
    item.setCodigo(1);
    item.setDescricao("Item 1");
    item.setQuantidade(10);
    item.setPreco(2.5);
    System.out.println("Total: " + item.getTotal());

  }

  static class Item {
    int codigo;
    String descricao;
    int quantidade;
    double preco;

    public int getCodigo() {
      return this.codigo;
    }

    public void setCodigo(int codigo) {
      this.codigo = codigo;
    }

    public String getDescricao() {
      return this.descricao;
    }

    public void setDescricao(String descricao) {
      this.descricao = descricao;
    }

    public int getQuantidade() {
      return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
      if (quantidade < 0) {
        this.quantidade = 0;
      } else {
        this.quantidade = quantidade;
      }
    }

    public double getPreco() {
      return this.preco;
    }

    public void setPreco(double preco) {
      if (preco < 0) {
        this.preco = 0;
      } else {
        this.preco = preco;
      }
    }

    public double getTotal() {
      return this.preco * this.quantidade;
    }
  }
}
