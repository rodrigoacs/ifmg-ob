// 2) Escreva em Java uma classe Ponto2D que represente um ponto no plano cartesiano. Além dos atributos por você identificados, a classe deve oferecer os seguintes membros:
//   a) Construtores sobrecarregados que permitam a inicialização do ponto:
//     i) Por default (sem parâmetros) na origem do espaço 2D;
//     ii) Num local indicado por dois parâmetros do tipo double (indicando o valor de abcissa e ordenada do ponto que está sendo criado);
//     iii) Em um local indicado por outro ponto.
//   b) Métodos de acesso (getter/setter) dos atributos do ponto;
//   c) Métodos sobrecarregados de movimentação do ponto com os mesmos parâmetros indicados para os construtores;
//   d) Método de comparação semântica do ponto (equals);
//   e) Método de representação do objeto como String;
//   f) Método que permita calcular a distância do ponto que recebe a mensagem, para outro ponto;
//   g) Método que permita a criação de um novo ponto no mesmo local do ponto que recebeu a mensagem (clone);

public class q2 {
  public static void main(String[] args) {
    // Criando pontos
    Ponto2D ponto1 = new Ponto2D(2.0, 3.0);
    Ponto2D ponto2 = new Ponto2D();
    Ponto2D ponto3 = new Ponto2D(ponto1);

    // Imprimindo representações em string dos pontos
    System.out.println("Ponto1: " + ponto1.representaString());
    System.out.println("Ponto2: " + ponto2.representaString());
    System.out.println("Ponto3: " + ponto3.representaString());

    // Movendo o ponto2 para (5, 5)
    ponto2.movePonto(5.0, 5.0);
    System.out.println("Ponto2 apos mover: " + ponto2.representaString());

    // Verificando se ponto1 e ponto3 são iguais
    if (ponto1.comparaPontos(ponto3)) {
      System.out.println("Ponto1 e Ponto3 sao iguais.");
    } else {
      System.out.println("Ponto1 e Ponto3 nao sao iguais.");
    }

    // Calculando a distância entre ponto1 e ponto2
    double distancia = ponto1.calculaDistancia(ponto2);
    System.out.println("Distancia entre Ponto1 e Ponto2: " + distancia);
  }
}
