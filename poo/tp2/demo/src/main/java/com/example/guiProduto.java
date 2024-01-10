package com.example;

import java.awt.Color;
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

public class guiProduto extends javax.swing.JFrame {
  public static String[] getProdCadastrados() {
    Produto p = new Produto();
    return p.get();
  }

  public static void gerarGuiCadastroProduto() {
    JFrame frame = new JFrame("Cadastrar Produto");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLayout(null);
    frame.setSize(360, 240);
    frame.setLocationRelativeTo(null);

    JLabel nomeLabel = new JLabel("Nome:");
    nomeLabel.setBounds(20, 20, 80, 25);
    JTextField nomeField = new JTextField(20);
    nomeField.setBounds(100, 20, 165, 25);

    JLabel precoLabel = new JLabel("Preço:");
    precoLabel.setBounds(20, 60, 80, 25);
    JTextField precoField = new JTextField(20);
    precoField.setBounds(100, 60, 165, 25);

    JButton cadastrarButton = new JButton("Cadastrar");
    cadastrarButton.setBounds(100, 100, 120, 25);
    cadastrarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String nome = nomeField.getText();
        String preco = precoField.getText();

        if (nome.isBlank() || preco.isBlank()) {
          JOptionPane.showMessageDialog(frame, "Erro na criação do produto! Preencha todos os campos.");
        } else {
          Produto p = new Produto(nome, Double.parseDouble(preco));
          p.insert(p);

          JOptionPane.showMessageDialog(frame, "Produto cadastrado:\nNome: "
              + nome + "\nPreço: " + preco);
          frame.dispose();
        }
      }
    });

    frame.add(nomeLabel);
    frame.add(nomeField);
    frame.add(precoLabel);
    frame.add(precoField);
    frame.add(cadastrarButton);
    frame.setVisible(true);
  }

  public static void gerarGuiListarProdutos() {
    JFrame frame = new JFrame("Listar Produtos");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(800, 600);

    String[] colunas = { "Nome", "Preço", "Editar", "Excluir" };
    DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);

    JTable tProd = new JTable(tableModel) {
      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2 || columnIndex == 3;
      }

      @Override
      public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) {
          component.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
        }
        return component;
      }

    };
    tProd.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
    tProd.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tProd, "Editar", frame));
    tProd.getColumn("Excluir").setCellRenderer(new ButtonRenderer("Excluir"));
    tProd.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), tProd, "Excluir", frame));

    Produto p = new Produto();
    String[][] dados = p.listarProdutos();

    for (String[] linha : dados) {
      tableModel.addRow(linha);
    }

    JScrollPane scrollPane = new JScrollPane(tProd);
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
      this.button = new JButton();

      button.addActionListener(e -> {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
          if ("Editar".equals(label)) {
            gerarGuiEdicaoProdutos(selectedRow, table, frame);

          } else if ("Excluir".equals(label)) {
            int confirm = JOptionPane.showConfirmDialog(table,
                "Tem certeza que deseja excluir este produto?",
                "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
              String nome = (String) table.getValueAt(selectedRow, 0);
              Produto p = new Produto();
              int idProduto = p.getId(nome);
              if (p.delete(idProduto)) {
                JOptionPane.showMessageDialog(table, "Produto excluído com sucesso!");
                frame.dispose();
                gerarGuiListarProdutos();
              } else {
                JOptionPane.showMessageDialog(table, "Erro ao excluir produto.");
              }
            }
          }
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

    public void gerarGuiEdicaoProdutos(int rowIndex, JTable table, JFrame framePai) {
      JFrame frame = new JFrame("Editar Produto");
      frame.setLayout(new GridLayout(0, 2));
      frame.setSize(600, 600);

      String nome = (String) table.getValueAt(rowIndex, 0);
      String preco = (String) table.getValueAt(rowIndex, 1);

      frame.add(new JLabel("Nome:"));
      JTextField nomeField = new JTextField(nome);
      frame.add(nomeField);

      frame.add(new JLabel("Preco:"));
      JTextField precoField = new JTextField(preco);
      frame.add(precoField);

      JButton confirmarButton = new JButton("Confirmar");
      confirmarButton.addActionListener(e -> {
        String nNome = nomeField.getText();
        String nPreco = precoField.getText();

        Produto p = new Produto();
        int idCliente = p.getId(nome);
        boolean atualizado = p.update(idCliente, nNome, nPreco);

        if (atualizado) {
          JOptionPane.showMessageDialog(frame, "Produto atualizado com sucesso!");
          framePai.dispose();
          gerarGuiListarProdutos();
        } else {
          JOptionPane.showMessageDialog(frame, "Erro ao atualizar produto.");
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
