public class q2b {
  public static void main(String[] args) {
    // Criando uma pessoa
    Pessoa pessoa = new Pessoa();
    pessoa.nome = "Joao";
    pessoa.idade = 30;
    pessoa.dinheiro = (float) 100.0;

    System.out.println("Dados da Pessoa:");
    System.out.println("Nome: " + pessoa.nome);
    System.out.println("Idade: " + pessoa.idade);
    System.out.println("Dinheiro: " + pessoa.dinheiro);

    // Criando uma pessoa rica
    Rica rica = new Rica();
    rica.nome = "Maria";
    rica.idade = 35;
    rica.dinheiro = (float) 1000.0;

    System.out.println("\nDados da Pessoa Rica:");
    System.out.println("Nome: " + rica.nome);
    System.out.println("Idade: " + rica.idade);
    System.out.println("Dinheiro: " + rica.dinheiro);

    // Testando o método "trabalha" para a pessoa rica
    rica.trabalha();
    System.out.println("\nDepois de trabalhar, o dinheiro da Pessoa Rica e: " + rica.dinheiro);

    // Criando uma pessoa pobre
    Pobre pobre = new Pobre();
    pobre.nome = "Pedro";
    pobre.idade = 25;
    pobre.dinheiro = (float) 5.0;

    System.out.println("\nDados da Pessoa Pobre:");
    System.out.println("Nome: " + pobre.nome);
    System.out.println("Idade: " + pobre.idade);
    System.out.println("Dinheiro: " + pobre.dinheiro);

    // Testando o método "mendiga" para a pessoa pobre
    pobre.mendiga();
    System.out.println("\nDepois de mendigar, o dinheiro da Pessoa Pobre e: " + pobre.dinheiro);
  }

  public static class Pessoa {
    String nome;
    int idade;
    float dinheiro;

    public Pessoa() {

    }
  }

  public static class Rica extends Pessoa {
    public void trabalha() {
      this.dinheiro += 1200.0;
    }
  }

  public static class Pobre extends Pessoa {
    public void mendiga() {
      this.dinheiro += 0.1;
    }
  }

}
