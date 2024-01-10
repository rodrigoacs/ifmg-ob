package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientePrime extends Cliente {
  protected Double desconto;
  protected Boolean freteGratis = true;

  public ClientePrime(String nome, String rua, String bairro, String numero, String cidade, String estado, String cpf,
      Double desconto) {
    super(nome, rua, bairro, numero, cidade, estado, cpf);
    this.desconto = desconto;
  }

  public int insert(ClientePrime c) {
    String sql = "INSERT INTO clientes (nome, rua, bairro, numero, cidade, estado, cpf, prime, desconto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexao = DriverManager.getConnection(url, usuario, senha);) {
      PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, c.nome);
      statement.setString(2, c.rua);
      statement.setString(3, c.bairro);
      statement.setString(4, c.numero);
      statement.setString(5, c.cidade);
      statement.setString(6, c.estado);
      statement.setString(7, c.cpf);
      statement.setBoolean(8, true);
      statement.setDouble(9, c.desconto);

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

  public static Double getDesconto(int id) {
    Double desconto = 0.0;
    try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
      String sql = "SELECT desconto FROM clientes WHERE id = '" + id + "'";

      try (PreparedStatement statement = conexao.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
          desconto = Double.parseDouble(resultSet.getString("desconto"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return desconto;
  }
}
