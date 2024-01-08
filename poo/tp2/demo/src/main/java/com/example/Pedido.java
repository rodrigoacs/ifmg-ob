package com.example;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Pedido implements DB<Pedido> {
  int idCliente;
  String dataPedido;
  Double total;

  public int getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(int idCliente) {
    this.idCliente = idCliente;
  }

  public String getDataPedido() {
    return dataPedido;
  }

  public void setDataPedido(String dataPedido) {
    this.dataPedido = dataPedido;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public Pedido(int idCliente, String dataPedido, Double total) {
    this.idCliente = idCliente;
    this.dataPedido = dataPedido;
    this.total = total;
  }

  @Override
  public String[] get() {
    return null;
  }

  @Override
  public int insert(Pedido p) {
    java.sql.Date dataSQL = null;
    try {
      SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
      SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd");
      java.util.Date dataParsed = formatoEntrada.parse(p.dataPedido);
      String dataFormatadaParaSQL = formatoSaida.format(dataParsed);
      dataSQL = java.sql.Date.valueOf(dataFormatadaParaSQL);
    } catch (ParseException e) {
      e.printStackTrace();
      return -1;
    }

    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "INSERT INTO pedidos (id_cliente, data_pedido, total) VALUES (?, ?, ?)";
      try (PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        statement.setInt(1, p.idCliente);
        statement.setDate(2, dataSQL);
        statement.setDouble(3, p.total);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
          throw new SQLException("Falha ao inserir o pedido.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            return generatedKeys.getInt(1); // Retorna o ID gerado
          } else {
            throw new SQLException("Falha ao obter o ID do pedido.");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  public boolean insertItens(ArrayList<Produto> produtos, int idPedido) {
    for (Produto produto : produtos) {
      try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
        String sql = "INSERT INTO itens_pedido (id_pedido, id_produto, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
          statement.setInt(1, idPedido);
          statement.setInt(2, produto.getProdutoID(produto.getNome()));
          statement.setInt(3, produto.getQuantidade());

          int rowsAffected = statement.executeUpdate();
          if (rowsAffected == 0) {
            throw new SQLException("Falha ao inserir o item: " + produto.getNome());
          }

        }
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    }

    return true;
  }

}
