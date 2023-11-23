import java.util.ArrayList;

public class q1 {
  public static void main(String[] args) {
    ArrayList<Empregado> lista = new ArrayList<Empregado>();

    Empregado a = new Assalariado("a_nome", "a_sobrenome", "a_cpf", 10);
    Empregado b = new Assalariado("b_nome", "b_sobrenome", "b_cpf", 10);
    Empregado c = new Comissionado("c_nome", "c_sobrenome", "c_cpf", 1200, 10);
    Empregado d = new Comissionado("d_nome", "d_sobrenome", "d_cpf", 1200, 10);
    Empregado e = new Horista("e_nome", "e_sobrenome", "e_cpf", 10, 8);
    Empregado f = new Horista("f_nome", "f_sobrenome", "f_cpf", 10, 8);

    lista.add(a);
    lista.add(b);
    lista.add(c);
    lista.add(d);
    lista.add(e);
    lista.add(f);

    double total = 0;
    for (Empregado empregado : lista) {
      System.out.println(empregado.getNome() + ' ' + empregado.vencimento());
      total += empregado.vencimento();
    }
    System.out.println("total: " + total);

  }

  static abstract class Empregado {
    private String nome;
    private String sobrenome;
    private String cpf;

    public Empregado() {
      this.nome = null;
      this.sobrenome = null;
      this.cpf = null;
    }

    public Empregado(String nome, String sobrenome, String cpf) {
      this.nome = nome;
      this.sobrenome = sobrenome;
      this.cpf = cpf;
    }

    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public String getSobrenome() {
      return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
      this.sobrenome = sobrenome;
    }

    public String getCpf() {
      return cpf;
    }

    public void setCpf(String cpf) {
      this.cpf = cpf;
    }

    abstract double vencimento();
  }

  static class Assalariado extends Empregado {
    private double salario;

    public Assalariado(double salario) {
      this.salario = salario;
    }

    public Assalariado(String nome, String sobrenome, String cpf, double salario) {
      super(nome, sobrenome, cpf);
      this.salario = salario;
    }

    public double getSalario() {
      return salario;
    }

    public void setSalario(double salario) {
      this.salario = salario;
    }

    @Override
    double vencimento() {
      return getSalario();
    }

  }

  static class Comissionado extends Empregado {
    private double totalVenda;
    private double taxaComissao;

    public Comissionado(double totalVenda, double taxaComissao) {
      this.totalVenda = totalVenda;
      this.taxaComissao = taxaComissao;
    }

    public Comissionado(String nome, String sobrenome, String cpf, double totalVenda, double taxaComissao) {
      super(nome, sobrenome, cpf);
      this.totalVenda = totalVenda;
      this.taxaComissao = taxaComissao;
    }

    public double getTotalVenda() {
      return totalVenda;
    }

    public void setTotalVenda(double totalVenda) {
      this.totalVenda = totalVenda;
    }

    public double getTaxaComissao() {
      return taxaComissao;
    }

    public void setTaxaComissao(double taxaComissao) {
      this.taxaComissao = taxaComissao;
    }

    @Override
    double vencimento() {
      return getTotalVenda() * getTaxaComissao() / 100;
    }
  }

  static class Horista extends Empregado {
    private double precoHora;
    private double horasTrabalhadas;

    public Horista(double precoHora, double horasTrabalhadas) {
      this.precoHora = precoHora;
      this.horasTrabalhadas = horasTrabalhadas;
    }

    public Horista(String nome, String sobrenome, String cpf, double precoHora, double horasTrabalhadas) {
      super(nome, sobrenome, cpf);
      this.precoHora = precoHora;
      this.horasTrabalhadas = horasTrabalhadas;
    }

    public double getPrecoHora() {
      return precoHora;
    }

    public void setPrecoHora(double precoHora) {
      this.precoHora = precoHora;
    }

    public double getHorasTrabalhadas() {
      return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(double horasTrabalhadas) {
      this.horasTrabalhadas = horasTrabalhadas;
    }

    @Override
    double vencimento() {
      return getHorasTrabalhadas() * getPrecoHora();
    }
  }
}
