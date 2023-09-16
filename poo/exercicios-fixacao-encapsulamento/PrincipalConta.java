import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class PrincipalConta {
  public static void main(String[] args) {
    List<Conta> lista = new ArrayList<>();
    Scanner in = new Scanner(System.in);
    Conta conta = new Conta();
    Cliente cliente = new Cliente();
    int i = 0, numero;
    do {
      System.out.println("MENU" +
          "\n[1] para criar uma conta" +
          "\n[2] para ver o saldo de uma conta" +
          "\n[3] para sacar" +
          "\n[4] para depositar\n");
      switch (in.nextInt()) {
        case 1:
          i++;
          conta = new Conta();
          cliente = new Cliente();
          System.out.println("Digite o nome do titular da conta: ");
          cliente.setNome(in.next());
          System.out.println("Digite o saldo inicial da conta: ");
          conta.setSaldo(in.nextDouble());
          conta.setTitular(cliente);
          conta.setNumero(i);
          lista.add(conta);
          System.out.println("Conta " + i + " criada com sucesso!\n");
          break;

        case 2:
          System.out.println("Digite o numero da conta: ");
          numero = in.nextInt();
          if (numero > lista.size()) {
            System.out.println("Conta inexistente!\n");
            break;
          } else {
            System.out.println("Saldo: " + lista.get(numero - 1).getSaldo() + "\n");
          }
          break;

        case 3:
          System.out.println("Digite o numero da conta: ");
          numero = in.nextInt();
          if (numero > lista.size()) {
            System.out.println("Conta inexistente!\n");
            break;
          } else {
            System.out.println("Digite o valor do saque: ");
            if (lista.get(numero - 1).sacar(in.nextDouble())) {
              System.out.println("Saque realizado com sucesso! Saldo atual: " + conta.getSaldo() + "\n");
            } else {
              System.out.println("Saldo insuficiente!" + "\n");
            }
          }
          break;

        case 4:
          System.out.println("Digite o numero da conta: ");
          numero = in.nextInt();
          if (numero > lista.size()) {
            System.out.println("Conta inexistente!\n");
            break;
          } else {
            System.out.println("Digite o valor do deposito: ");
            lista.get(numero - 1).deposito(in.nextDouble());
            System.out.println("Deposito realizado com sucesso! Saldo atual: " + conta.getSaldo() + "\n");
          }
          break;

        default:
          return;
      }

    } while (true);

  }
}
