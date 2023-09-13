public class q1 {
  public static void main(String[] args) {
    Televisor televisor = new Televisor();

    System.out.println("Teste 1: Ligar o televisor");
    televisor.ligarTelevisor();
    televisor.mostraStatus();

    System.out.println("\nTeste 2: Aumentar o volume");
    televisor.aumentarVolume();
    televisor.mostraStatus();

    System.out.println("\nTeste 3: Subir o canal");
    televisor.subirCanal();
    televisor.mostraStatus();

    System.out.println("\nTeste 4: Reduzir o volume");
    televisor.reduzirVolume();
    televisor.mostraStatus();

    System.out.println("\nTeste 5: Descer o canal");
    televisor.descerCanal();
    televisor.mostraStatus();

    System.out.println("\nTeste 6: Desligar o televisor");
    televisor.desligarTelevisor();
    televisor.mostraStatus();

    System.out.println("\nTeste 7: Tentar aumentar o volume com o televisor desligado");
    televisor.aumentarVolume();
    televisor.mostraStatus();

    System.out.println("\nTeste 8: Tentar reduzir o volume com o televisor desligado");
    televisor.reduzirVolume();
    televisor.mostraStatus();

    System.out.println("\nTeste 9: Tentar subir o canal com o televisor desligado");
    televisor.subirCanal();
    televisor.mostraStatus();

    System.out.println("\nTeste 10: Tentar descer o canal com o televisor desligado");
    televisor.descerCanal();
    televisor.mostraStatus();

    System.out.println("\nTeste 11: Conferir se algum status foi alterado com o televisor desligado");
    televisor.ligarTelevisor();
    televisor.mostraStatus();
  }
}

class Televisor {
  int canal = 1;
  int volume = 0;
  boolean ligado = false;

  public void aumentarVolume() {
    if (!ligado) {
      System.out.println("O televisor esta desligado.");
      return;
    }
    if (volume < 10) {
      volume++;
    } else if (volume == 10) {
      System.out.println("O volume ja esta no maximo.");
    }
  }

  public void reduzirVolume() {
    if (!ligado) {
      System.out.println("O televisor esta desligado.");
      return;
    }
    if (volume > 0) {
      volume--;
    } else if (volume == 0) {
      System.out.println("O volume ja esta no minimo.");
    }
  }

  public void subirCanal() {
    if (!ligado) {
      System.out.println("O televisor esta desligado.");
      return;
    }
    if (canal < 16) {
      canal++;
    } else if (canal == 16) {
      canal = 1;
    }
  }

  public void descerCanal() {
    if (!ligado) {
      System.out.println("O televisor esta desligado.");
      return;
    }
    if (canal > 1) {
      canal--;
    } else if (canal == 1) {
      canal = 16;
    }
  }

  public void ligarTelevisor() {
    ligado = true;
  }

  public void desligarTelevisor() {
    canal = 1;
    volume = 0;
    ligado = false;
  }

  public void mostraStatus() {
    if (ligado) {
      System.out.println("O televisor esta ligado no canal " + canal + " no volume " + volume);
    } else {
      System.out.println("O televisor esta desligado");
    }
  }

}
