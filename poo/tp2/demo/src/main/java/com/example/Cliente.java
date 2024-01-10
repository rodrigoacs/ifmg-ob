package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente extends Usuario implements DB<Cliente> {

  protected String nome;
  protected String rua;
  protected String bairro;
  protected String numero;
  protected String cidade;
  protected String estado;

  public Cliente() {

  }

  public Cliente(String nome, String rua, String bairro, String numero, String cidade, String estado, String cpf) {
    super(cpf);
    this.nome = nome;
    this.rua = rua;
    this.bairro = bairro;
    this.numero = numero;
    this.cidade = cidade;
    this.estado = estado;
  }

  @Override
  public int insert(Cliente c) {
    String sql = "INSERT INTO clientes (nome, rua, bairro, numero, cidade, estado, cpf, prime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);) {
      PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, c.nome);
      statement.setString(2, c.rua);
      statement.setString(3, c.bairro);
      statement.setString(4, c.numero);
      statement.setString(5, c.cidade);
      statement.setString(6, c.estado);
      statement.setString(7, c.cpf);
      statement.setBoolean(8, false);

      int rowsAffected = statement.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Falha ao inserir o cliente.");
      }

      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1);
        } else {
          throw new SQLException("Falha ao obter o ID do cliente.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  @Override
  public String[] get() {
    String[] clientes = null;

    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "SELECT * FROM clientes";
      String countQuery = "SELECT COUNT(id) FROM clientes";
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

        clientes = new String[Integer.parseInt(countResult)];

        int index = 0;
        while (resultSet.next()) {
          String nome = resultSet.getString("nome");
          clientes[index++] = nome;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return clientes;
  }

  public static boolean update(int id, String nome, String rua, String bairro, String numero, String cidade,
      String estado,
      String cpf) {
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "UPDATE clientes SET nome = ?, rua = ?, bairro = ?, numero = ?, cidade = ?, estado = ?, cpf = ?, desconto = 0.0 WHERE id = ?";
      try (PreparedStatement statement = conexao.prepareStatement(sql)) {
        statement.setString(1, nome);
        statement.setString(2, rua);
        statement.setString(3, bairro);
        statement.setString(4, numero);
        statement.setString(5, cidade);
        statement.setString(6, estado);
        statement.setString(7, cpf);
        statement.setInt(8, id);

        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean delete(int id) {
    String sql = "UPDATE clientes SET ativo = FALSE WHERE id = ?";

    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
        PreparedStatement statement = conexao.prepareStatement(sql)) {

      statement.setInt(1, id);
      int rowsAffected = statement.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public int getId(String nome) {
    int id = 0;
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "SELECT id FROM clientes WHERE nome = '" + nome + "'";

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

  public String[][] listarClientes() {
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement
            .executeQuery(
                "SELECT case when prime = 1 then 'sim' else 'nao' end as prime, nome, rua, bairro, numero, cidade, estado, cpf FROM clientes WHERE ativo = true")) {

      ArrayList<String[]> lista = new ArrayList<>();
      while (resultSet.next()) {
        String prime = resultSet.getString("prime");
        String nome = resultSet.getString("nome");
        String rua = resultSet.getString("rua");
        String bairro = resultSet.getString("bairro");
        String numero = resultSet.getString("numero");
        String cidade = resultSet.getString("cidade");
        String estado = resultSet.getString("estado");
        String cpf = resultSet.getString("cpf");

        lista.add(new String[] { prime, nome, rua, bairro, numero, cidade, estado, cpf });
      }

      return lista.toArray(new String[0][]);
    } catch (SQLException e) {
      e.printStackTrace();
      return new String[0][0];
    }
  }

  public static boolean verificaPrime(int id) {

    boolean desconto = false;
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "SELECT prime FROM clientes WHERE id = '" + id + "'";

      try (PreparedStatement statement = conexao.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
          desconto = resultSet.getBoolean("prime");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return desconto;
  }

}
