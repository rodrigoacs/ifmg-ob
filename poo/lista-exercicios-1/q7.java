// 7) Escreva em Java uma classe Continente. Um continente possui um nome e é composto por um conjunto de países. Forneça os membros de classe a seguir:
//   a) Construtor que inicialize o nome do continente;
//   b) Um método que permita adicionar países aos continentes;
//   c) Um método que retorne a dimensão total do continente;
//   d) Um método que retorne a população total do continente;
//   e) Um método que retorne a densidade populacional do continente;
//   f) Um método que retorne o país com maior população no continente;
//   g) Um método que retorne o país com menor população no continente;
//   h) Um método que retorne o país de maior dimensão territorial no continente;
//   i) Um método que retorne o país de menor dimensão territorial no continente;
//   j) Um método que retorne a razão territorial do maior pais em relação ao menor país.

import java.util.ArrayList;

public class q7 {
  public static void main(String[] args) {
    // Criar alguns países
    Pais brasil = new Pais("BR", "Brasil", 211000000, 8515767.049);
    Pais argentina = new Pais("AR", "Argentina", 45195777, 2780400.0);
    Pais uruguai = new Pais("UY", "Uruguai", 3456750, 176215.0);

    // Criar um continente e adicionar países a ele
    Continente americaDoSul = new Continente("America do Sul");
    americaDoSul.adiconaPais(brasil);
    americaDoSul.adiconaPais(argentina);
    americaDoSul.adiconaPais(uruguai);

    // Testar os métodos da classe Continente
    System.out.println("Continente: " + americaDoSul.getNome());
    System.out.println("Populacao Total: " + americaDoSul.populacaoTotal());
    System.out.println("Dimensao Total: " + americaDoSul.dimensaoTotal());
    System.out.println("Densidade Populacional: " + americaDoSul.densidadePopulacional());
    System.out.println("Pais com Maior Populacao: " + americaDoSul.maiorPopulacao().getNome());
    System.out.println("Pais com Menor Populacao: " + americaDoSul.menorPopulacao().getNome());
    System.out.println("Pais com Maior Dimensão: " + americaDoSul.maiorDimensao().getNome());
    System.out.println("Pais com Menor Dimensão: " + americaDoSul.menorDimensao().getNome());
    System.out.println("Razao Territorial: " + americaDoSul.razaoTerritorial());
  }
}

class Continente {
  String nome;
  ArrayList<Pais> paises = new ArrayList<>();

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public ArrayList<Pais> getPaises() {
    return paises;
  }

  public void setPaises(ArrayList<Pais> paises) {
    this.paises = paises;
  }

  public Continente(String nome) {
    this.setNome(nome);
  }

  public void adiconaPais(Pais p) {
    this.getPaises().add(p);
  }

  public double dimensaoTotal() {
    double dimensaoTotal = 0;
    for (Pais pais : this.getPaises()) {
      dimensaoTotal += pais.getDimensao();
    }
    return dimensaoTotal;
  }

  public double populacaoTotal() {
    double populacaoTotal = 0;
    for (Pais pais : this.getPaises()) {
      populacaoTotal += pais.getPopulacao();
    }
    return populacaoTotal;
  }

  public double densidadePopulacional() {
    return this.populacaoTotal() / this.dimensaoTotal();
  }

  public Pais maiorPopulacao() {
    Pais maior = new Pais("null", "null", 0, 0);
    for (Pais pais : this.getPaises()) {
      if (pais.getPopulacao() > maior.getPopulacao()) {
        maior = pais;
      }
    }
    return maior;
  }

  public Pais menorPopulacao() {
    Pais menor = new Pais("null", "null", Integer.MAX_VALUE, 0);
    for (Pais pais : this.getPaises()) {
      if (pais.getPopulacao() < menor.getPopulacao()) {
        menor = pais;
      }
    }
    return menor;
  }

  public Pais maiorDimensao() {
    Pais maior = new Pais("null", "null", 0, 0);
    for (Pais pais : this.getPaises()) {
      if (pais.getDimensao() > maior.getDimensao()) {
        maior = pais;
      }
    }
    return maior;
  }

  public Pais menorDimensao() {
    Pais menor = new Pais("null", "null", 0, Integer.MAX_VALUE);
    for (Pais pais : this.getPaises()) {
      if (pais.getDimensao() < menor.getDimensao()) {
        menor = pais;
      }
    }
    return menor;
  }

  public double razaoTerritorial() {
    return this.maiorDimensao().getDimensao() / this.menorDimensao().getDimensao();
  }

}
