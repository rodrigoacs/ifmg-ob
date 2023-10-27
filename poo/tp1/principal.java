import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.lang.Thread;
import java.text.SimpleDateFormat;

public class principal {
  public static void main(String[] args) {
    System.out.print("\033[H\033[2J");
    ArrayList<Pedido> pedidos = new ArrayList<>();
    Scanner in = new Scanner(System.in);
    String resposta;
    int op1 = 0;

    System.out.println("====================================================");
    System.out.println("Bem vindo ao sistema de pedidos");
    System.out.println("====================================================");
    do {
      System.out.println("1 - Listar pedidos");
      System.out.println("2 - Criar pedido");
      System.out.println("3 - Sair");
      System.out.println("====================================================");
      op1 = Integer.parseInt(in.nextLine());
      switch (op1) {
        case 1:
          System.out.println("ID  | Nome                | Preco   | Qtde | Total");
          for (Pedido pedido : pedidos) {
            System.out.println("====================================================");
            System.out.printf("%-4d| %-20s| R$%-6.2f| %-5d| R$%-6.2f%n",
                pedido.getId(), pedido.getCliente(), pedido.getData(), pedido.getTotal());
          }
          System.out.println("====================================================");
          break;
        case 2:
          Pedido p;
          System.out.print("\033[H\033[2J");
          System.out.println("====================================================");
          System.out.println("Informe o nome do cliente");
          String cliente = in.nextLine();
          System.out.println("Informe a data do pedido (dd/mm/aaaa ou 'hoje' para data atual')");
          String data = in.nextLine();
          if (data.equals("hoje")) {
            p = new Pedido(cliente);
          } else {
            p = new Pedido(cliente, data);
          }
          System.out.println("====================================================");

          p.imprimirMenu();
          do {
            resposta = in.nextLine();
            switch (resposta) {
              case "1":
                p.adicionarItem();
                break;
              case "2":
                p.removerItem();
                break;
              case "3":
                p.imprimirPedido();
                break;
              case "4":
                pedidos.add(p);
                System.out.println("====================================================");
                System.out.println("Pedido adicionado com sucesso");
                System.out.println("====================================================");
                pausa();
                System.out.print("\033[H\033[2J");
                break;
              default:
                System.out.println("====================================================");
                System.out.println("Opcao invalida");
                System.out.println("====================================================");
                break;
            }
          } while (!resposta.equals("4"));
          break;
        case 3:
          break;
        default:
          System.out.println("====================================================");
          System.out.println("Opcao invalida");
          System.out.println("====================================================");
          break;
      }
    } while (op1 != 3);

  }

  static class Pedido {
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
      System.out.println("4 - Encerrar pedido");
      System.out.println("====================================================");
    }

    private void imprimirItens() {
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

  }

  static class Item {
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

    private int geraID() {
      return ++ultimoID;
    }
  }

  public static void pausa() {
    try {
      Thread.sleep(2000);
    } catch (Exception e) {

    }
  }
}
