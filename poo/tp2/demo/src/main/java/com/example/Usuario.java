package com.example;

public abstract class Usuario {
  protected String cpf;

  public Usuario(String cpf) {
    this.cpf = cpf;
  }

  public Usuario() {
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

}
