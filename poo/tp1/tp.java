// As classes `Carro`, `Item` e `OrdemDeServico` podem ter vários métodos adicionais, dependendo dos requisitos e funcionalidades que você deseja implementar. Aqui estão algumas sugestões de métodos que podem ser úteis em cada uma dessas classes:
// ### Classe Carro:
// 1. `calcularConsumoCombustivel()`: Este método pode calcular o consumo médio de combustível com base em dados de quilometragem e abastecimento.
// 2. `iniciarMotor()`: Um método para ligar o motor do carro.
// 3. `pararMotor()`: Um método para desligar o motor do carro.
// 4. `acelerar()`: Método para acelerar o carro.
// 5. `frear()`: Método para frear o carro.
// ### Classe Item:
// 1. `calcularValorTotal()`: Calcula o valor total com base na quantidade e no preço do item.
// 2. `validarItem()`: Verifica se o item é válido de acordo com determinados critérios.
// 3. `definirCategoria()`: Atribui uma categoria ao item com base em suas características.
// ### Classe OrdemDeServico:
// 1. `adicionarItem(Item item)`: Adiciona um item à ordem de serviço.
// 2. `removerItem(Item item)`: Remove um item da ordem de serviço.
// 3. `calcularTotal()`: Recalcula o total da ordem de serviço com base nos itens.
// 4. `adicionarTaxaDeServico(float taxa)`: Adiciona uma taxa de serviço à ordem de serviço.
// 5. `imprimirRecibo()`: Gera um recibo da ordem de serviço, incluindo informações detalhadas sobre os itens, o carro e o custo total.
// 6. `agendarDataDeEntrega()`: Agenda uma data de entrega para a ordem de serviço.
// 7. `registrarPagamento(float valor)`: Registra um pagamento parcial ou total da ordem de serviço.
// Lembre-se de que a escolha de quais métodos adicionar às classes depende dos requisitos do sistema e das funcionalidades desejadas. Certifique-se de que os métodos sejam bem documentados e sigam as melhores práticas de programação.

import java.util.ArrayList;

public class tp {
  public static void main(String[] args) {
    // Testes para a classe Carro
    testCarro();

    // Testes para a classe Item
    testItem();

    // Testes para a classe OrdemDeServico
    testOrdemDeServico();
  }

  static void testCarro() {
    Carro carro = new Carro();
    carro.setPlaca("ABC1234");
    carro.setCor("Azul");
    carro.setMarca("Toyota");

    System.out.println("Carro:");
    System.out.println("Placa: " + carro.getPlaca());
    System.out.println("Cor: " + carro.getCor());
    System.out.println("Marca: " + carro.getMarca());
  }

  static void testItem() {
    Item item = new Item();
    item.setNome("Produto 1");
    item.setPreco(50.0f);
    item.setQtde(2);

    System.out.println("Item:");
    System.out.println("Nome: " + item.getNome());
    System.out.println("Preço: " + item.getPreco());
    System.out.println("Quantidade: " + item.getQtde());
    System.out.println("Total: " + item.calculaTotal());
  }

  static void testOrdemDeServico() {
    OrdemDeServico ordem = new OrdemDeServico();

    Item item1 = new Item();
    item1.setNome("Produto 1");
    item1.setPreco(50.0f);
    item1.setQtde(2);

    Item item2 = new Item();
    item2.setNome("Produto 2");
    item2.setPreco(30.0f);
    item2.setQtde(3);

    System.out.println("Ordem de Serviço:");
    System.out.println("Adicionando itens à ordem de serviço...");
    ordem.adicionarItem(item1);
    ordem.adicionarItem(item2);

    System.out.println("Itens na ordem de serviço:");
    ordem.imprimirOS();

    System.out.println("Total da Ordem de Serviço: " + ordem.getTotal());

    System.out.println("Removendo um item da ordem de serviço...");
    if (ordem.removerItem(item1)) {
      System.out.println("Item removido com sucesso.");
    } else {
      System.out.println("Não foi possível remover o item.");
    }
    if (ordem.removerItem(item1)) {
      System.out.println("Item removido com sucesso.");
    } else {
      System.out.println("Não foi possível remover o item.");
    }

    System.out.println("Novo total da Ordem de Serviço: " + ordem.getTotal());
  }

  static class Carro {
    String modelo;
    String placa;
    String cor;
    String marca;

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
    private String nome;
    private float preco;
    private int qtde;

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

    private void imprimirOS() {
      System.out.println("QTDE | ITEM | PRECO");
      for (Item item : itens) {
        System.out.println(item.getQtde() + " | " + item.getNome() + " | " + item.getPreco());
      }
    }

    private boolean removerItem(Item i) {
      return itens.remove(i);
    }

    private boolean adicionarItem(Item i) {
      return itens.add(i);
    }
  }
}