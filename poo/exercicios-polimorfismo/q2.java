import java.util.Scanner;

public class q2 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    DVD d = new DVD();
    d.inserirDados();
    d.printDados();

    CD c = new CD();
    c.inserirDados();
    c.printDados();

  }

  static class Midia {
    private int codigo;
    private double preco;
    private String nome;

    public Midia(int codigo, double preco, String nome) {
      this.codigo = codigo;
      this.preco = preco;
      this.nome = nome;
    }

    public Midia() {
    }

    public int getCodigo() {
      return codigo;
    }

    public void setCodigo(int codigo) {
      this.codigo = codigo;
    }

    public double getPreco() {
      return preco;
    }

    public void setPreco(double preco) {
      this.preco = preco;
    }

    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public String getTipo() {
      return this.getClass().getSimpleName();
    }

    public String getDetalhes() {
      return "nome: " + this.getNome() + "\npreco: " + this.getPreco() + "\ntipo: " + this.getTipo();
    }

    public void printDados() {
      System.out.println(this.getTipo());
      System.out.println(this.getDetalhes());
    }

    public void inserirDados() {
      System.out.println("digite o codigo: ");
      this.codigo = in.nextInt();
      System.out.println("digite o preco: ");
      this.preco = in.nextDouble();
      System.out.println("digite o nome: ");
      this.nome = in.next();
    }
  }

  static public class DVD extends Midia {
    private int nFaixas;

    public DVD(int codigo, double preco, String nome, int nFaixas) {
      super(codigo, preco, nome);
      this.nFaixas = nFaixas;
    }

    public DVD() {

    }

    public int getnFaixas() {
      return nFaixas;
    }

    public void setnFaixas(int nFaixas) {
      this.nFaixas = nFaixas;
    }

    @Override
    public String getDetalhes() {
      return super.getDetalhes() + "\nnumero de faixas: " + this.getnFaixas() + "\n";
    }

    @Override
    public void inserirDados() {
      super.inserirDados();
      System.out.println("digite o numero de faixas: ");
      this.nFaixas = in.nextInt();
    }
  }

  static public class CD extends Midia {
    int nMusicas;

    public CD(int codigo, double preco, String nome, int nMusicas) {
      super(codigo, preco, nome);
      this.nMusicas = nMusicas;
    }

    public CD() {
    }

    public int getnMusicas() {
      return nMusicas;
    }

    public void setnMusicas(int nMusicas) {
      this.nMusicas = nMusicas;
    }

    @Override
    public String getDetalhes() {
      return super.getDetalhes() + "\nnumero de musicas: " + this.getnMusicas() + "\n";
    }

    @Override
    public void inserirDados() {
      super.inserirDados();
      System.out.println("digite o numero de musicas: ");
      this.nMusicas = in.nextInt();
    }
  }
}
