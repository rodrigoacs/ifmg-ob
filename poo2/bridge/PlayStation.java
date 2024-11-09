public class PlayStation extends Dispositivo {
  public PlayStation(int estado_, int maximo_) {
    estado = estado_;
    maximo = maximo_;
  }

  @Override
  public void botaoCinco() {
    System.out.println("Jogo Anterior");
    estado--;
  }

  @Override
  public void botaoSeis() {
    System.out.println("Próximo Jogo");
    estado++;
  }

}
