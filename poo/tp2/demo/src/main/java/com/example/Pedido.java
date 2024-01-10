package com.example;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Pedido implements DB<Pedido> {
  protected int idCliente;
  protected String dataPedido;
  protected Double total;

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

  public Pedido() {
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
          statement.setInt(2, produto.getId(produto.getNome()));
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

  @Override
  public boolean delete(int id) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public int getId(String nome) {
    throw new UnsupportedOperationException("Unimplemented method 'getId'");
  }

  public String[][] listarPedidos() {
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement
            .executeQuery(
                "select p.id, c.nome, p.data_pedido, p.total from pedidos p join clientes c on p.id_cliente = c.id ")) {

      ArrayList<String[]> lista = new ArrayList<>();
      while (resultSet.next()) {
        String id = Integer.toString(resultSet.getInt("id"));
        String nome = resultSet.getString("nome");
        String dataPedido = resultSet.getString("data_pedido");
        String total = Double.toString(resultSet.getDouble("total"));

        lista.add(new String[] { id, nome, dataPedido, total });
      }

      return lista.toArray(new String[0][]);
    } catch (SQLException e) {
      e.printStackTrace();
      return new String[0][0];
    }

  }

  public static String[][] getDetalhesPedido(String pedidoId) {
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(
            "SELECT ip.quantidade, p.nome, p.preco, (p.preco * ip.quantidade) AS total " +
                "FROM itens_pedido ip " +
                "JOIN produtos p ON ip.id_produto = p.id " +
                "WHERE ip.id_pedido = '" + pedidoId + "'")) {

      ArrayList<String[]> lista = new ArrayList<>();
      while (resultSet.next()) {
        String quantidade = Integer.toString(resultSet.getInt("quantidade"));
        String nomeProduto = resultSet.getString("nome");
        String preco = Double.toString(resultSet.getDouble("preco"));
        String total = Double.toString(resultSet.getDouble("total"));

        lista.add(new String[] { quantidade, nomeProduto, preco, total });
      }

      return lista.toArray(new String[0][]);
    } catch (SQLException e) {
      e.printStackTrace();
      return new String[0][0];
    }
  }

}
