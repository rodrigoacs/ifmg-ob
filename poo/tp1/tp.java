// As classes `Carro`, `Item` e `OrdemDeServico` podem ter vários métodos adicionais, dependendo dos requisitos e funcionalidades que você deseja implementar. Aqui estão algumas sugestões de métodos que podem ser úteis em cada uma dessas classes:
// ### Classe Carro:
// 1. `calcularConsumoCombustivel()`: Este método pode calcular o consumo médio de combustível com base em dados de quilometragem e abastecimento.
// 2. `iniciarMotor()`: Um método para ligar o motor do carro.
// 3. `pararMotor()`: Um método para desligar o motor do carro.
// 4. `acelerar()`: Método para acelerar o carro.
// 5. `frear()`: Método para frear o carro.
// ### Classe Item:
// 1. `calcularValorTotal()`: Calcula o valor total com base na quantidade e no preco do item.
// 2. `validarItem()`: Verifica se o item é válido de acordo com determinados critérios.
// 3. `definirCategoria()`: Atribui uma categoria ao item com base em suas características.
// ### Classe OrdemDeServico:
// 1. `adicionarItem(Item item)`: Adiciona um item à ordem de servico.
// 2. `removerItem(Item item)`: Remove um item da ordem de servico.
// 3. `calcularTotal()`: Recalcula o total da ordem de servico com base nos itens.
// 4. `adicionarTaxaDeServico(float taxa)`: Adiciona uma taxa de servico à ordem de servico.
// 5. `imprimirRecibo()`: Gera um recibo da ordem de servico, incluindo informacões detalhadas sobre os itens, o carro e o custo total.
// 6. `agendarDataDeEntrega()`: Agenda uma data de entrega para a ordem de servico.
// 7. `registrarPagamento(float valor)`: Registra um pagamento parcial ou total da ordem de servico.
// Lembre-se de que a escolha de quais métodos adicionar às classes depende dos requisitos do sistema e das funcionalidades desejadas. Certifique-se de que os métodos sejam bem documentados e sigam as melhores práticas de programacão.

import java.util.ArrayList;

public class tp {
  public static void main(String[] args) {
    // Criando uma ordem de servico
    OrdemDeServico os = new OrdemDeServico();

    // Adicionando um carro à ordem de servico
    os.adicionarCarro();

    // Adicionando itens à ordem de servico
    os.adicionarItem();

    // Imprimindo a ordem de servico
    os.imprimirOS();

    // Removendo um item da ordem de servico
    if (os.removerItem()) {
      System.out.println("Item removido com sucesso!");
      System.out.println("Total da Ordem de Servico apos a remocao: R$" + os.getTotal());
    } else {
      System.out.println("Item nao encontrado ou remocao nao realizada.");
    }
  }

  static class Carro {
    String modelo;

    String placa;
    String cor;
    String marca;

    public String getModelo() {
      return modelo;
    }

    public void setModelo(String modelo) {
      this.modelo = modelo;
    }

    public String getPlaca() {
      return placa;
    }

    public void setPlaca(String placa) {
      this.placa = placa;
    }

    public String getCor() {
      return cor;
    }

    public void setCor(String cor) {
      this.cor = cor;
    }

    public String getMarca() {
      return marca;
    }

    public void setMarca(String marca) {
      this.marca = marca;
    }

  }

  static class Item {
    private int id;
    private String nome;
    private float preco;
    private int qtde;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getNome() {
      return nome;
    }

    public void setNome(String nome) {
      this.nome = nome;
    }

    public float getPreco() {
      return preco;
    }

    public void setPreco(float preco) {
      this.preco = preco;
    }

    public int getQtde() {
      return qtde;
    }

    public void setQtde(int qtde) {
      this.qtde = qtde;
    }

    public double calculaTotal() {
      return this.qtde * this.preco;
    }
  }

  static class OrdemDeServico {
    private ArrayList<Item> itens = new ArrayList<>();
    private Carro carro;
    private float total;

    public Carro getCarro() {
      return carro;
    }

    public void setCarro(Carro carro) {
      this.carro = carro;
    }

    public float getTotal() {
      this.total = calculaTotal();
      return total;
    }

    public void setTotal(float total) {
      this.total = total;
    }

    private float calculaTotal() {
      float soma = 0;
      for (Item item : itens) {
        soma += item.calculaTotal();
      }
      return soma;
    }

    public void imprimirOS() {
      System.out.println("Ordem de Servico:");
      System.out.println("Placa do Carro: " + this.getCarro().getPlaca());
      System.out.println("Cor do Carro: " + this.getCarro().getCor());
      System.out.println("Marca do Carro: " + this.getCarro().getMarca());
      System.out.println("Modelo do Carro: " + this.getCarro().getModelo());
      System.out.println();

      System.out.println("Itens:");
      System.out.println("ID  | Nome                | Preco   | Qtde | Total");
      for (Item item : itens) {
        System.out.printf("%-4d| %-20s| R$%-6.2f| %-5d| R$%-6.2f%n",
            item.getId(), item.getNome(), item.getPreco(), item.getQtde(), item.calculaTotal());
      }
      System.out.println("Total da Ordem de Servico: R$" + this.getTotal());
    }

    public boolean removerItem() {
      System.out.println("informe o id do item");
      for (Item item : itens) {
        System.out.println(item.getId() + " | " + item.getNome());
      }
      int id = Integer.parseInt(System.console().readLine());
      for (Item item : itens) {
        if (item.getId() == id) {
          itens.remove(item);
          return true;
        }
      }
      return false;
    }

    public void adicionarItem() {
      System.out.println("informe o nome do item");
      String nome = System.console().readLine();
      System.out.println("informe o preco do item");
      float preco = Float.parseFloat(System.console().readLine());
      System.out.println("informe a quantidade do item");
      int qtde = Integer.parseInt(System.console().readLine());

      Item item = new Item();
      item.setNome(nome);
      item.setPreco(preco);
      item.setQtde(qtde);

      this.itens.add(item);
    }

    public void adicionarCarro() {
      System.out.println("informe a placa do carro");
      String placa = System.console().readLine();
      System.out.println("informe a cor do carro");
      String cor = System.console().readLine();
      System.out.println("informe a marca do carro");
      String marca = System.console().readLine();
      System.out.println("informe o modelo do carro");
      String modelo = System.console().readLine();

      Carro carro = new Carro();
      carro.setPlaca(placa);
      carro.setCor(cor);
      carro.setMarca(marca);
      carro.setModelo(modelo);

      this.setCarro(carro);
    }
  }
}