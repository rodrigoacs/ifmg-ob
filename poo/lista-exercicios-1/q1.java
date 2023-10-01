// 1) Escreva em Java uma classe Contador, que encapsule um valor usado para contagem de itens ou eventos. A classe deve oferecer métodos que devem:
//  a) Zerar;
//  b) Incrementar;
//  c) Retornar o valor do contador.

public class q1 {
  public static void main(String[] args) {
    Contador c = new Contador();
    c.setContador(10);
    System.out.println(c.getContador());
    c.zerarContador();
    System.out.println(c.getContador());
    c.incrementarContador();
    System.out.println(c.getContador());
  }
}

class Contador {
  int contador;

  public int getContador() {
    return this.contador;
  }

  public void setContador(int n) {
    if (n <= 0) {
      this.contador = 0;
    } else {
      this.contador = n;
    }
  }

  public void zerarContador() {
    this.contador = 0;
  }

  public void incrementarContador() {
    this.contador++;
  }
}