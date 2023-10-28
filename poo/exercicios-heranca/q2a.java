public class q2a {

  public static void main(String[] args) {
    // Testando a classe Cachorro
    Cachorro cachorro = new Cachorro();
    cachorro.nome = "Gary";
    cachorro.late();
    cachorro.caminha();

    // Testando a classe Gato
    Gato gato = new Gato();
    gato.nome = "Frodo";
    gato.mia();
    gato.caminha();
  }

  public static class Animal {
    String nome;
    String raca;

    public Animal() {

    }

    public Animal(String nome) {
      this.nome = nome;
    }

    public void caminha() {
      System.out.println("O animal " + this.nome + " esta caminhando.");
    }
  }

  public static class Cachorro extends Animal {
    public void late() {
      System.out.println("O cachorro " + this.nome + " esta latindo.");
    }
  }

  public static class Gato extends Animal {
    public void mia() {
      System.out.println("O gato " + this.nome + " esta miando.");
    }
  }
}
