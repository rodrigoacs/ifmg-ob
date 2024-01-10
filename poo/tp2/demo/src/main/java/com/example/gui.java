package com.example;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author rodrigo
 */
public class gui extends javax.swing.JFrame {

	public gui() {
		initComponents();
	}

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
		guiPedido.selecionarClienteEData();
	}

	private void CadastrarClienteActionPerformed(java.awt.event.ActionEvent evt) {
		guiCliente.gerarGuiCadastroCliente();
	}

	private void CadastrarProdutoActionPerformed(java.awt.event.ActionEvent evt) {
		guiProduto.gerarGuiCadastroProduto();
	}

	private void ListarClienteActionPerformed(java.awt.event.ActionEvent evt) {
		guiCliente.gerarGuiListarClientes();
	}

	private void ListarProdutoActionPerformed(java.awt.event.ActionEvent evt) {
		guiProduto.gerarGuiListarProdutos();
	}

	private void ListarPedidoActionPerformed(java.awt.event.ActionEvent evt) {
		guiPedido.gerarGuiListarPedidos();
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
