# **POO II - Lista de Exercícios**
## Rodrigo Augusto Correa Soares (0039063)
### **Padroes GRASP (General Responsibility Assignment Patterns) ou Padrões de Atribuição de Responsabilidade Geral**
**Questão 1**  
Os padrões GRASP são:

  1. **Creator**: Define quem é responsável por criar um objeto.
   
  2. **Information Expert**: Identifica quem possui as informações para realizar determinada tarefa.
  
  3. **Controller**: Define quem coordena as ações e interações entre os objetos.
  
  4. **Low Coupling**: Promove baixo acoplamento entre classes para facilitar mudanças.
  
  5. **High Cohesion**: Assegura que cada classe tenha uma única responsabilidade principal.
  
  6. **Polymorphism**: Usa polimorfismo para lidar com diferentes comportamentos uniformemente.
  
  7. **Pure Fabrication**: Introduz classes artificiais para atender responsabilidades que não se encaixam em outras classes.
  
  8. **Indirection**: Adiciona intermediários para desacoplar classes.
  
  9.  **Protected Variations**: Protege o sistema contra mudanças usando interfaces e abstrações.

**Questão 2**  
O diagrama (B) é melhor, pois utiliza herança para diferenciar `Indivíduo` e `Corporação`, aplicando o padrão **Low Coupling**, já que o acoplamento é reduzido, e o **Polymorphism**, pois permite tratar `Indivíduo` e `Corporação` como uma única entidade (`Cliente`).

**Questão 3**  
O padrão **Creator** atribui a responsabilidade de criação de objetos à classe que:
1. Contém ou agrega a classe do objeto criado.
2. Usa diretamente o objeto criado.
3. Possui informações para inicializar o objeto.

**Questão 4**  
Os padrões comportamentais do catálogo GoF têm como objetivo gerenciar algoritmos, responsabilidades e comunicações entre objetos, garantindo flexibilidade e reutilização.

**Questão 5**  
Os padrões estruturais do catálogo GoF organizam classes e objetos para formar estruturas maiores, simplificando a criação e a manutenção do sistema.

**Questão 6**  
Os padrões de criação do catálogo GoF têm como objetivo abstrair o processo de instanciar objetos, facilitando a criação de sistemas flexíveis e escaláveis.

**Questão 7**  
Alternativa **D**

**Questão 8**  
Alternativa **C**

**Questão 9**  
Alternativa **E**

**Questão 10**  
O MVC (Model-View-Controller) é uma arquitetura que separa a lógica do sistema em:
1. **Model**: Representa os dados e regras de negócio.
2. **View**: Gerencia a apresentação ao usuário.
3. **Controller**: Coordena as interações entre Model e View.

**Questão 11**  
Para garantir baixo acoplamento, deve-se:
- Usar interfaces e abstrações para conectar classes.
- Evitar dependências diretas entre muitas classes.
- Garantir que as alterações em uma classe afetem minimamente as outras.

**Questão 12**  
Alternativa **C**

**Questão 13**  
Alternativa **A**

**Questão 14**  
Alternativa **B**

**Questão 16**  
```java
interface Impressora {
    void imprimir(String mensagem);
}

class ImpressoraTela implements Impressora {
    public void imprimir(String mensagem) {
        System.out.println(mensagem);
    }
}

class ImpressoraArquivo implements Impressora {
    public void imprimir(String mensagem) throws IOException {
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write(mensagem);
        }
    }
}

class FabricaImpressora {
    public static Impressora criar() {
        return Math.random() > 0.5 ? new ImpressoraTela() : new ImpressoraArquivo();
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Impressora impressora = FabricaImpressora.criar();
        impressora.imprimir("Hello, World!");
    }
}
```
**Questão 17**  
```java
// Produto (interface comum para Pizzas e Calzones)
interface Produto {
    void imprimirIngredientes();
}

// Produtos concretos para Pizzas
class PizzaCalabresa implements Produto {
    public void imprimirIngredientes() {
        System.out.println("Pizza de Calabresa: Queijo, Calabresa, Tomate.");
    }
}

class PizzaPresunto implements Produto {
    public void imprimirIngredientes() {
        System.out.println("Pizza de Presunto: Queijo, Presunto, Tomate.");
    }
}

// Produtos concretos para Calzones
class CalzoneCalabresa implements Produto {
    public void imprimirIngredientes() {
        System.out.println("Calzone de Calabresa: Queijo, Calabresa, Tomate.");
    }
}

class CalzonePresunto implements Produto {
    public void imprimirIngredientes() {
        System.out.println("Calzone de Presunto: Queijo, Presunto, Tomate.");
    }
}

// Abstract Factory (interface para as fábricas de Pizza e Calzone)
interface FabricaProdutos {
    Produto criarPizza();
    Produto criarCalzone();
}

// Fábrica concreta para produtos de Calabresa
class FabricaCalabresa implements FabricaProdutos {
    public Produto criarPizza() {
        return new PizzaCalabresa();
    }
    public Produto criarCalzone() {
        return new CalzoneCalabresa();
    }
}

// Fábrica concreta para produtos de Presunto
class FabricaPresunto implements FabricaProdutos {
    public Produto criarPizza() {
        return new PizzaPresunto();
    }
    public Produto criarCalzone() {
        return new CalzonePresunto();
    }
}

// Cliente (utiliza a fábrica para criar produtos com base no dia da semana)
public class Pizzaria {
    public static void main(String[] args) {
        FabricaProdutos fabrica = selecionarFabrica("terça"); // Exemplo
        Produto pizza = fabrica.criarPizza();
        Produto calzone = fabrica.criarCalzone();

        System.out.println("Ingredientes do dia:");
        pizza.imprimirIngredientes();
        calzone.imprimirIngredientes();
    }

    private static FabricaProdutos selecionarFabrica(String diaSemana) {
        switch (diaSemana.toLowerCase()) {
            case "segunda":
            case "quarta":
            case "sexta":
                return new FabricaCalabresa();
            case "terça":
            case "quinta":
            case "sábado":
                return new FabricaPresunto();
            default:
                throw new IllegalArgumentException("Pizzaria fechada aos domingos!");
        }
    }
}

```
**Questão 18**  
```java
import java.util.HashSet;
import java.util.Set;

// Classe Pedido
class Pedido {
    private Set<String> dentroDaCaixa = new HashSet<>();
    private Set<String> foraDaCaixa = new HashSet<>();

    public void adicionarDentroDaCaixa(String item) {
        dentroDaCaixa.add(item);
    }

    public void adicionarForaDaCaixa(String item) {
        foraDaCaixa.add(item);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Seu pedido:\n");
        buffer.append("Dentro da caixa:\n");
        for (String item : dentroDaCaixa) {
            buffer.append("\t").append(item).append("\n");
        }
        buffer.append("Fora da caixa:\n");
        for (String item : foraDaCaixa) {
            buffer.append("\t").append(item).append("\n");
        }
        buffer.append("\nTenha um bom dia!\n");
        return buffer.toString();
    }
}

// Interface Builder
interface PedidoBuilder {
    void adicionarSanduiche();
    void adicionarBatata();
    void adicionarBrinquedo();
    void adicionarRefrigerante();
    Pedido getPedido();
}

// Builder Concreto para um Pedido Infantil
class PedidoInfantilBuilder implements PedidoBuilder {
    private Pedido pedido = new Pedido();

    @Override
    public void adicionarSanduiche() {
        pedido.adicionarDentroDaCaixa("Cheeseburger");
    }

    @Override
    public void adicionarBatata() {
        pedido.adicionarDentroDaCaixa("Batata pequena");
    }

    @Override
    public void adicionarBrinquedo() {
        pedido.adicionarDentroDaCaixa("Carrinho");
    }

    @Override
    public void adicionarRefrigerante() {
        pedido.adicionarForaDaCaixa("Coca-Cola");
    }

    @Override
    public Pedido getPedido() {
        return pedido;
    }
}

// Diretor (coordenador do processo de montagem do pedido)
class Diretor {
    private PedidoBuilder builder;

    public Diretor(PedidoBuilder builder) {
        this.builder = builder;
    }

    public void construirPedido() {
        builder.adicionarSanduiche();
        builder.adicionarBatata();
        builder.adicionarBrinquedo();
        builder.adicionarRefrigerante();
    }

    public Pedido getPedido() {
        return builder.getPedido();
    }
}

// Classe Cliente
public class Restaurante {
    public static void main(String[] args) {
        // Cliente solicita um pedido infantil
        PedidoBuilder builder = new PedidoInfantilBuilder();
        Diretor diretor = new Diretor(builder);

        // Diretor monta o pedido
        diretor.construirPedido();
        Pedido pedido = diretor.getPedido();

        // Exibe o pedido final
        System.out.println(pedido);
    }
}

```
**Questão 19**
```java
// Classe Contato (Produto)
abstract class Contato {
    protected String nome;

    public Contato(String nome) {
        this.nome = nome;
    }

    @Override
    public abstract String toString();
}

// Contato para Internet
class ContatoInternet extends Contato {
    private String email;

    public ContatoInternet(String nome, String email) {
        super(nome);
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contato Internet: Nome=" + nome + ", Email=" + email;
    }
}

// Contato para Telefone
class ContatoTelefone extends Contato {
    private String telefone;

    public ContatoTelefone(String nome, String telefone) {
        super(nome);
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Contato Telefone: Nome=" + nome + ", Telefone=" + telefone;
    }
}

// Contato Completo
class ContatoCompleto extends Contato {
    private String endereco;
    private String telefone;
    private String email;

    public ContatoCompleto(String nome, String endereco, String telefone, String email) {
        super(nome);
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contato Completo: Nome=" + nome + ", Endereço=" + endereco + ", Telefone=" + telefone + ", Email=" + email;
    }
}

// Interface Builder
interface ContatoBuilder {
    void setNome(String nome);

    void setTelefone(String telefone);

    void setEmail(String email);

    void setEndereco(String endereco);

    Contato getResultado();
}

// Builder para Contato Internet
class ContatoInternetBuilder implements ContatoBuilder {
    private String nome;
    private String email;

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setTelefone(String telefone) {
        // Não usado em Contato Internet
    }

    @Override
    public void setEndereco(String endereco) {
        // Não usado em Contato Internet
    }

    @Override
    public Contato getResultado() {
        return new ContatoInternet(nome, email);
    }
}

// Builder para Contato Telefone
class ContatoTelefoneBuilder implements ContatoBuilder {
    private String nome;
    private String telefone;

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public void setEmail(String email) {
        // Não usado em Contato Telefone
    }

    @Override
    public void setEndereco(String endereco) {
        // Não usado em Contato Telefone
    }

    @Override
    public Contato getResultado() {
        return new ContatoTelefone(nome, telefone);
    }
}

// Builder para Contato Completo
class ContatoCompletoBuilder implements ContatoBuilder {
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public Contato getResultado() {
        return new ContatoCompleto(nome, endereco, telefone, email);
    }
}

// Classe Diretor
class Diretor {
    private ContatoBuilder builder;

    public Diretor(ContatoBuilder builder) {
        this.builder = builder;
    }

    public void construirContato(String nome, String endereco, String telefone, String email) {
        builder.setNome(nome);
        builder.setEndereco(endereco);
        builder.setTelefone(telefone);
        builder.setEmail(email);
    }

    public Contato getContato() {
        return builder.getResultado();
    }
}

// Classe Cliente
public class Main {
    public static void main(String[] args) {
        // Criar e exibir Contato Internet
        ContatoBuilder internetBuilder = new ContatoInternetBuilder();
        Diretor diretor = new Diretor(internetBuilder);
        diretor.construirContato("João", null, null, "joao@email.com");
        Contato contatoInternet = diretor.getContato();
        System.out.println(contatoInternet);

        // Criar e exibir Contato Telefone
        ContatoBuilder telefoneBuilder = new ContatoTelefoneBuilder();
        diretor = new Diretor(telefoneBuilder);
        diretor.construirContato("Maria", null, "1234-5678", null);
        Contato contatoTelefone = diretor.getContato();
        System.out.println(contatoTelefone);

        // Criar e exibir Contato Completo
        ContatoBuilder completoBuilder = new ContatoCompletoBuilder();
        diretor = new Diretor(completoBuilder);
        diretor.construirContato("Carlos", "Rua das Flores, 123", "9876-5432", "carlos@email.com");
        Contato contatoCompleto = diretor.getContato();
        System.out.println(contatoCompleto);
    }
}

```
**Questão 20**  
```java
import java.util.ArrayList;
import java.util.List;

class Nome {
    private String nome;
    private String sobrenome;

    public Nome(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome;
    }
}

interface NomeFactory {
    Nome criarNome(String entrada);
}

class NomeFormatoNormalFactory implements NomeFactory {
    @Override
    public Nome criarNome(String entrada) {
        String[] partes = entrada.split(" ");
        return new Nome(partes[0], partes[1]);
    }
}

class NomeFormatoInvertidoFactory implements NomeFactory {
    @Override
    public Nome criarNome(String entrada) {
        String[] partes = entrada.split(", ");
        return new Nome(partes[1], partes[0]);
    }
}

abstract class AplicacaoNomes {
    protected NomeFactory factory;
    protected List<Nome> nomes = new ArrayList<>();

    public AplicacaoNomes(NomeFactory factory) {
        this.factory = factory;
    }

    public void adicionarNome(String entrada) {
        nomes.add(factory.criarNome(entrada));
    }

    public void imprimirNomes() {
        for (Nome nome : nomes) {
            System.out.println(nome);
        }
    }
}

class AplicacaoFormatoNormal extends AplicacaoNomes {
    public AplicacaoFormatoNormal() {
        super(new NomeFormatoNormalFactory());
    }
}

class AplicacaoFormatoInvertido extends AplicacaoNomes {
    public AplicacaoFormatoInvertido() {
        super(new NomeFormatoInvertidoFactory());
    }
}

public class Main {
    public static void main(String[] args) {
        String[] entradas = {
            "McNealy, Scott",
            "James Gosling",
            "Naughton, Patrick"
        };

        AplicacaoNomes appFormatoNormal = new AplicacaoFormatoNormal();
        AplicacaoNomes appFormatoInvertido = new AplicacaoFormatoInvertido();

        for (String entrada : entradas) {
            if (entrada.contains(",")) {
                appFormatoInvertido.adicionarNome(entrada);
            } else {
                appFormatoNormal.adicionarNome(entrada);
            }
        }

        System.out.println("Nomes no formato normal:");
        appFormatoNormal.imprimirNomes();

        System.out.println("\nNomes no formato invertido:");
        appFormatoInvertido.imprimirNomes();
    }
}

```
**Questão 21**
```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

interface ProvedoraInformacao {
    String obterInformacao() throws IOException;
}

class ProvedoraPublica implements ProvedoraInformacao {
    @Override
    public String obterInformacao() throws IOException {
        return new String(Files.readAllBytes(Paths.get("publico.txt")));
    }
}

class ProvedoraConfidencial implements ProvedoraInformacao {
    @Override
    public String obterInformacao() throws IOException {
        return new String(Files.readAllBytes(Paths.get("confidencial.txt")));
    }
}

class ProvedoraInformacaoFactory {
    public static ProvedoraInformacao criarProvedora(String senha) {
        if ("designpatterns".equals(senha)) {
            return new ProvedoraConfidencial();
        } else {
            return new ProvedoraPublica();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String senha = args.length > 0 ? args[0] : "";
        ProvedoraInformacao provedora = ProvedoraInformacaoFactory.criarProvedora(senha);

        try {
            String informacao = provedora.obterInformacao();
            System.out.println(informacao);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}

```
**Questao 22**
```java
import java.io.FileWriter;
import java.io.IOException;

interface Logger {
    void log(String mensagem) throws IOException;
}

class ConsoleLogger implements Logger {
    @Override
    public void log(String mensagem) {
        System.out.println(mensagem);
    }
}

class FileLogger implements Logger {
    private static final String FILENAME = "log.txt";

    @Override
    public void log(String mensagem) throws IOException {
        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write(mensagem + "\n");
        }
    }
}

class LoggerFactory {
    public static Logger criarLogger(String tipo) {
        if ("arquivo".equalsIgnoreCase(tipo)) {
            return new FileLogger();
        } else {
            return new ConsoleLogger();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String tipoLog = args.length > 0 ? args[0] : "console";
        Logger logger = LoggerFactory.criarLogger(tipoLog);

        try {
            for (int i = 1; i <= 10; i++) {
                logger.log("Número: " + i);
            }
            System.out.println("Log concluído!");
        } catch (IOException e) {
            System.err.println("Erro ao realizar o log: " + e.getMessage());
        }
    }
}

```
**Questão 23**  
```java
class Incremental {
    private static int count = 0;
    private static Incremental singleton;

    private Incremental() {
    }

    public static Incremental getInstancia() {
        if (singleton == null) {
            singleton = new Incremental();
        }
        count++;
        return singleton;
    }

    @Override
    public String toString() {
        return "Incremental " + count;
    }
}

public class TesteIncremental {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Incremental inc = Incremental.getInstancia();
            System.out.println(inc);
        }
    }
}

```

**Questão 24**
Factory Method

**Questão 25**
Observer

**Questão 26**
Decorator

**Questão 27**
Composite

**Questão 28**
Singleton