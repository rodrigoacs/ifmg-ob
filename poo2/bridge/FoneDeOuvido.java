public class FoneDeOuvido extends Dispositivo {
  int volume;

  public FoneDeOuvido(int canal, int volume) {
    super();
  }

  @Override
  public void botaoCinco() {
    System.out.println("Diminuindo volume do fone de ouvido");
    this.setVolume(this.getVolume() - 1);
  }

  @Override
  public void botaoSeis() {
    System.out.println("Aumentando volume do fone de ouvido");
    
    this.setVolume(this.getVolume() + 1);
  }

  public int getVolume() {
    return volume;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }
}
