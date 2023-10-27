package arquivos_separados;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Pedido {
  static Scanner in = new Scanner(System.in);
  private static int ultimoID = 0;

  private ArrayList<Item> itens = new ArrayList<>();

  private float total;
  private String cliente;
  private String data;
  private int id;

  Pedido(String cliente, String data) {
    this.cliente = in.nextLine();
    this.data = in.nextLine();
    this.id = geraID();
  }

  Pedido(String cliente) {
    Date dataAtual = new Date();
    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
    String dataFormatada = formatoData.format(dataAtual);
    this.cliente = cliente;
    this.data = dataFormatada;
    this.id = geraID();
  }

  public ArrayList<Item> getItens() {
    return itens;
  }

  public void setItens(ArrayList<Item> itens) {
    this.itens = itens;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCliente() {
    return cliente;
  }

  public void setCliente(String cliente) {
    this.cliente = cliente;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
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

  private int geraID() {
    return ++ultimoID;
  }

  public void imprimirMenu() {
    System.out.print("\033[H\033[2J");
    System.out.println("====================================================");
    System.out.println("Navegacao:");
    System.out.println("====================================================");
    System.out.println("1 - Adicionar item");
    System.out.println("2 - Remover item");
    System.out.println("3 - Imprimir pedido");
    System.out.println("4 - Alterar preço de um item");
    System.out.println("5 - Encerrar pedido");
    System.out.println("====================================================");
  }

  public void imprimirItens() {
    System.out.println("====================================================");
    System.out.println("Itens:");
    System.out.println("ID  | Nome                | Preco   | Qtde | Total");
    for (Item item : itens) {
      System.out.printf("%-4d| %-20s| R$%-6.2f| %-5d| R$%-6.2f%n",
          item.getId(), item.getNome(), item.getPreco(), item.getQtde(), item.calculaTotal());
    }
  }

  public void imprimirPedido() {
    imprimirMenu();
    System.out.println("====================================================");
    System.out.println("Pedido:");
    imprimirItens();
    System.out.println("====================================================");
    System.out.println("Total do Pedido: R$" + this.getTotal());
    System.out.println("====================================================");
  }

  public void removerItem() {
    imprimirMenu();
    System.out.println();
    imprimirItens();
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
      }
    }
    pausa();
    imprimirMenu();
  }

  public void adicionarItem() {
    imprimirMenu();
    Item item;
    System.out.println();
    System.out.println("informe o nome do item");
    String nome = in.nextLine();
    System.out.println("informe o preco do item");
    float preco = Float.parseFloat(in.nextLine());
    System.out.println("informe a quantidade do item");
    int qtde = Integer.parseInt(in.nextLine());
    System.out.println("esse item tem desconto? (s/n)");
    String desconto = in.nextLine();
    if (desconto.equals("s")) {
      System.out.println("informe o desconto do item");
      float descontoItem = Float.parseFloat(in.nextLine());
      item = new Item(nome, qtde, preco, descontoItem);
    } else {
      item = new Item(nome, preco, qtde);
    }

    this.itens.add(item);

    System.out.println("====================================================");
    System.out.println("Item adicionado com sucesso");
    System.out.println("====================================================");
    pausa();
    imprimirMenu();
  }

  public static void pausa() {
    try {
      Thread.sleep(2000);
    } catch (Exception e) {

    }
  }
}
