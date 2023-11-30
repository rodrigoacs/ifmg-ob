import java.util.Scanner;

public class aula {
  public static void main(String[] args) {
    Funcionario f1 = new Secretaria("Maria", "0102");
    Gerente g = new Gerente(2147483647, "Joao", "0304", "Compras");
    Diretor d = new Diretor("Paula", "0706", 5678);
    Secretaria s = new Secretaria("Marta", "0943");

    ControleBonificacao cb = new ControleBonificacao();

    f1.setSalario(1500.0);
    g.setSalario(5000.0);
    d.setSalario(15000);
    s.setSalario(2000);

    // System.out.println("Eu sou o(a): " + f1.getNome() + " e meu salario eh: " +
    // f1.getSalario());
    // System.out.println("Sou um funcionario(a) e meu bonus eh: " +
    // f1.calculaBonificacao());
    // System.out.println("Eu sou o(a): " + g.nome + " e meu salario eh: " +
    // g.getSalario());
    // System.out.println("Sou um gerente e meu bonus eh: " +
    // g.calculaBonificacao());
    // System.out.println("Eu sou o(a) diretor(a): " + d.getNome());

    cb.regitra(f1);
    cb.regitra(g);
    cb.regitra(d);
    cb.regitra(s);

    SistemaInterno si = new SistemaInterno();
    si.login(g);

    // System.out.println("Total de bonificacao a ser paga pela empresa eh: " + cb.getTotalDeBonif());
  }

  public static class ControleBonificacao {
    private double totalDeBonif = 0;

    public void regitra(Funcionario f) {
      this.totalDeBonif += f.calculaBonificacao();
    }

    public double getTotalDeBonif() {
      return this.totalDeBonif;
    }
  }

  public static class DeptosAutorizados {
    public final static String DEPTOS[] = { "Compras", "Financeiro", "Cliente", "Produção", "RH" };

    public static boolean validaDepto(String dpto) {
      boolean saida = false;
      for (int i = 0; i < DEPTOS.length; i++) {
        if (DEPTOS[i].equals(dpto)) {
          saida = true;
        }
      }
      return saida;
    }
  }

  public static class Diretor extends Funcionario {
    private int numDepto;
    private int senha;

    public Diretor(String nome, String cpf, int senha) {
      super(nome, cpf);
      this.numDepto = 4;
      this.senha = senha;
    }

    public boolean autentica(int senha) {
      System.out.println("Sou o diretor " + this.nome + " e estou validando minha senha!");
      return senha == this.senha ? true : false;
    }

    @Override
    public double calculaBonificacao() {
      return (this.salario * 1.5);
    }
  }

  public static abstract class Funcionario {
    protected String nome;
    private String cpf;
    protected double salario;

    public Funcionario(String nome, String cpf) {
      this.nome = nome;
      this.cpf = cpf;
      this.salario = 0.0;
    }

    public abstract double calculaBonificacao();

    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public String getCpf() {
      return cpf;
    }

    public void setCpf(String cpf) {
      this.cpf = cpf;
    }

    public double getSalario() {
      return salario;
    }

    public void setSalario(double salario) {
      this.salario = salario;
    }
  }

  public static class Gerente extends FunAuntenticavel {
    private int senha;
    private int numFuncGerenciados;
    private String depto;

    public Gerente(int senha, String nome, String cpf, String depto) {
      super(nome, cpf);
      this.senha = senha;
      this.depto = depto;
    }

    public boolean autentica(int senha) {
      System.out.println("Sou o gerente " + this.nome + " e estou validando minha senha!");
      return senha == this.senha && DeptosAutorizados.validaDepto(this.depto) ? true : false;
    }

    public double calculaBonificacao() {
      return (this.salario * 1.4);
    }

    public int getSenha() {
      return senha;
    }

    public void setSenha(int senha) {
      this.senha = senha;
    }

    public int getNumFuncGerenciados() {
      return numFuncGerenciados;
    }

    public void setNumFuncGerenciados(int numFuncGerenciados) {
      this.numFuncGerenciados = numFuncGerenciados;
    }
  }

  public static class Secretaria extends Funcionario {
    public Secretaria(String nome, String cpf) {
      super(nome, cpf);
    }

    public double calculaBonificacao() {
      return (this.salario * 1.25);
    }
  }

  public static class SistemaInterno {
    Scanner teclado = new Scanner(System.in);

    public void login(FunAuntenticavel fun) {
      System.out.println("Informe sua senha: ");
      System.out.println(fun.autentica(teclado.nextInt()) ? "Acesso Permitido!" : "Acesso Negado!");
    }
  }

  public static abstract class FunAuntenticavel extends Funcionario {
    public FunAuntenticavel(String nome, String cpf) {
      super(nome, cpf);
    }

    public abstract boolean autentica(int senha);
  }
}