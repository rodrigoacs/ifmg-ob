package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Produto implements DB<Produto> {

  String nome;
  int quantidade;
  Double preco;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public Produto(String nome, int quantidade) {
    this.nome = nome;
    this.quantidade = quantidade;
  }

  public Produto(String nome, Double preco) {
    this.nome = nome;
    this.preco = preco;
  }

  public Produto() {

  }

  @Override
  public int insert(Produto p) {
    String sql = "INSERT INTO produtos (nome, preco) VALUES (?, ?)";

    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);) {
      PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, p.nome);
      statement.setDouble(2, p.preco);

      int rowsAffected = statement.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Falha ao inserir o cliente.");
      }

      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1); // Retorna o ID gerado
        } else {
          throw new SQLException("Falha ao obter o ID do cliente.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return -1; // Indica falha
    }
  }

  @Override
  public String[] get() {
    String[] produtos = null;

    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "SELECT * FROM produtos";
      String countQuery = "SELECT COUNT(id) FROM produtos";
      String countResult = null;

      try (PreparedStatement statement = conexao.prepareStatement(countQuery)) {
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            countResult = resultSet.getString(1);
          }
        }
      }

      try (PreparedStatement statement = conexao.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();

        produtos = new String[Integer.parseInt(countResult)];

        int index = 0;
        while (resultSet.next()) {
          String nome = resultSet.getString("nome");
          produtos[index++] = nome;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return produtos;
  }

  public boolean delete(int idProduto) {
    String sql = "UPDATE produtos SET ativo = FALSE WHERE id = ?";

    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
        PreparedStatement statement = conexao.prepareStatement(sql)) {

      statement.setInt(1, idProduto);
      int rowsAffected = statement.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public String[][] listarProdutos() {
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement
            .executeQuery("SELECT nome, preco FROM produtos WHERE ativo = true")) {

      ArrayList<String[]> lista = new ArrayList<>();
      while (resultSet.next()) {
        String nome = resultSet.getString("nome");
        String preco = Double.toString(resultSet.getDouble("preco"));

        lista.add(new String[] { nome, preco });
      }

      return lista.toArray(new String[0][]);
    } catch (SQLException e) {
      e.printStackTrace();
      return new String[0][0];
    }

  }

  public Double getPrice(String produto) {
    if (produto == null) {
      return 0.0;
    }
    Double preco = 0.0;
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "SELECT preco FROM produtos WHERE nome = '" + produto + "'";

      try (PreparedStatement statement = conexao.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        preco = Double.parseDouble(resultSet.getString("preco"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return preco;
  }

  public int getProdutoID(String nome) {
    int id = 0;
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "SELECT id FROM produtos WHERE nome = '" + nome + "'";

      try (PreparedStatement statement = conexao.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
          id = Integer.parseInt(resultSet.getString("id"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }
}
