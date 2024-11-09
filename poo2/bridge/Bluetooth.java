public class Bluetooth extends ControleRemoto {
  public Bluetooth(Dispositivo dispositivo) {
    super(dispositivo);
  }

  @Override
  public void botaoNove() {
    System.out.println("Reproduzindo música!");
  }
}
