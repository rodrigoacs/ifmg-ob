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

  private void decoficarDadoCRC(boolean bits[]) {
    // implemente a decodificação CRC* aqui e encontre os
    // erros e faça as devidas correções para ter a imagem correta
    

  }

  // recebe os dados do transmissor
  public boolean receberDadoBits(boolean bits[]) {

    // aqui você deve trocar o médodo decofificarDado para decoficarDadoCRC
    // (implemente!!)
    // decodificarDadoCRC(bits);

    // será que sempre teremos sucesso nessa recepção
    return true;
  }
}