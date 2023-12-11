import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class gui {

  public static void main(String[] args) {
    ArrayList<Pedido> pedidos = new ArrayList<>();
    Gui gui = new Gui(pedidos);
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        System.err.println(e);
      }
    }
  }

  static class Gui {
    private JFrame frame;
    private JPanel panel;
    private JButton criarPedidoBtn;
    private JButton listaPedidosBtn;

    private ArrayList<Pedido> pedidos = new ArrayList<>();

    public Gui(ArrayList<Pedido> pedidos) {
      frame = new JFrame("Interface");
      frame.setSize(300, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      criarPedido();
      listarPedidos();

      panel = new JPanel();

      panel.add(listaPedidosBtn);
      panel.add(criarPedidoBtn);
      frame.add(panel);
      frame.setVisible(true);
    }

    public void listarPedidos() {
      listaPedidosBtn = new JButton("Listar Pedidos");
      listaPedidosBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String[] colunas = { "ID", "Nome", "Data", "Total" };
          String[][] dados = new String[pedidos.size()][4];
          for (int i = 0; i < pedidos.size(); i++) {
            dados[i][0] = String.valueOf(pedidos.get(i).getId());
            dados[i][1] = pedidos.get(i).getCliente();
            dados[i][2] = pedidos.get(i).getData();
            dados[i][3] = String.valueOf(pedidos.get(i).getTotal());
          }
          JTable table = new JTable(dados, colunas);
          JScrollPane scrollPane = new JScrollPane(table);
          JOptionPane.showMessageDialog(null, scrollPane, "Pedidos", JOptionPane.PLAIN_MESSAGE);
        }
      });
    }

    public void criarPedido() {
      criarPedidoBtn = new JButton("Criar Pedido");
      criarPedidoBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          Pedido p;
          String cliente = JOptionPane.showInputDialog("Informe o nome do cliente");
          String data = JOptionPane.showInputDialog("Informe a data do pedido (dd/mm/aaaa ou 'hoje' para data atual')");
          if (data.equals("hoje")) {
            p = new Pedido(cliente);
          } else {
            p = new Pedido(cliente, data);
          }
          pedidos.add(p);
          p.gerarGui();
        }
      });
    }
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
      this.cliente = cliente;
      this.data = data;
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
      System.out.println("Navegacao:");
      System.out.println("1 - Adicionar item");
      System.out.println("2 - Remover item");
      System.out.println("3 - Imprimir pedido");
      System.out.println("4 - Alterar preco de um item");
      System.out.println("5 - Encerrar pedido");
    }

    private void imprimirItens() {
      System.out.println("Itens:");
      System.out.println("ID  | Nome                | Preco   | Qtde | Total");
      for (Item item : itens) {
        System.out.printf("%-4d| %-20s| R$%-6.2f| %-5d| R$%-6.2f%n",
            item.getId(), item.getNome(), item.getPreco(), item.getQtde(), item.calculaTotal());
      }
    }

    public void imprimirPedido() {
      imprimirMenu();
      System.out.println("Pedido:");
      imprimirItens();
      System.out.println("Total do Pedido: R$" + this.getTotal());
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
      // adaptado para interface grafica
      String nome = JOptionPane.showInputDialog("Informe o nome do item");
      int qtde = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade do item"));
      float preco = Float.parseFloat(JOptionPane.showInputDialog("Informe o preco do item"));
      float desconto = Float.parseFloat(JOptionPane.showInputDialog("Informe o desconto do item"));
      Item item = new Item(nome, qtde, preco, desconto);
      itens.add(item);
    }

    public void gerarGui() {
      JFrame frame = new JFrame("Pedido");
      frame.setSize(800, 800);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JPanel panel = new JPanel();
      JButton adicionarItemBtn = new JButton("Adicionar Item");
      JButton removerItemBtn = new JButton("Remover Item");
      JButton imprimirPedidoBtn = new JButton("Imprimir Pedido");
      JButton alterarPrecoBtn = new JButton("Alterar Preco");
      JButton voltar = new JButton("Voltar");

      adicionarItemBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          adicionarItem();
        }
      });

      removerItemBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          removerItem();
        }
      });

      imprimirPedidoBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          imprimirPedido();
        }
      });

      voltar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          voltar(frame);
        }
      });

      panel.add(adicionarItemBtn);
      panel.add(removerItemBtn);
      panel.add(imprimirPedidoBtn);
      panel.add(alterarPrecoBtn);
      panel.add(voltar);
      frame.add(panel);
      frame.setVisible(true);
    }

    public void voltar(JFrame frame) {
      frame.dispose();
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

    public void alteraPreco(float preco, Pedido pedido) {
      this.setPreco(preco);
      System.out.println("Preco alterado com sucesso");
      pausa();
      pedido.imprimirMenu();
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
