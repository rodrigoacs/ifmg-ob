package com.example;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

import com.formdev.flatlaf.FlatLightLaf;

import org.jdatepicker.impl.*;

/**
 *
 * @author rodrigo
 */
public class gui extends javax.swing.JFrame {

	public gui() {

		initComponents();

	}

	private static String[] getClientesCadastrados() {
		Cliente c = new Cliente();
		return c.get();

	}

	private static String[] getProdCadastrados() {
		Produto p = new Produto();
		return p.get();
	}

	private static boolean validarData(String dataTexto) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		formato.setLenient(false);
		try {
			formato.parse(dataTexto);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	private static void selecionarClienteEData() {
		JFrame frame = new JFrame("Seleção de Cliente e Data");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new FlowLayout());

		JLabel labelCliente = new JLabel("Cliente: ");
		JComboBox<String> comboBoxClientes = new JComboBox<>(getClientesCadastrados());
		JLabel labelData = new JLabel("Data: (dd/MM/yyyy)");
		JTextField textFieldData = new JTextField(10);

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
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

	private static void gerarGuiPedidos(String cliente, String data) {
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

		JComboBox<String> itemComboBox = new JComboBox<>(getProdCadastrados());
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
		JLabel totalLabel = new JLabel("Total: ");
		JTextField totalTextField = new JTextField();
		totalTextField.setEditable(false);
		JButton confirmarButton = new JButton("Confirmar");

		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				totalTextField.setText(String.format("%.2f", calculaTotal(tableModel)));
			}
		});

		confirmarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cliente c = new Cliente();
				Pedido p = new Pedido(c.getClienteID(cliente), data, calculaTotal(tableModel));
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

	private static double calculaTotal(DefaultTableModel tableModel) {
		double total = 0.0;
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			Integer quantidade = (Integer) tableModel.getValueAt(i, 0);
			Double preco = (Double) tableModel.getValueAt(i, 2);

			if (quantidade != null && preco != null) {
				total += quantidade * preco;
			}
		}

		return total;
	}

	private static Double getPrice(String produto) {
		Produto p = new Produto();
		return p.getPrice(produto);
	}

	public static void gerarGuiCadastroCliente() {
		JFrame frame = new JFrame("Cadastrar Cliente");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);

		// Componentes da GUI
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

		JButton cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(100, 240, 120, 25);
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

				Cliente c = new Cliente(nome, rua, bairro, numero, cidade, estado, cpf);
				c.insert(c);

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
		frame.add(cadastrarButton);

		frame.setVisible(true);
	}

	public static void gerarGuiCadastroProduto() {
		JFrame frame = new JFrame("Cadastrar Produto");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(400, 400);
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
				Double preco = Double.parseDouble(precoField.getText());

				Produto p = new Produto(nome, preco);
				p.insert(p);

				JOptionPane.showMessageDialog(frame, "Produto cadastrado:\nNome: "
						+ nome + "\nPreço: " + preco);
				frame.dispose();
			}
		});

		frame.add(nomeLabel);
		frame.add(nomeField);
		frame.add(precoLabel);
		frame.add(precoField);
		frame.add(cadastrarButton);
		frame.setVisible(true);
	}

	public static void gerarGuiListarClientes() {
		JFrame frame = new JFrame("Listar Clientes");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1000, 1000);

		String[] colunas = { "Nome", "Rua", "Bairro", "Número", "Cidade", "Estado", "CPF", "Editar", "Excluir" };
		DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);

		JTable tClientes = new JTable(tableModel);
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
		private JTable table;
		private String label;

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
							Cliente c = new Cliente();
							int idCliente = c.getClienteID(nome);
							if (c.delete(idCliente)) {
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

		private void gerarGuiEdicaoCliente(int rowIndex, JTable table, JFrame framePai) {
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

				Cliente c = new Cliente();
				int idCliente = c.getClienteID(nome);
				boolean atualizado = c.update(idCliente, nNome, nRua, nBairro, nNumero, nCidade, nEstado, nCpf);

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

	public static void gerarGuiListarProdutos() {
		JFrame frame = new JFrame("Listar Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 600);

		String[] colunas = { "Nome", "Preço", "Editar", "Excluir" };
		DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);

		JTable tProd = new JTable(tableModel);
		tProd.getColumn("Editar").setCellRenderer(new PButtonRenderer("Editar"));
		tProd.getColumn("Editar").setCellEditor(new PButtonEditor(new JCheckBox(), tProd, "Editar", frame));
		tProd.getColumn("Excluir").setCellRenderer(new PButtonRenderer("Excluir"));
		tProd.getColumn("Excluir").setCellEditor(new PButtonEditor(new JCheckBox(), tProd, "Excluir", frame));

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

	static class PButtonRenderer extends JButton implements TableCellRenderer {
		public PButtonRenderer(String text) {
			setText(text);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			return this;
		}
	}

	static class PButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private JTable table;
		private String label;

		public PButtonEditor(JCheckBox checkBox, JTable table, String label, JFrame frame) {
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
							int idProduto = p.getProdutoID(nome);
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

		private void gerarGuiEdicaoProdutos(int rowIndex, JTable table, JFrame framePai) {
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
				int idCliente = p.getProdutoID(nome);
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

	@SuppressWarnings("unchecked")

	private void initComponents() {

		jLabel2 = new javax.swing.JLabel();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		CriarPedido = new javax.swing.JMenuItem();
		CadastrarCliente = new javax.swing.JMenuItem();
		CadastrarProduto = new javax.swing.JMenuItem();
		ListarCliente = new javax.swing.JMenuItem();
		ListarProduto = new javax.swing.JMenuItem();
		ListarPedido = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(255, 255, 255));
		setMaximumSize(new java.awt.Dimension(1024, 1024));
		setPreferredSize(new java.awt.Dimension(1024, 1024));

		jLabel2.setBackground(new java.awt.Color(255, 255, 255));
		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setIcon(new javax.swing.ImageIcon("./src/main/resources/8f799f9e-d751-41e3-9bb0-ecf8b7392801.png"));
		jLabel2.setAlignmentY(0.0F);
		jLabel2.setIconTextGap(0);

		jMenu1.setText("Menu");

		CriarPedido.setText("Criar Pedido");
		CriarPedido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		CriarPedido.setBorderPainted(false);
		CriarPedido.setContentAreaFilled(false);
		CriarPedido.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CriarPedidoActionPerformed(evt);
			}
		});
		jMenu1.add(CriarPedido);

		CadastrarCliente.setText("Cadastrar Cliente");
		CadastrarCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		CadastrarCliente.setBorderPainted(false);
		CadastrarCliente.setContentAreaFilled(false);
		CadastrarCliente.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CadastrarClienteActionPerformed(evt);
			}
		});
		jMenu1.add(CadastrarCliente);

		CadastrarProduto.setText("Cadastrar Produto");
		CadastrarProduto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		CadastrarProduto.setBorderPainted(false);
		CadastrarProduto.setContentAreaFilled(false);
		CadastrarProduto.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CadastrarProdutoActionPerformed(evt);
			}
		});
		jMenu1.add(CadastrarProduto);

		ListarCliente.setText("Listar Clientes");
		ListarCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		ListarCliente.setBorderPainted(false);
		ListarCliente.setContentAreaFilled(false);
		ListarCliente.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ListarClienteActionPerformed(evt);
			}
		});
		jMenu1.add(ListarCliente);

		ListarProduto.setText("Listar Produtos");
		ListarProduto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		ListarProduto.setBorderPainted(false);
		ListarProduto.setContentAreaFilled(false);
		ListarProduto.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ListarProdutoActionPerformed(evt);
			}
		});
		jMenu1.add(ListarProduto);

		ListarPedido.setText("Listar Pedidos");
		ListarPedido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		ListarPedido.setBorderPainted(false);
		ListarPedido.setContentAreaFilled(false);
		ListarPedido.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ListarPedidoActionPerformed(evt);
			}
		});
		jMenu1.add(ListarPedido);

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1024,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1024,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		jLabel2.getAccessibleContext().setAccessibleName("");

		pack();
		setLocationRelativeTo(null);

	}// </editor-fold>

	private void CriarPedidoActionPerformed(java.awt.event.ActionEvent evt) {
		selecionarClienteEData();
	}

	private void CadastrarClienteActionPerformed(java.awt.event.ActionEvent evt) {
		gerarGuiCadastroCliente();
	}

	private void CadastrarProdutoActionPerformed(java.awt.event.ActionEvent evt) {
		gerarGuiCadastroProduto();
	}

	private void ListarClienteActionPerformed(java.awt.event.ActionEvent evt) {
		gerarGuiListarClientes();
	}

	private void ListarProdutoActionPerformed(java.awt.event.ActionEvent evt) {
		gerarGuiListarProdutos();
	}

	private void ListarPedidoActionPerformed(java.awt.event.ActionEvent evt) {
	}

	public static void main(String args[]) {

		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new gui().setVisible(true);
			}
		});
	}

	public static void pausa() {
		try {
			Thread.sleep(100);
		} catch (Exception e) {

		}
	}

	static Boolean atualizarPedidos = true;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem CadastrarCliente;
	private javax.swing.JMenuItem CadastrarProduto;
	private javax.swing.JMenuItem ListarCliente;
	private javax.swing.JMenuItem ListarProduto;
	private javax.swing.JMenuItem CriarPedido;
	private javax.swing.JMenuItem ListarPedido;
}
