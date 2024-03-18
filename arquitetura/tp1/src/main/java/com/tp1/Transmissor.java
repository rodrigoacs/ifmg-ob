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

    // aplicando o polinômio para os demais bits
    // se o bit for 1, aplicamos o polinômio
    // se o bit for 0, não aplicamos o polinômio
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

    // pegando os bits finais (CRC)
    boolean[] bitsFinais = new boolean[POLINOMIO.length - 1];
    System.arraycopy(bitsCRC, bitsCRC.length - (POLINOMIO.length - 1), bitsFinais, 0, POLINOMIO.length - 1);

    return bitsFinais;
  }

  public void enviaDado(Receptor receptor) {
    for (int i = 0; i < this.mensagem.length(); i++) {
      boolean bits[] = streamCaracter(this.mensagem.charAt(i));
      boolean bitsCRC[] = dadoBitsCRC(bits);

      boolean bitsSemRuido[] = new boolean[bits.length];
      bitsSemRuido = bits.clone();

      boolean indicadorCRC;
      do {
        boolean[] combinedBits = new boolean[bits.length + bitsCRC.length];
        System.arraycopy(bits, 0, combinedBits, 0, bits.length);
        System.arraycopy(bitsCRC, 0, combinedBits, bits.length, bitsCRC.length);

        indicadorCRC = receptor.receberDadoBits(combinedBits);
        if (!indicadorCRC) {
          System.out.println("Erro detectado, reenviando...");
          bits = bitsSemRuido.clone();
        }

      } while (!indicadorCRC);

    }
  }
}