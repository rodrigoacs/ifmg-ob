public class Carro {
  String modelo;
  String placa;
  String cor;
  String marca;

  Carro(String modelo, String placa, String cor, String marca) {
    this.modelo = modelo;
    this.placa = placa;
    this.cor = cor;
    this.marca = marca;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public String getCor() {
    return cor;
  }

  public void setCor(String cor) {
    this.cor = cor;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }
}
