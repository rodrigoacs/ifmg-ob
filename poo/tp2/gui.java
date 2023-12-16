import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.awt.Component;
import java.util.Iterator;

public class gui {
  static Boolean atualizarPedidos = true;

  public static void main(String[] args) {
    ArrayList<Pedido> pedidos = new ArrayList<>();
    Gui gui = new Gui(pedidos);
  }

  static class Gui {
    private JFrame frame;
    private JPanel panel;
    private JButton criarPedidoBtn;
    private JTable listaPedidos;

    private ArrayList<Pedido> pedidos = new ArrayList<>();

    public Gui(ArrayList<Pedido> pedidos) {
      frame = new JFrame("Interface");
      panel = new JPanel();

      frame.setSize(800, 800);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      while (true) {
        if (atualizarPedidos) {
          atualizarPedidos = false;
          panel.removeAll();
          panel.add(criarPedido(criarPedidoBtn));
          panel.add(listarPedidos(listaPedidos));
          frame.add(panel);
          frame.revalidate();
          frame.repaint();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        }
        pausa();
      }
    }

    public JTable listarPedidos(JTable table) {
      String[] colunas = { "ID", "Nome", "Data", "Total" };
      String[][] dados = new String[pedidos.size()][4];
      for (int i = 0; i < pedidos.size(); i++) {
        dados[i][0] = String.valueOf(pedidos.get(i).getId());
        dados[i][1] = pedidos.get(i).getCliente();
        dados[i][2] = pedidos.get(i).getData();
        dados[i][3] = String.valueOf(pedidos.get(i).getTotal());
      }
      table = new JTable(dados, colunas);
      return table;
    }

    public JButton criarPedido(JButton criarPedidoBtn) {
      criarPedidoBtn = new JButton("Criar Pedido");
      criarPedidoBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          Pedido p;
          JTextField clienteField = new JTextField();
          JTextField dataField = new JTextField();

          Object[] message = { "Cliente:", clienteField, "Data (dd/mm/aaaa ou 'hoje'):", dataField };

          JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
          pane.createDialog(null, "Criar Pedido").setVisible(true);

          String cliente = clienteField.getText();
          String data = dataField.getText();

          if (data.equals("hoje")) {
            p = new Pedido(cliente);
          } else {
            p = new Pedido(cliente, data);
          }
          pedidos.add(p);
          p.gerarGui();
        }
      });
      return criarPedidoBtn;
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

    private boolean removeTabela(JPanel panel) {
      Component[] components = panel.getComponents();
      for (Component component : components) {
        if (component instanceof JTable) {
          panel.remove(component);
          return true;
        }
      }
      return false;
    }

    private JTable imprimirItens() {
      String[] colunas = { "ID", "Nome", "Preco", "Qtde", "Total" };
      String[][] dados = new String[itens.size()][5];
      for (int i = 0; i < itens.size(); i++) {
        dados[i][0] = String.valueOf(itens.get(i).getId());
        dados[i][1] = itens.get(i).getNome();
        dados[i][2] = String.valueOf(itens.get(i).getPreco());
        dados[i][3] = String.valueOf(itens.get(i).getQtde());
        dados[i][4] = String.valueOf(itens.get(i).calculaTotal());
      }
      JTable table = new JTable(dados, colunas);
      return table;
    }

    private void atualizarTabela(JPanel panel) {
      removeTabela(panel);
      panel.add(imprimirItens());
    }

    public void removerItem() {
      JTextField idField = new JTextField();
      JTextField qtdeField = new JTextField();

      Object[] message = { "ID:", idField, "Quantidade:", qtdeField };

      JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
      pane.createDialog(null, "Remover Item").setVisible(true);

      int id = Integer.parseInt(idField.getText());
      int qtde = Integer.parseInt(qtdeField.getText());

      Iterator<Item> iterator = itens.iterator();
      while (iterator.hasNext()) {
        Item item = iterator.next();
        if (item.getId() == id) {
          if (item.getQtde() > qtde) {
            item.setQtde(item.getQtde() - qtde);
          } else {
            iterator.remove();
          }
        }
      }
    }

    public void adicionarItem() {
      JTextField nomeField = new JTextField();
      JTextField qtdeField = new JTextField();
      JTextField precoField = new JTextField();
      JTextField descontoField = new JTextField();

      Object[] message = { "Nome:", nomeField, "Quantidade:", qtdeField, "Preco:", precoField, "Desconto:",
          descontoField };

      JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
      pane.createDialog(null, "Adicionar Item").setVisible(true);

      String nome = nomeField.getText();
      int qtde = Integer.parseInt(qtdeField.getText());
      float preco = Float.parseFloat(precoField.getText());
      float desconto = Float.parseFloat(descontoField.getText());

      Item item = new Item(nome, qtde, preco, desconto);
      itens.add(item);
    }

    public void alterarPreco() {
      JTextField idField = new JTextField();
      JTextField novoPrecoField = new JTextField();

      Object[] message = { "ID:", idField, "Novo Preco:", novoPrecoField };

      JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
      pane.createDialog(null, "Alterar Preco").setVisible(true);

      int id = Integer.parseInt(idField.getText());
      float novoPreco = Float.parseFloat(novoPrecoField.getText());

      for (Item item : itens) {
        if (item.getId() == id) {
          item.setPreco(novoPreco);
          JOptionPane.showMessageDialog(null, "Preço alterado com sucesso!");
          return;
        }
      }
      JOptionPane.showMessageDialog(null, "Item não encontrado com o ID fornecido.");
    }

    public void atualizarLabelTotal(JLabel label) {
      this.total = calculaTotal();
      label.setText("Cliente: " + this.cliente + " Data: " + this.data + " Total: " + this.total);
    }

    public void gerarGui() {
      JFrame frame = new JFrame("Pedido");
      frame.setSize(800, 800);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JPanel panel = new JPanel();
      JButton adicionarItemBtn = new JButton("Adicionar Item");
      JButton removerItemBtn = new JButton("Remover Item");
      JButton alterarPrecoBtn = new JButton("Alterar Preco");
      JButton voltar = new JButton("Voltar");
      JLabel label = new JLabel("Cliente: " + this.cliente + " Data: " + this.data + " Total: " + this.total);

      adicionarItemBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          adicionarItem();
          atualizarTabela(panel);
          atualizarLabelTotal(label);
          frame.revalidate();
          frame.repaint();
        }
      });

      alterarPrecoBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          alterarPreco();
          atualizarTabela(panel);
          atualizarLabelTotal(label);
          frame.revalidate();
          frame.repaint();
        }
      });

      removerItemBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          removerItem();
          atualizarTabela(panel);
          atualizarLabelTotal(label);
          frame.revalidate();
          frame.repaint();
        }
      });

      voltar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          voltar(frame);
        }
      });

      panel.add(label);
      panel.add(adicionarItemBtn);
      panel.add(removerItemBtn);
      panel.add(alterarPrecoBtn);
      panel.add(voltar);
      frame.add(panel);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    }

    public void voltar(JFrame frame) {
      atualizarPedidos = true;
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

    private int geraID() {
      return ++ultimoID;
    }
  }

  public static void pausa() {
    try {
      Thread.sleep(100);
    } catch (Exception e) {

    }
  }

}
