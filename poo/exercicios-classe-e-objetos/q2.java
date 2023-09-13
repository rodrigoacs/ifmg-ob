public class q2 {
  public static void main(String[] args) {
    Pessoa p1 = new Pessoa();
    Pessoa p2 = new Pessoa();

    p1.nome = "Albert Einstein";
    p1.ajustaDataNascimento(14, 3, 1879);
    p1.calculaIdade(13, 9, 2023);
    System.out.println(p1.informaNome() + " tem " + p1.informaIdade() + " anos");

    p2.nome = "Isaac Newton";
    p2.ajustaDataNascimento(4, 1, 1643);
    p2.calculaIdade(13, 9, 2023);
    System.out.println(p2.informaNome() + " tem " + p2.informaIdade() + " anos");
  }
}

class Pessoa {
  int idade;
  int diaNascimento;
  int mesNascimento;
  int anoNascimento;
  String nome;

  public void calculaIdade(int dia, int mes, int ano) {
    if (mes > mesNascimento || (mes == mesNascimento && dia >= diaNascimento)) {
      idade = ano - anoNascimento;
    } else {
      idade = ano - anoNascimento - 1;
    }
  }

  public int informaIdade() {
    return idade;
  }

  public String informaNome() {
    return nome;
  }

  public void ajustaDataNascimento(int dia, int mes, int ano) {
    diaNascimento = dia;
    mesNascimento = mes;
    anoNascimento = ano;
  }
}
