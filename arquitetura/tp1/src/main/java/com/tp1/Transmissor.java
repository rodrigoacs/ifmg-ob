package transferenciadadostp;

import java.util.Random;

public class Transmissor {
  private String mensagem;
  final boolean POLINOMIO[] = { true, false, false, true, true }; // 10011

  public Transmissor(String mensagem) {
    this.mensagem = mensagem;
  }

  // convertendo um símbolo para "vetor" de boolean (bits)
  private boolean[] streamCaracter(char simbolo) {

    // cada símbolo da tabela ASCII é representado com 8 bits
    boolean bits[] = new boolean[8];

    // convertendo um char para int (encontramos o valor do mesmo na tabela ASCII)
    int valorSimbolo = (int) simbolo;
    int indice = 7;

    // convertendo cada "bits" do valor da tabela ASCII
    while (valorSimbolo >= 2) {
      int resto = valorSimbolo % 2;
      valorSimbolo /= 2;
      bits[indice] = (resto == 1);
      indice--;
    }
    bits[indice] = (valorSimbolo == 1);

    return bits;
  }

  // não modifique (seu objetivo é corrigir esse erro gerado no receptor)
  private void geradorRuido(boolean bits[]) {
    Random geradorAleatorio = new Random();

    // pode gerar um erro ou não..
    if (geradorAleatorio.nextInt(5) > 1) {
      int indice = geradorAleatorio.nextInt(8);
      bits[indice] = !bits[indice];
    }
  }

  private boolean[] dadoBitsCRC(boolean bits[]) {
    boolean[] bitsCRC = new boolean[bits.length + POLINOMIO.length - 1];
    System.arraycopy(bits, 0, bitsCRC, 0, bits.length);

    System.out.println("Bits Originais: ");
    for (boolean b : bits) {
      System.out.print(b ? 1 : 0);
    }

    System.out.println("\nPolinomio: ");
    for (boolean b : POLINOMIO) {
      System.out.print(b ? 1 : 0);
    }

    System.out.println("\nBits CRC: ");
    for (boolean b : bitsCRC) {
      System.out.print(b ? 1 : 0);
    }

    for (int i = 0; i < bits.length; i++) {
      if (bitsCRC[i]) {
        for (int j = 0; j < POLINOMIO.length; j++) {
          bitsCRC[i + j] ^= POLINOMIO[j];
        }
      } else {
        for (int j = 0; j < POLINOMIO.length; j++) {
          bitsCRC[i + j] ^= false;
        }
      }
    }

    boolean[] bitsFinais = new boolean[POLINOMIO.length - 1];
    System.arraycopy(bitsCRC, bitsCRC.length - (POLINOMIO.length - 1), bitsFinais, 0, POLINOMIO.length - 1);

    System.out.println("\nBits Finais CRC: ");
    for (boolean b : bitsFinais) {
      System.out.print(b ? 1 : 0);
    }

    return bitsCRC;
  }

  public void enviaDado(Receptor receptor) {
    for (int i = 0; i < this.mensagem.length(); i++) {
      boolean bits[] = streamCaracter(this.mensagem.charAt(i));

      /*-------AQUI você deve adicionar os bits do códico CRC para contornar os problemas de ruidos
                  você pode modificar o método anterior também
                  */
      boolean bitsCRC[] = dadoBitsCRC(bits);

      // add ruidos na mensagem a ser enviada para o receptor
      geradorRuido(bits);

      // enviando a mensagem "pela rede" para o receptor (uma forma de testarmos esse
      // método)
      boolean indicadorCRC = receptor.receberDadoBits(bits);
      // o que faremos com o indicador quando houver algum erro? qual ação vamos tomar
      // com o retorno do receptor
    }
  }
}
