package arquivos_separados;
import java.util.ArrayList;
import java.util.Scanner;

public class tp {
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
                System.out.print("\033[H\033[2J");
                p.imprimirItens();
                System.out.println("====================================================");
                System.out.println("Informe o id do item");
                int id = Integer.parseInt(in.nextLine());
                System.out.println("Informe o novo preco do item");
                float preco = Float.parseFloat(in.nextLine());
                for (Item item : p.getItens()) {
                  if (item.getId() == id) {
                    item.alteraPreco(preco, p);
                  }
                }
                break;
              case "5":
                pedidos.add(p);
                System.out.println("====================================================");
                System.out.println("Pedido adicionado com sucesso");
                System.out.println("====================================================");
                pausa();
                System.out.print("\033[H\033[2J");
                break;
              default:
                System.out.print("\033[H\033[2J");
                p.imprimirMenu();
                break;
            }
          } while (!resposta.equals("5"));
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

  public static void pausa() {
    try {
      Thread.sleep(2000);
    } catch (Exception e) {

    }
  }
}