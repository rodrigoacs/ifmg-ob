import java.util.ArrayList;

public class Pais {
  private String ISO, nome;
  private int populacao;
  private double dimensao;
  private ArrayList<String> vizinhos = new ArrayList<>();

  public String getISO() {
    return ISO;
  }

  public void setISO(String ISO) {
    this.ISO = ISO;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getPopulacao() {
    return populacao;
  }

  public void setPopulacao(int populacao) {
    this.populacao = populacao;
  }

  public double getDimensao() {
    return dimensao;
  }

  public void setDimensao(double dimensao) {
    this.dimensao = dimensao;
  }

  public ArrayList<String> getVizinhos() {
    return vizinhos;
  }

  public void setVizinhos(ArrayList<String> vizinhos) {
    this.vizinhos = vizinhos;
  }

  public Pais(String ISO, String nome, int populacao, double dimensao) {
    this.setISO(ISO);
    this.setNome(nome);
    this.setPopulacao(populacao);
    this.setDimensao(dimensao);
  }

  public boolean comparaPaises(Pais p) {
    return p.getISO().equals(this.getISO());
  }

  public boolean eVizinho(Pais p) {
    return this.getVizinhos().contains(p.getNome());
  }

  public double densidadePopulacional() {
    return this.getPopulacao() / this.getDimensao();
  }

  public ArrayList<String> vizinhosEmComum(Pais p) {
    ArrayList<String> vizinhosEmComum = new ArrayList<>();
    for (String vizinho : p.getVizinhos()) {
      if (this.getVizinhos().contains(vizinho)) {
        vizinhosEmComum.add(vizinho);
      }
    }
    return vizinhosEmComum;
  }
}