public class q1 {
    public static void main(String[] args) {
        Animal cachorro = new Cachorro("cachorro", 10);
        Animal cavalo = new Cavalo("cavalo", 10);
        Animal preguica = new Preguica("preguica", 10);

        cachorro.emitirSom();
        cavalo.emitirSom();
        preguica.emitirSom();

    }

    static class Animal {
        private String nome;
        private int idade;

        public Animal(String nome, int idade) {
            this.nome = nome;
            this.idade = idade;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getIdade() {
            return idade;
        }

        public void setIdade(int idade) {
            this.idade = idade;
        }

        public void emitirSom() {
            System.out.println(this.nome + " emitiu som");
        };
    }

    static class Cachorro extends Animal {
        public Cachorro(String nome, int idade) {
            super(nome, idade);
        }

        public void correr() {
            System.out.println(this.getNome() + " esta correndo");
        }

    }

    static class Cavalo extends Animal {
        public Cavalo(String nome, int idade) {
            super(nome, idade);
        }

        public void correr() {
            System.out.println(this.getNome() + " esta correndo");
        }

    }

    static class Preguica extends Animal {
        public Preguica(String nome, int idade) {
            super(nome, idade);
        }

        public void subirNaArvore() {
            System.out.println(this.getNome() + " subiu na arvore");
        }

    }
}
