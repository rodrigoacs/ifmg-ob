import java.util.ArrayList;
import java.util.Scanner;

public class OrdemDeServico {
  static Scanner in = new Scanner(System.in);

  private ArrayList<Item> itens = new ArrayList<>();
  private Carro carro;
  private float total;

  public Carro getCarro() {
    return carro;
  }

  public void setCarro(Carro carro) {
    this.carro = carro;
  }

  public float getTotal() {
    this.total = calculaTotal();
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  private float calculaTotal() {
    float soma = 0;
    for (Item item : itens) {
      soma += item.calculaTotal();
    }
    return soma;
  }

  private void imprimeItens() {
    System.out.println("==================================================");
    System.out.println("Itens:");
    System.out.println("==================================================");
    System.out.println("ID  | Nome                | Preco   | Qtde | Total");
    for (Item item : itens) {
      System.out.printf("%-4d| %-20s| R$%-6.2f| %-5d| R$%-6.2f%n",
          item.getId(), item.getNome(), item.getPreco(), item.getQtde(), item.calculaTotal());
    }
  }

  public void imprimirOS() {
    System.out.println("==================================================");
    System.out.println("Ordem de Servico:");
    System.out.println("==================================================");
    System.out.println("Marca: " + this.getCarro().getMarca());
    System.out.println("Modelo: " + this.getCarro().getModelo());
    System.out.println("Cor: " + this.getCarro().getCor());
    System.out.println("Placa: " + this.getCarro().getPlaca());
    imprimeItens();
    System.out.println("==================================================");
    System.out.println("Total da Ordem de Servico: R$" + this.getTotal());
    System.out.println("==================================================");
  }

  public boolean removerItem() {
    System.out.println();
    imprimeItens();
    System.out.println("informe o id do item");
    int id = Integer.parseInt(in.nextLine());
    System.out.println("informe a quantidade do item");
    int qtde = Integer.parseInt(in.nextLine());
    for (Item item : itens) {
      if (item.getId() == id) {
        if (item.getQtde() > qtde) {
          item.setQtde(item.getQtde() - qtde);
        } else {
          itens.remove(item);
        }
        return true;
      }
    }
    return false;
  }

  public void adicionarItem() {
    System.out.println();
    System.out.println("informe o nome do item");
    String nome = in.nextLine();
    System.out.println("informe o preco do item");
    float preco = Float.parseFloat(in.nextLine());
    System.out.println("informe a quantidade do item");
    int qtde = Integer.parseInt(in.nextLine());

    Item item = new Item(nome, preco, qtde);

    this.itens.add(item);
  }

  public void adicionarCarro() {
    System.out.println();
    System.out.println("informe a placa do carro");
    String placa = in.nextLine();
    System.out.println("informe a cor do carro");
    String cor = in.nextLine();
    System.out.println("informe a marca do carro");
    String marca = in.nextLine();
    System.out.println("informe o modelo do carro");
    String modelo = in.nextLine();

    Carro carro = new Carro(modelo, placa, cor, marca);

    this.setCarro(carro);
  }
}
