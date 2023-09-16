public class q1 {

  public static void main(String[] args) {
    Funcionario e1 = new Funcionario();
    Funcionario e2 = new Funcionario();
    e1.setNome("Maria");
    e1.setSobrenome("Santos");
    e1.setSalarioMensal(3500.0);
    e2.setNome("Joao");
    e2.setSobrenome("Silva");
    e2.setSalarioMensal(3000.0);

    System.out.println("Salario anual de " + e1.getNome() + ": " + e1.calcularSalarioAnual());
    System.out.println("Salario anual de " + e2.getNome() + ": " + e2.calcularSalarioAnual());

    e1.aumentarSalario(10);
    e2.aumentarSalario(10);

    System.out.println("Novo salario anual de " + e1.getNome() + ": " + e1.calcularSalarioAnual());
    System.out.println("Novo salario anual de " + e2.getNome() + ": " + e2.calcularSalarioAnual());
  }

  static class Funcionario {
    private String nome;
    private String sobrenome;
    private double salarioMensal;

    public String getNome() {
      return this.nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public String getSobrenome() {
      return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
      this.sobrenome = sobrenome;
    }

    public double getSalarioMensal() {
      return this.salarioMensal;
    }

    public void setSalarioMensal(double salarioMensal) {
      if (salarioMensal < 0) {
        this.salarioMensal = 0;
      } else {
        this.salarioMensal = salarioMensal;
      }
    }

    public double calcularSalarioAnual() {
      return this.salarioMensal * 12;
    }

    public void aumentarSalario(double porcentagem) {
      this.salarioMensal = this.salarioMensal + (this.salarioMensal * (porcentagem / 100));
    }
  }
}
