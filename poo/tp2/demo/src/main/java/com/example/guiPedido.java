package com.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class guiPedido extends javax.swing.JFrame {
  public static boolean validarData(String dataTexto) {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    formato.setLenient(false);
    try {
      formato.parse(dataTexto);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static void selecionarClienteEData() {
    JFrame frame = new JFrame("Seleção de Cliente e Data");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLayout(new FlowLayout());

    JLabel labelCliente = new JLabel("Cliente: ");
    JComboBox<String> comboBoxClientes = new JComboBox<>(guiCliente.getClientesCadastrados());
    JLabel labelData = new JLabel("Data: (dd/MM/yyyy)");
    JTextField textFieldData = new JTextField(10);

    JButton btnConfirmar = new JButton("Confirmar");
    btnConfirmar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String clienteSelecionado = (String) comboBoxClientes.getSelectedItem();
        String dataTexto = textFieldData.getText();

        if (validarData(dataTexto)) {
          gerarGuiPedidos(clienteSelecionado, dataTexto);
          frame.dispose();
        } else {
          JOptionPane.showMessageDialog(frame, "Formato de data inválido. Use dd/MM/yyyy.");
        }
      }
    });

    frame.add(labelCliente);
    frame.add(comboBoxClientes);
    frame.add(labelData);
    frame.add(textFieldData);
    frame.add(btnConfirmar);

    frame.setSize(800, 150);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static void gerarGuiPedidos(String cliente, String data) {
    JFrame frame = new JFrame("Pedido");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    DefaultTableModel tableModel = new DefaultTableModel() {
      @Override
      public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
          case 0:
            return Integer.class; // Quantidade
          case 1:
            return String.class; // Produto
          case 2:
            return Double.class; // Preço
          case 3:
            return Double.class; // Total
          default:
            return Object.class;
        }
      }

      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 0 || column == 1; // Apenas quantidade e produto são editáveis
      }

      @Override
      public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column);
        if (column == 0 || column == 1) {
          Integer quantidade = (Integer) getValueAt(row, 0);
          Double preco = getPrice((String) getValueAt(row, 1));
          if (quantidade != null && preco != null) {
            setValueAt(preco, row, 2);
            setValueAt(quantidade * preco, row, 3);
          }
        }
      }
    };

    tableModel.addColumn("Quantidade");
    tableModel.addColumn("Produto");
    tableModel.addColumn("Preço");
    tableModel.addColumn("Total");

    JTable table = new JTable(tableModel) {
      @Override
      public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) {
          component.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.GRAY);
        }
        return component;
      }
    };

    JComboBox<String> itemComboBox = new JComboBox<>(guiProduto.getProdCadastrados());
    TableColumn itemColumn = table.getColumnModel().getColumn(1);
    itemColumn.setCellEditor(new DefaultCellEditor(itemComboBox));

    tableModel.addRow(new Object[] {});

    JButton addButton = new JButton("Adicionar Produto");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        tableModel.addRow(new Object[] {});
      }
    });

    JLabel clienteLabel = new JLabel("Cliente: ");
    JTextField clienteTextField = new JTextField(cliente);
    clienteTextField.setEditable(false);

    JLabel dataLabel = new JLabel("Data: ");
    JTextField dataTextField = new JTextField(data);
    dataTextField.setEditable(false);

    JLabel fretelLabel = new JLabel("Frete: ");
    JTextField freteTextField = new JTextField();
    int id = new Cliente().getId(cliente);
    if (Cliente.verificaPrime(id)) {
      freteTextField.setEditable(false);
    }

    JLabel totalLabel = new JLabel("Total: ");
    JTextField totalTextField = new JTextField();
    totalTextField.setEditable(false);

    JButton confirmarButton = new JButton("Confirmar");

    tableModel.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        totalTextField.setText(String.format("%.2f", calculaTotal(tableModel, ClientePrime.getDesconto(id))));
      }
    });

    confirmarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int id = new Cliente().getId(cliente);
        Pedido p = new Pedido(id, data, calculaTotal(tableModel, ClientePrime.getDesconto(id)));
        int idPedido = p.insert(p);
        p.insertItens(getItens(tableModel), idPedido);
        if (idPedido != -1) {
          JOptionPane.showMessageDialog(frame, "Pedido criado com sucesso!");
        } else {
          JOptionPane.showMessageDialog(frame, "Erro na criação do pedido! Tente novamente.");
        }

        frame.dispose();
      }
    });

    JPanel panelBotoes = new JPanel();
    panelBotoes.add(addButton);
    panelBotoes.add(confirmarButton);

    JPanel panelInfo = new JPanel(new GridLayout(1, 6));
    panelInfo.add(clienteLabel);
    panelInfo.add(clienteTextField);
    panelInfo.add(dataLabel);
    panelInfo.add(dataTextField);
    panelInfo.add(fretelLabel);
    panelInfo.add(freteTextField);
    panelInfo.add(totalLabel);
    panelInfo.add(totalTextField);

    frame.setLayout(new BorderLayout());
    frame.add(panelInfo, BorderLayout.NORTH);
    frame.add(new JScrollPane(table), BorderLayout.CENTER);
    frame.add(panelBotoes, BorderLayout.SOUTH);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static ArrayList<Produto> getItens(DefaultTableModel model) {
    ArrayList<Produto> itensPedido = new ArrayList<>();

    for (int i = 0; i < model.getRowCount(); i++) {
      try {
        String nomeProduto = model.getValueAt(i, 1).toString();
        int quantidade = Integer.parseInt(model.getValueAt(i, 0).toString());
        Produto p = new Produto(nomeProduto, quantidade);

        itensPedido.add(p);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }

    return itensPedido;
  }

  public static double calculaTotal(DefaultTableModel tableModel, Double desconto) {
    double total = 0.0;
    for (int i = 0; i < tableModel.getRowCount(); i++) {
      Integer quantidade = (Integer) tableModel.getValueAt(i, 0);
      Double preco = (Double) tableModel.getValueAt(i, 2);

      if (quantidade != null && preco != null) {
        total += quantidade * preco;
      }
    }
    total = total - (total * (desconto / 100));
    return total;
  }

  public static Double getPrice(String produto) {
    Produto p = new Produto();
    return p.getPrice(produto);
  }

  public static void gerarGuiListarPedidos() {
    JFrame frame = new JFrame("Listar Pedidos");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(800, 600);

    String[] colunas = { "Numero", "Nome", "Data", "Total", "Detalhes" };
    DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);

    JTable tPed = new JTable(tableModel) {
      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
      }
    };

    tPed.getColumn("Detalhes").setCellRenderer(new ButtonRenderer("Detalhes"));
    tPed.getColumn("Detalhes").setCellEditor(new ButtonEditor(new JCheckBox(), tPed, "Detalhes"));

    Pedido p = new Pedido();
    String[][] dados = p.listarPedidos();

    for (String[] linha : dados) {
      tableModel.addRow(linha);
    }

    JScrollPane scrollPane = new JScrollPane(tPed);
    frame.add(scrollPane);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  static class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer(String text) {
      setText(text);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      return this;
    }
  }

  static class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    public JTable table;
    public String label;
    public String id;

    public ButtonEditor(JCheckBox checkBox, JTable table, String label) {
      super(checkBox);
      this.table = table;
      this.label = label;
      this.button = new JButton(label);

      button.addActionListener(e -> {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
          id = (String) table.getValueAt(selectedRow, 0);
          gerarGuiDetalhes(id);
        }
      });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
      return button;
    }

    @Override
    public Object getCellEditorValue() {
      return label;
    }

    public void gerarGuiDetalhes(String id) {
      JFrame frame = new JFrame("Detalhes");
      frame.setLayout(new GridLayout(0, 1));
      frame.setSize(600, 600);

      String[] colunas = { "Qtde", "Nome", "Preço", "Total" };
      DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);

      JTable tPedidos = new JTable(tableModel) {
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
          return false;
        }
      };

      String[][] dados = Pedido.getDetalhesPedido(id);

      for (String[] linha : dados) {
        tableModel.addRow(linha);
      }

      JScrollPane scrollPane = new JScrollPane(tPedidos);
      frame.add(scrollPane);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    }

  }
}
