// 3) Escreva em Java uma classe NumeroComplexo, que representa um número complexo. A classe deve fornecer as seguintes operações:
//   a) Construtor com valores das partes inteira e fracionária;
//   b) Métodos getter/setter para os atributos da parte inteira e parte imaginária;
//   c) Método somar, que recebe outro número complexo e o adiciona ao número complexo que recebeu a mensagem. (a+bi)+(c+di) = (a+c)+(b+d)i;
//   d) Método subtrair, que recebe outro número complexo e o subtrai do número complexo que recebeu a mensagem. (a+bi)−(c+di) = (a−c)+(b−d)i;
//   e) Método multiplicar, que recebe outro número complexo e o multiplica ao complexo que recebeu a mensagem: (a+bi) * (c+di) = (ac−bd)+(ad+bc)i;
//   f) Método dividir, que recebe outro número complexo e o divide ao complexo que recebeu a mensagem: (a+bi) / (c+di) = (ac+bd)/(c² + d²) + (bc-ad)/(c² + d²)i;
//   g) Um método de comparação semântica dos números complexos;
//   h) Um método que gere e retorne a representação string do número complexo;
//   i) Um método que retorne o módulo do número complexo.

public class q3 {
  public static void main(String[] args) {
    // Criando números complexos
    NumeroComplexo numero1 = new NumeroComplexo(2.0, 3.0);
    NumeroComplexo numero2 = new NumeroComplexo(1.0, 2.0);

    // Cálculo do módulo de um número complexo
    double modulo = numero1.modulo();
    System.out.println("Modulo de Numero1: " + modulo);

    // Imprimindo representações em string dos números complexos
    System.out.println("Numero1: " + numero1.representaString());
    System.out.println("Numero2: " + numero2.representaString());

    // Soma de números complexos
    numero1.soma(numero2);
    System.out.println("Soma de Numero1 e Numero2: " + numero1.representaString());

    // Subtração de números complexos
    numero2.subtrai(numero1);
    System.out.println("Subtracao de Numero2 e Numero1: " + numero2.representaString());

    // Multiplicação de números complexos
    numero1.multiplica(numero2);
    System.out.println("Multiplicacao de Numero1 e Numero2: " + numero1.representaString());

    // Divisão de números complexos
    numero2.divide(numero1);
    System.out.println("Divisao de Numero2 por Numero1: " + numero2.representaString());

    // Comparação de números complexos
    if (numero1.comparaNumeros(numero2)) {
      System.out.println("Numero1 e Numero2 sao iguais.");
    } else {
      System.out.println("Numero1 e Numero2 nao sao iguais.");
    }

  }
}

class NumeroComplexo {
  double inteira;
  double imaginaria;

  public NumeroComplexo(double inteira, double imaginaria) {
    this.setInteira(inteira);
    this.setImaginaria(imaginaria);
  }

  public double getImaginaria() {
    return imaginaria;
  }

  public double getInteira() {
    return inteira;
  }

  public void setImaginaria(double imaginaria) {
    this.imaginaria = imaginaria;
  }

  public void setInteira(double inteira) {
    this.inteira = inteira;
  }

  public void soma(NumeroComplexo n) {
    this.setInteira(n.getInteira() + this.getInteira());
    this.setImaginaria(n.getImaginaria() + this.getImaginaria());
  }

  public void subtrai(NumeroComplexo n) {
    this.setInteira(n.getInteira() - this.getInteira());
    this.setImaginaria(n.getImaginaria() - this.getImaginaria());
  }

  public void multiplica(NumeroComplexo n) {
    this.setInteira(this.getInteira() * n.getInteira() - this.getImaginaria() * n.getImaginaria());
    this.setImaginaria(this.getInteira() * n.getImaginaria() - this.getImaginaria() * n.getInteira());
  }

  public void divide(NumeroComplexo n) {
    this.setInteira((this.getInteira() * n.getInteira() + this.getImaginaria() * n.getImaginaria())
        / Math.pow(n.getInteira(), 2) + Math.pow(n.getImaginaria(), 2));
  }

  public boolean comparaNumeros(NumeroComplexo n) {
    return this.getInteira() == n.getInteira() && this.getImaginaria() == n.getImaginaria() ? true : false;
  }

  public String representaString() {
    return this.getInteira() + "+" + this.getImaginaria() + "i";
  }

  public double modulo() {
    return Math.sqrt(Math.pow(this.getInteira(), 2) + Math.pow(this.getImaginaria(), 2));
  }
}
