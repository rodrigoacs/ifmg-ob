package transferenciadadostp;

public class Receptor {

  // mensagem recebida pelo transmissor
  private String mensagem;

  public Receptor() {
    // mensagem vazia no inicio da execução
    this.mensagem = "";
  }

  public String getMensagem() {
    return mensagem;
  }

  private boolean decodificarDado(boolean bits[]) {
    int codigoAscii = 0;
    int expoente = bits.length - 1;

    // converntendo os "bits" para valor inteiro para então encontrar o valor tabela
    // ASCII
    for (int i = 0; i < bits.length; i++) {
      if (bits[i]) {
        codigoAscii += Math.pow(2, expoente);
      }
      expoente--;
    }

    // concatenando cada simbolo na mensagem original
    this.mensagem += (char) codigoAscii;

    // esse retorno precisa ser pensado... será que o dado sempre chega sem ruído???
    return true;
  }

  private boolean verificaErro(boolean bits[], boolean polinomio[]) {
    boolean[] bitsCRC = new boolean[bits.length];
    System.arraycopy(bits, 0, bitsCRC, 0, bits.length);

    // é repetida a operação realizada no Transmissor
    for (int i = 0; i < bits.length - (polinomio.length - 1); i++) {
      if (bitsCRC[i]) {
        for (int j = 0; j < polinomio.length; j++) {
          bitsCRC[i + j] ^= polinomio[j];
        }
      }
    }

    // pegando os bits finais (CRC)
    boolean[] bitsFinais = new boolean[polinomio.length - 1];
    System.arraycopy(bitsCRC, bitsCRC.length - (polinomio.length - 1), bitsFinais, 0, polinomio.length - 1);

    // verificando se o dado está correto, se os bits finais são todos falsos (0)
    for (int i = 0; i < bitsFinais.length; i++) {
      if (bitsFinais[i]) {
        return true;
      }
    }
    return false;
  }

  private boolean decodificarDadoCRC(boolean bits[], boolean polinomio[]) {
    if (verificaErro(bits, polinomio))
      return false;

    boolean[] bitsSemCRC = new boolean[bits.length - (polinomio.length - 1)];
    System.arraycopy(bits, 0, bitsSemCRC, 0, bitsSemCRC.length);
    decodificarDado(bitsSemCRC);
    return true;
  }

  // recebe os dados do transmissor
  public boolean receberDadoBits(boolean bits[], boolean polinomio[]) {
    return decodificarDadoCRC(bits, polinomio);
  }
}
