import java.util.Scanner;

public class q3 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Escolha o tipo de ingresso:");
    System.out.println("1 - Normal");
    System.out.println("2 - VIP");
    int escolhaTipo = scanner.nextInt();

    if (escolhaTipo == 1) {
      Ingresso ingresso = new Normal();
      System.out.println("Ingresso: Normal");
      System.out.println("Valor do ingresso: R$" + ingresso.getValor());
    } else if (escolhaTipo == 2) {
      System.out.println("Escolha o tipo VIP:");
      System.out.println("1 - Camarote Superior");
      System.out.println("2 - Camarote Inferior");
      int escolhaVIP = scanner.nextInt();

      if (escolhaVIP == 1) {
        Ingresso ingresso = new CamaroteSuperior();
        System.out.println("Ingresso: Camarote Superior (VIP)");
        System.out.println("Valor do ingresso: R$" + ingresso.getValor());
      } else if (escolhaVIP == 2) {
        Ingresso ingresso = new CamaroteInferior();
        System.out.println("Ingresso: Camarote Inferior (VIP)");
        System.out.println("Valor do ingresso: R$" + ingresso.getValor());
      } else {
        System.out.println("Escolha VIP inválida.");
      }
    } else {
      System.out.println("Escolha de ingresso inválida.");
    }

    scanner.close();
  }

  public static class Ingresso {
    double valor = 100.0;

    public double getValor() {
      return valor;
    }

    public void setValor(double valor) {
      this.valor = valor;
    }

    public void imprimeValor() {
      System.out.println("Valor do ingresso: " + this.valor);
    }
  }

  public static class VIP extends Ingresso {
    double valorAdicional = 50.0;

    public double getValorAdicional() {
      return valorAdicional;
    }

    public void setValorAdicional(double valorAdicional) {
      this.valorAdicional = valorAdicional;
    }

    public void imprimeValor() {
      System.out.println("Valor do ingresso VIP: " + (this.valor + this.valorAdicional));
    }
  }

  public static class Normal extends Ingresso {
    public void imprimeValor() {
      System.out.println("Valor do ingresso normal: " + this.valor);
    }
  }

  public static class CamaroteInferior extends VIP {
    String localizacao;

    public void imprimeLocalizacao() {
      System.out.println("Localizacao do camarote inferior: " + this.localizacao);
    }

    public String getLocalizacao() {
      return localizacao;
    }

    public void setLocalizacao(String localizacao) {
      this.localizacao = localizacao;
    }
  }

  public static class CamaroteSuperior extends VIP {
    double valorAdicionalCamaroteSuperior;

    public void imprimeValor() {
      System.out.println(
          "Valor do ingresso VIP: " + (this.valor + this.valorAdicional + this.valorAdicionalCamaroteSuperior));
    }

    public double getValorAdicionalCamaroteSuperior() {
      return valorAdicionalCamaroteSuperior;
    }

    public void setValorAdicionalCamaroteSuperior(double valorAdicionalCamaroteSuperior) {
      this.valorAdicionalCamaroteSuperior = valorAdicionalCamaroteSuperior;
    }
  }
}
