public class q3 {
  public static void main(String[] args) {
    // Teste 1: Fatura válida com valores positivos
    Fatura fatura1 = new Fatura();
    fatura1.numero = "12345";
    fatura1.descricao = "Produto A";
    fatura1.qtdeComprada = 5;
    fatura1.preco = 10.0;

    System.out.println("Total da Fatura 1: " + fatura1.getTotalFatura());

    // Teste 2: Fatura com quantidade comprada negativa
    Fatura fatura2 = new Fatura();
    fatura2.numero = "67890";
    fatura2.descricao = "Produto B";
    fatura2.qtdeComprada = -2;
    fatura2.preco = 15.0;

    System.out.println("Total da Fatura 2: " + fatura2.getTotalFatura());

    // Teste 3: Fatura com preço negativo
    Fatura fatura3 = new Fatura();
    fatura3.numero = "54321";
    fatura3.descricao = "Produto C";
    fatura3.qtdeComprada = 3;
    fatura3.preco = -8.0;

    System.out.println("Total da Fatura 3: " + fatura3.getTotalFatura());

    // Teste 4: Fatura com quantidade e preço negativos
    Fatura fatura4 = new Fatura();
    fatura4.numero = "98765";
    fatura4.descricao = "Produto D";
    fatura4.qtdeComprada = -1;
    fatura4.preco = -5.0;

    System.out.println("Total da Fatura 4: " + fatura4.getTotalFatura());
  }
}

class Fatura {
  String numero;
  String descricao;
  int qtdeComprada;
  double preco;

  public double getTotalFatura() {
    if (preco < 0 || qtdeComprada < 0) {
      return 0;
    }
    return qtdeComprada * preco;
  }
}
