import java.util.Scanner;

public class q4 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Escolha o tipo de imovel:");
    System.out.println("1 - Novo");
    System.out.println("2 - Velho");
    int escolhaTipo = scanner.nextInt();

    if (escolhaTipo == 1) {
      Novo imovel = new Novo();
      System.out.println("Imovel: Novo");
      System.out.println("Valor final do imovel: R$" + imovel.getPrecoAdicional());
    } else if (escolhaTipo == 2) {
      Velho imovel = new Velho();
      System.out.println("Imovel: Velho");
      System.out.println("Valor final do imovel: R$" + imovel.getPrecoDesconto());
    } else {
      System.out.println("Escolha de imovel invalida.");
    }

    scanner.close();
  }

  public static class Imovel {
    protected double preco = 100000.0;

    public double getPreco() {
      return preco;
    }
  }

  public static class Novo extends Imovel {
    protected double adicional = 20000.0;

    public double getPrecoAdicional() {
      return super.getPreco() + adicional;
    }
  }

  public static class Velho extends Imovel {
    protected double desconto = 15000.0;

    public double getPrecoDesconto() {
      return super.getPreco() - desconto;
    }
  }
}
