// 4) Escreva em Java uma classe que represente uma reta (y=ax+b). Forneça os seguintes membros de classe:
//   a) Construtores sobrecarregados que criem uma reta a partir de:
//     i) Dois valores, representando o coeficiente angular e o coeficiente linear da reta;
//     ii) Dois pontos;
//   b) Métodos de acesso para o coeficiente angular e para o coeficiente linear da reta;
//   c) Um método que verifique se um ponto dado pertence a reta;
//   d) Um método que gere e retorne a representação String da reta;
//   e) Um método que dada uma outra reta, retorne o ponto de interseção da reta dada ou null se as retas forem paralelas.
public class q4 {
  public static void main(String[] args) {
    // Criando retas
    Reta reta1 = new Reta(2, 1);
    Reta reta2 = new Reta(-3, -2);

    // Teste de interseção
    double[] pontoIntersecao = reta1.intersecao(reta2);

    if (pontoIntersecao != null) {
      System.out.println("As retas se intersectam no ponto (" + pontoIntersecao[0] + ", " + pontoIntersecao[1] + ")");
    } else {
      System.out.println("As retas sao paralelas e nao tem interseção.");
    }

    // Verificando se um ponto pertence à reta
    Ponto2D ponto = new Ponto2D(3, 7);
    boolean pertence = reta1.pertenceAReta(ponto);

    if (pertence) {
      System.out.println("O ponto (" + ponto.getX() + ", " + ponto.getY() + ") pertence a reta.");
    } else {
      System.out.println("O ponto (" + ponto.getX() + ", " + ponto.getY() + ") não pertence a reta.");
    }
  }
}

class Reta {
  double coeficienteAngular;
  int coeficienteLinear;

  public double getCoeficienteAngular() {
    return coeficienteAngular;
  }

  public int getCoeficienteLinear() {
    return coeficienteLinear;
  }

  public void setCoeficienteAngular(double m) {
    this.coeficienteAngular = m;
  }

  public void setCoeficienteLinear(int coeficienteLinear) {
    this.coeficienteLinear = coeficienteLinear;
  }

  public Reta(int coeficienteAngular, int coeficienteLinear) {
    this.setCoeficienteAngular(coeficienteAngular);
    this.setCoeficienteLinear(coeficienteLinear);
  }

  public Reta(Ponto2D a, Ponto2D b) {
    double m = (a.getY() - b.getY()) / (a.getX() - b.getX());
    this.setCoeficienteAngular(m);
  }

  public boolean pertenceAReta(Ponto2D a) {
    return a.getY() == this.getCoeficienteAngular() * a.getX() + this.getCoeficienteLinear() ? true : false;
  }

  public double[] intersecao(Reta r) {
    if (r.getCoeficienteAngular() == this.getCoeficienteAngular()) {
      return null;
    } else {
      double x = (r.getCoeficienteLinear() - this.getCoeficienteLinear())
          / (this.getCoeficienteAngular() - r.getCoeficienteAngular());
      double y = this.getCoeficienteAngular() * x + this.getCoeficienteLinear();
      return new double[] { x, y };
    }
  }

}
