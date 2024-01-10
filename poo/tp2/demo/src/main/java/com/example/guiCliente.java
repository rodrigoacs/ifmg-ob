package com.example;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class guiCliente extends javax.swing.JFrame {
  public static String[] getClientesCadastrados() {
    Cliente c = new Cliente();
    return c.get();
  }

  public static void gerarGuiCadastroCliente() {
    JFrame frame = new JFrame("Cadastrar Cliente");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLayout(null);
    frame.setSize(400, 500);
    frame.setLocationRelativeTo(null);

    JLabel nomeLabel = new JLabel("Nome:");
    nomeLabel.setBounds(20, 20, 80, 25);
    JTextField nomeField = new JTextField(20);
    nomeField.setBounds(100, 20, 165, 25);

    JLabel ruaLabel = new JLabel("Rua:");
    ruaLabel.setBounds(20, 50, 80, 25);
    JTextField ruaField = new JTextField(20);
    ruaField.setBounds(100, 50, 165, 25);

    JLabel bairroLabel = new JLabel("Bairro:");
    bairroLabel.setBounds(20, 80, 80, 25);
    JTextField bairroField = new JTextField(20);
    bairroField.setBounds(100, 80, 165, 25);

    JLabel numeroLabel = new JLabel("Número:");
    numeroLabel.setBounds(20, 110, 80, 25);
    JTextField numeroField = new JTextField(20);
    numeroField.setBounds(100, 110, 165, 25);

    JLabel cidadeLabel = new JLabel("Cidade:");
    cidadeLabel.setBounds(20, 140, 80, 25);
    JTextField cidadeField = new JTextField(20);
    cidadeField.setBounds(100, 140, 165, 25);

    JLabel estadoLabel = new JLabel("Estado:");
    estadoLabel.setBounds(20, 170, 80, 25);
    JTextField estadoField = new JTextField(20);
    estadoField.setBounds(100, 170, 165, 25);

    JLabel cpfLabel = new JLabel("CPF:");
    cpfLabel.setBounds(20, 200, 80, 25);
    JTextField cpfField = new JTextField(20);
    cpfField.setBounds(100, 200, 165, 25);

    JLabel primeLabel = new JLabel("Prime?:");
    primeLabel.setBounds(20, 230, 80, 25);
    JCheckBox primeField = new JCheckBox();
    primeField.setBounds(100, 230, 165, 25);

    JLabel descontoLabel = new JLabel("% de desconto:");
    descontoLabel.setBounds(20, 260, 80, 25);
    JTextField descontoField = new JTextField(20);
    descontoField.setBounds(100, 260, 165, 25);

    JButton cadastrarButton = new JButton("Cadastrar");
    cadastrarButton.setBounds(100, 300, 120, 25);
    cadastrarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String nome = nomeField.getText();
        String rua = ruaField.getText();
        String bairro = bairroField.getText();
        String numero = numeroField.getText();
        String cidade = cidadeField.getText();
        String estado = estadoField.getText();
        String cpf = cpfField.getText();
        Double desconto = Double.parseDouble(descontoField.getText());

        if (primeField.isSelected()) {
          ClientePrime cp = new ClientePrime(nome, rua, bairro, numero, cidade, estado, cpf, desconto);
          cp.insert(cp);
        } else {
          Cliente c = new Cliente(nome, rua, bairro, numero, cidade, estado, cpf);
          c.insert(c);
        }

        JOptionPane.showMessageDialog(frame, "Cliente cadastrado:\nNome: " + nome
            + "\nRua: " + rua + "\nBairro: " + bairro + "\nNúmero: " + numero
            + "\nCidade: " + cidade + "\nEstado: " + estado + "\nCPF: " + cpf);
        frame.dispose();
      }
    });

    frame.add(nomeLabel);
    frame.add(nomeField);
    frame.add(ruaLabel);
    frame.add(ruaField);
    frame.add(bairroLabel);
    frame.add(bairroField);
    frame.add(numeroLabel);
    frame.add(numeroField);
    frame.add(cidadeLabel);
    frame.add(cidadeField);
    frame.add(estadoLabel);
    frame.add(estadoField);
    frame.add(cpfLabel);
    frame.add(cpfField);
    frame.add(descontoLabel);
    frame.add(descontoField);
    frame.add(primeLabel);
    frame.add(primeField);
    frame.add(cadastrarButton);

    frame.setVisible(true);
  }

  public static void gerarGuiListarClientes() {
    JFrame frame = new JFrame("Listar Clientes");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(1000, 1000);

    String[] colunas = { "Nome", "Rua", "Bairro", "Número", "Cidade", "Estado", "CPF", "Editar", "Excluir" };
    DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);

    JTable tClientes = new JTable(tableModel) {
      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
      }
    };

    tClientes.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
    tClientes.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tClientes, "Editar", frame));
    tClientes.getColumn("Excluir").setCellRenderer(new ButtonRenderer("Excluir"));
    tClientes.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), tClientes, "Excluir", frame));

    Cliente c = new Cliente();

    String[][] dados = c.listarClientes();

    for (String[] linha : dados) {
      tableModel.addRow(linha);
    }

    JScrollPane scrollPane = new JScrollPane(tClientes);
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

    public ButtonEditor(JCheckBox checkBox, JTable table, String label, JFrame frame) {
      super(checkBox);
      this.table = table;
      this.label = label;
      button = new JButton(label);

      button.addActionListener(e -> {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
          if ("Editar".equals(label)) {
            gerarGuiEdicaoCliente(selectedRow, table, frame);
          } else if ("Excluir".equals(label)) {
            int confirm = JOptionPane.showConfirmDialog(table,
                "Tem certeza que deseja excluir este cliente?",
                "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
              String nome = (String) table.getValueAt(selectedRow, 0);
              int idCliente = new Cliente().getId(nome);
              if (new Cliente().delete(idCliente)) {
                JOptionPane.showMessageDialog(table, "Cliente excluído com sucesso!");
                frame.dispose();
                gerarGuiListarClientes();
              } else {
                JOptionPane.showMessageDialog(table, "Erro ao excluir cliente.");
              }
            }
          }
        }
      });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
      return button;
    }

    public Object getCellEditorValue() {
      return label;
    }

    public void gerarGuiEdicaoCliente(int rowIndex, JTable table, JFrame framePai) {
      JFrame frame = new JFrame("Editar Cliente");
      frame.setLayout(new GridLayout(0, 2));
      frame.setSize(600, 600);

      String nome = (String) table.getValueAt(rowIndex, 0);
      String rua = (String) table.getValueAt(rowIndex, 1);
      String bairro = (String) table.getValueAt(rowIndex, 2);
      String numero = (String) table.getValueAt(rowIndex, 3);
      String cidade = (String) table.getValueAt(rowIndex, 4);
      String estado = (String) table.getValueAt(rowIndex, 5);
      String cpf = (String) table.getValueAt(rowIndex, 6);

      frame.add(new JLabel("Nome:"));
      JTextField nomeField = new JTextField(nome);
      frame.add(nomeField);

      frame.add(new JLabel("Rua:"));
      JTextField ruaField = new JTextField(rua);
      frame.add(ruaField);

      frame.add(new JLabel("Bairro:"));
      JTextField bairroField = new JTextField(bairro);
      frame.add(bairroField);

      frame.add(new JLabel("Número:"));
      JTextField numeroField = new JTextField(numero);
      frame.add(numeroField);

      frame.add(new JLabel("Cidade:"));
      JTextField cidadeField = new JTextField(cidade);
      frame.add(cidadeField);

      frame.add(new JLabel("Estado:"));
      JTextField estadoField = new JTextField(estado);
      frame.add(estadoField);

      frame.add(new JLabel("CPF:"));
      JTextField cpfField = new JTextField(cpf);
      frame.add(cpfField);

      JButton confirmarButton = new JButton("Confirmar");
      confirmarButton.addActionListener(e -> {
        String nNome = nomeField.getText();
        String nRua = ruaField.getText();
        String nBairro = bairroField.getText();
        String nNumero = numeroField.getText();
        String nCidade = cidadeField.getText();
        String nEstado = estadoField.getText();
        String nCpf = cpfField.getText();

        int idCliente = new Cliente().getId(nome);
        boolean atualizado = Cliente.update(idCliente, nNome, nRua, nBairro, nNumero, nCidade, nEstado, nCpf);

        if (atualizado) {
          JOptionPane.showMessageDialog(frame, "Cliente atualizado com sucesso!");
          framePai.dispose();
          gerarGuiListarClientes();
        } else {
          JOptionPane.showMessageDialog(frame, "Erro ao atualizar cliente.");
        }

        frame.dispose();
      });
      frame.add(confirmarButton);

      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    }
  }

}
