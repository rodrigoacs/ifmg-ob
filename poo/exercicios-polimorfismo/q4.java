import java.util.ArrayList;
import java.util.Scanner;

public class q4 {

  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    ArrayList<Veiculos> veiculos = new ArrayList<Veiculos>();

    System.out.println("Digite o numero de veiculos: ");
    int n = scanner.nextInt();

    for (int i = 0; i < n; i++) {
      System.out.println("Digite o tipo de veiculo (1 - Carro, 2 - Moto): ");
      int tipo = scanner.nextInt();

      System.out.println("Digite o modelo: ");
      String modelo = scanner.next();

      System.out.println("Digite o preco: ");
      double preco = scanner.nextDouble();

      if (tipo == 1) {
        System.out.println("Digite a quilometragem: ");
        double km = scanner.nextDouble();

        veiculos.add(new Carro(modelo, preco, km));
      } else {
        System.out.println("Digite o ano: ");
        int ano = scanner.nextInt();

        veiculos.add(new Moto(modelo, preco, ano));
      }
    }

    for (Veiculos veiculo : veiculos) {
      veiculo.printDados();
      System.out.println("Preco final: " + veiculo.getPreco());
    }

  }

  public static class Veiculos {
    private String modelo;
    private double preco;

    public String getModelo() {
      return modelo;
    }

    public void setModelo(String modelo) {
      this.modelo = modelo;
    }

    public double getPreco() {
      return preco;
    }

    public void setPreco(double preco) {
      this.preco = preco;
    }

    public Veiculos(String modelo, double preco) {
      this.modelo = modelo;
      this.preco = preco;
    }

    public void printDados() {
      System.out.println("Modelo: " + this.modelo);
      System.out.println("Preco: " + this.preco);
    }
  }

  public static class Moto extends Veiculos {
    int ano;

    public int getAno() {
      return ano;
    }

    public void setAno(int ano) {
      this.ano = ano;
    }

    public Moto(String modelo, double preco, int ano) {
      super(modelo, preco);
      this.ano = ano;
    }

    public void printDados() {
      super.printDados();
      System.out.println("Ano: " + this.ano);
    }

    public void insertData(String modelo, double preco, int ano) {
      this.setModelo(modelo);
      this.setPreco(preco);
      this.setAno(ano);
    }

    @Override
    public double getPreco() {
      return ano >= 2008 ? super.getPreco() * 1.1 : super.getPreco();
    }
  }

  public static class Carro extends Veiculos {
    double km;

    public Carro(String modelo, double preco, double km) {
      super(modelo, preco);
      this.km = km;
    }

    public double getKm() {
      return km;
    }

    public void setKm(double km) {
      this.km = km;
    }

    @Override
    public double getPreco() {
      return km >= 100000 ? super.getPreco() * 0.92 : super.getPreco();
    }
  }
}
