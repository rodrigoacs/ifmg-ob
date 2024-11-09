/**
 * Escreva uma descrição da classe Principal aqui.
 * 
 * @author (seu nome)
 * @version (um número da versão ou uma data)
 */
public class Principal {
  public static void main(String[] args) {
    ControleRemoto tv1 = new TvQueFicaMuda(new TV(1, 100));
    ControleRemoto tv2 = new TvQuePausa(new TV(1, 200));
    ControleRemoto ps4 = new Dualshock4(new PlayStation(1, 50));
    ControleRemoto fone = new Bluetooth(new FoneDeOuvido(1, 50));

    System.out.println("Testando a TV que fica muda:");
    tv1.botaoCinco();
    tv1.botaoSeis();
    tv1.botaoNove();

    System.out.println("Testando a TV que pausa:");
    tv2.botaoCinco();
    tv2.botaoSeis();
    tv2.botaoNove();

    System.out.println("Testando o PS4:");
    ps4.botaoCinco();
    ps4.botaoSeis();
    ps4.botaoNove();

    System.out.println("Testando o fone de ouvido:");
    fone.botaoCinco();
    fone.botaoSeis();
    fone.botaoNove();

  }

}
