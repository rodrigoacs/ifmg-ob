// 5) Escreva em Java uma classe que represente um circulo no plano cartesiano. Forneça os seguintes membros de classe:
//   a) Um construtor que receba o raio e um ponto (o centro do circulo);
//   b) Um construtor que receba o raio e posicione o circulo na origem do espaço cartesiano;
//   c) Métodos de acesso ao atributo raio do circulo;
//   d) Métodos inflar e desinflar, que, respectivamente, aumentam e diminuem o raio do circulo de um dado valor;
//   e) Métodos sobrecarregados, inflar e desinflar, que, respectivamente, aumentam e diminuem o raio do circulo de uma unidade;
//   h) Métodos sobrecarregados mover, que:
//     i) por default (sem parâmetros) levam o circulo para a origem do espaço 2D;
//     ii) movem o circulo para um local indicado por dois parâmetros do tipo double (indicando o valor de abcissa e ordenada do ponto para onde o circulo se move);
//     iii) movem o circulo para o local indicado por outro ponto.
//   f) Método que retorna a Area do circulo

public class q5 {
  public static void main(String[] args) {
    // Criando um circulo com raio 5 e centro no ponto (2, 3)
    Ponto2D centro = new Ponto2D(2, 3);
    Circulo circulo1 = new Circulo(5, centro);

    // Teste de getters
    System.out.println("Raio do Circulo 1: " + circulo1.getRaio());
    System.out.println("Centro do Circulo 1: " + circulo1.getCentro().representaString());

    // Teste de inflar e desinflar
    circulo1.infla();
    System.out.println("Raio do Circulo 1 apos inflar: " + circulo1.getRaio());
    circulo1.desinfla();
    System.out.println("Raio do Circulo 1 apos desinflar: " + circulo1.getRaio());

    // Teste de mover
    circulo1.move(1, 1);
    System.out.println("Centro do Circulo 1 apos mover para (1, 1): " + circulo1.getCentro().representaString());

    // Criando um novo circulo com raio 3 (sem especificar o centro)
    Circulo circulo2 = new Circulo(3);

    // Teste de mover com Ponto2D
    Ponto2D novoCentro = new Ponto2D(4, 4);
    circulo2.move(novoCentro);
    System.out.println("Centro do Circulo 2 apos mover para (4, 4): " + circulo2.getCentro().representaString());

    // Teste do metodo area
    double areaCirculo1 = circulo1.area();
    System.out.println("Area do Circulo 1: " + areaCirculo1);

    double areaCirculo2 = circulo2.area();
    System.out.println("Area do Circulo 2: " + areaCirculo2);
  }
}

class Circulo {
  int raio;
  Ponto2D centro;

  public void setRaio(int raio) {
    this.raio = raio;
  }

  public int getRaio() {
    return raio;
  }

  public void setCentro(Ponto2D centro) {
    this.centro = centro;
  }

  public Ponto2D getCentro() {
    return centro;
  }

  public Circulo(int raio, Ponto2D centro) {
    this.setRaio(raio);
    this.setCentro(centro);
  }

  public Circulo(int raio) {
    this.setRaio(raio);
    Ponto2D centro = new Ponto2D(0, 0);
    this.setCentro(centro);
  }

  public void infla() {
    this.setRaio(this.getRaio() + 1);
  }

  public void desinfla() {
    this.setRaio(this.getRaio() - 1);
  }

  public void move() {
    this.centro.setX(0);
    this.centro.setY(0);
  }

  public void move(double abcissa, double ordenada) {
    this.centro.setX(abcissa);
    this.centro.setY(ordenada);
  }

  public void move(Ponto2D p) {
    this.centro = p;
  }

  public double area() {
    return 2 * Math.PI * this.raio * this.raio;
  }
}
