public class q1 {
  public static void main(String[] args) {
    // Teste para a classe Administrativo
    Administrativo administrativo = new Administrativo();
    administrativo.setNome("Carlos");
    administrativo.setSalario(3500.0);
    administrativo.setNumeroDeMatricula(67890);
    administrativo.setTurno("noite");
    administrativo.setAdicionalNoturno(10.0);
    administrativo.exibeDados();

    // Teste para a classe Tecnico
    Tecnico tecnico = new Tecnico();
    tecnico.setNome("Ana");
    tecnico.setSalario(2800.0);
    tecnico.setNumeroDeMatricula(54321);
    tecnico.setBonusSalarial(5.0);
    tecnico.exibeDados();
  }

  static class Funcionario {
    protected String nome;
    protected double salario;

    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public double getSalario() {
      return salario;
    }

    public void setSalario(double salario) {
      this.salario = salario;
    }

    public void addAumento(double valor) {
      this.salario += valor;
    }

    public double ganhoAnual() {
      return this.salario * 12;
    }

    public void exibeDados() {
      System.out.println("nome: " + this.nome);
      System.out.println("salario: " + this.salario);
      System.out.println("salario anual: " + this.ganhoAnual());
    }
  }

  static class Assistente extends Funcionario {
    protected int numeroDeMatricula;

    public int getNumeroDeMatricula() {
      return numeroDeMatricula;
    }

    public void setNumeroDeMatricula(int numeroDeMatricula) {
      this.numeroDeMatricula = numeroDeMatricula;
    }

    public void exibeDados() {
      System.out.println("nome: " + this.nome);
      System.out.println("numero de matricula: " + this.numeroDeMatricula);
      System.out.println("salario: " + this.salario);
      System.out.println("salario anual: " + this.ganhoAnual());
    }
  }

  static class Administrativo extends Assistente {
    protected String turno;
    protected double adicionalNoturno;

    public String getTurno() {
      return turno;
    }

    public void setTurno(String turno) {
      this.turno = turno;
    }

    public double getAdicionalNoturno() {
      return adicionalNoturno;
    }

    public void setAdicionalNoturno(double adicionalNoturno) {
      this.adicionalNoturno = adicionalNoturno;
    }

    public double ganhoAnual() {
      if (turno == "noite") {
        return this.salario * 12;
      } else {
        return (this.salario + ((this.salario / 100) * this.adicionalNoturno)) * 12;
      }
    }
  }

  static class Tecnico extends Assistente {
    protected double bonusSalarial;

    public double getBonusSalarial() {
      return bonusSalarial;
    }

    public void setBonusSalarial(double bonusSalarial) {
      this.bonusSalarial = bonusSalarial;
    }

    public double ganhoAnual() {
      return (this.salario + ((this.salario / 100) * this.bonusSalarial)) * 12;
    }
  }
}
