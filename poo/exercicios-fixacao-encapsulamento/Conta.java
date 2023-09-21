public class Conta {

  private int numero;
  private Cliente titular = new Cliente();
  private double saldo;

  public int getNumero() {
    return this.numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public Cliente getTitular() {
    return this.titular;
  }

  public void setTitular(Cliente titular) {
    this.titular = titular;
  }

  public double getSaldo() {
    return this.saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  void deposito(double valorDeposito) {
    this.saldo += valorDeposito;
  }

  boolean sacar(double valor) {
    if (this.saldo >= valor && valor > 0) {
      this.saldo -= valor;
      return true;
    } else {
      return false;
    }
  }

  boolean transferir(Conta destino, double valor) {
    if (this.sacar(valor)) {
      destino.deposito(valor);
      return true;
    } else {
      return false;
    }
  }

}