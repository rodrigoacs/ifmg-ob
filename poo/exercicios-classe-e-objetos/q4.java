public class q4 {
  public static void main(String[] args) {
    Empregado empregado1 = new Empregado();
    Empregado empregado2 = new Empregado();
    empregado1.informarDados("Maria", "Santos", 3500.0);
    empregado2.informarDados("Joao", "Silva", 3000.0);

    // Exibindo o salário anual de cada Empregado
    System.out.println("Salario anual de " + empregado1.nome + ": " + empregado1.calcularSalarioAnual());
    System.out.println("Salario anual de " + empregado2.nome + ": " + empregado2.calcularSalarioAnual());

    // Dando um aumento de 10% a ambos os Empregados
    empregado1.aumentarSalario(10);
    empregado2.aumentarSalario(10);

    // Exibindo o novo salário anual de cada Empregado após o aumento
    System.out.println("Novo salario anual de " + empregado1.nome + ": " + empregado1.calcularSalarioAnual());
    System.out.println("Novo salario anual de " + empregado2.nome + ": " + empregado2.calcularSalarioAnual());
  }
}

class Empregado {
  String nome;
  String sobrenome;
  double salarioMensal;

  public void informarDados(String nome, String sobrenome, double salarioMensal) {
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.salarioMensal = salarioMensal;
  }

  public double calcularSalarioAnual() {
    return this.salarioMensal * 12;
  }

  public void aumentarSalario(double porcentagem) {
    this.salarioMensal = this.salarioMensal + (this.salarioMensal * (porcentagem / 100));
  }
}
