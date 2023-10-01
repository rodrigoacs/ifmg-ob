// 6) Escreva uma classe que represente um país. Um país é representado através dos atributos: 
// código ISO 3166-1 (ex.: BRA), nome (ex.:Brasil), população (ex.: 193.946.886) 
// e a sua dimensão em Km2 (ex.:8.515.767,049). Além disso, cada país mantém uma lista de outros
// países com os quais ele faz fronteira. Escreva a classe em Java e forneça os seus membros a seguir:
//   a) Construtor que inicialize o código ISO, o nome e a dimensão do país;
//   b) Métodos de acesso (getter/setter) para as propriedades código ISSO, nome, população e dimensão do país;
//   c) Um método que permita verificar se dois objetos representam o mesmo país (igualdade semântica). 
//      Dois países são iguais se tiverem o mesmo código ISO;
//   d) Um método que informe se outro país é limítrofe do país que recebeu a mensagem;
//   e) Um método que retorne a densidade populacional do país;
//   f) Um método que receba um país como parâmetro e retorne a lista de vizinhos comuns aos dois países.
// Considere que um país tem no máximo 40 outros países com os quais ele faz fronteira.

import java.util.ArrayList;

public class q6 {
  public static void main(String[] args) {
    // Criando alguns países
    Pais brasil = new Pais("BR", "Brasil", 211000000, 8515767.049);
    Pais argentina = new Pais("AR", "Argentina", 45195777, 2780400.0);
    Pais peru = new Pais("PE", "Peru", 3456750, 176215.0);

    // Adicionando vizinhos aos países usando ArrayList
    brasil.getVizinhos().add("Argentina");
    brasil.getVizinhos().add("Peru");
    brasil.getVizinhos().add("Uruguai");
    brasil.getVizinhos().add("Paraguai");
    argentina.getVizinhos().add("Brasil");
    argentina.getVizinhos().add("Uruguai");
    argentina.getVizinhos().add("Paraguai");
    peru.getVizinhos().add("Brasil");

    // Teste de comparação de países por ISO
    System.out.println("Brasil e Argentina sao o mesmo pais? " + brasil.comparaPaises(argentina));
    System.out.println("Brasil e Peru sao o mesmo pais? " + brasil.comparaPaises(peru));

    // Teste de verificação de vizinhos
    System.out.println("Argentina e vizinho do Brasil? " + brasil.eVizinho(argentina));
    System.out.println("Peru e vizinho do Brasil? " + brasil.eVizinho(peru));

    // Teste de densidade populacional
    System.out.println("Densidade populacional do Brasil: " + brasil.densidadePopulacional());
    System.out.println("Densidade populacional da Argentina: " + argentina.densidadePopulacional());

    // Teste de vizinhos em comum
    ArrayList<String> vizinhosEmComum = brasil.vizinhosEmComum(argentina);
    System.out.println("Vizinhos em comum entre Brasil e Argentina: " + vizinhosEmComum);
  }
}