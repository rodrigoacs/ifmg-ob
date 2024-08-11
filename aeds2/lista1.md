# Rodrigo Augusto Correa Soares (0039063)

### 1. Seja a árvore abaixo, informe:

- **Nós Folhas**: `C`, `J`, `P`, e `T`.
  
- **Grau da Árvore**: O grau da árvore é `2`, pois cada nó tem no máximo dois filhos.

- **Altura da Árvore**: Altura é `2` (considerando os níveis: 0 para `M`, 1 para `G` e `R`, e 2 para `C`, `J`, `P`, `T`).

- **Descendentes do nó `R`**: Os descendentes de `R` são `P` e `T`.

### 2. Quantas árvores ordenadas existem com 3 nós?

- O número de árvores ordenadas seria (2 * 3 - 3)!! = 3!! = 3 * 1 = 3) árvores.

### 3. Desenhe as árvores que correspondem às seguintes expressões aritméticas:

- **a) 2 * (a-b/c)**
```
     *
    / \
   2   -
      / \
     a   /
        / \
       b   c
```

- **b) a + b + 5 * c**
```
       +
      / \
     +   *
    / \ / \
   a  b 5  c
```

### 4. Represente graficamente as árvores binárias abaixo e diga se são balanceadas, perfeitamente balanceadas ou não. Liste os nós em a) pré-ordem, b) in-ordem e iii) pós-ordem.

#### a) (1 (2 (4) (5)) (3 (6) (7)))

```
      1
     / \
    2   3
   / \ / \
  4  5 6  7
```
- **Pré-ordem**: 1, 2, 4, 5, 3, 6, 7
- **In-ordem**: 4, 2, 5, 1, 6, 3, 7
- **Pós-ordem**: 4, 5, 2, 6, 7, 3, 1

Esta árvore é perfeitamente balanceada, pois todos os níveis são preenchidos e as folhas estão no mesmo nível.

#### b) (A (B (D (F)) (E)) (C (G (H))))
```
      A
     / \
    B   C
   / \   \
  D   E   G
 /         \
F           H
```
- **Pré-ordem**: A, B, D, F, E, C, G, H
- **In-ordem**: F, D, B, E, A, G, H, C
- **Pós-ordem**: F, D, E, B, H, G, C, A

Esta árvore não é perfeitamente balanceada, pois nem todos os níveis estão completos.

### 5. Altere o algoritmo não-recursivo de percurso de AB em pré-ordem de maneira que a pilha já usada seja lista encadeada. Você viu uma vantagem nesta abordagem?

A vantagem desta abordagem é que a lista encadeada pode crescer dinamicamente, enquanto uma pilha baseada em array pode precisar de redimensionamento.

### 6. Dê um exemplo de que se conhecermos a sequência de nós em pré-ordem e in-ordem de uma árvore binária, podemos descobrir qual é a estrutura dessa árvore.

#### Sequência Pré-ordem: 
`A, B, D, E, C, F`

#### Sequência In-ordem: 
`D, B, E, A, F, C`

#### Passo a passo para reconstrução:
A primeira letra na sequência pré-ordem é sempre a raiz da árvore. Neste caso, `A` é a raiz. Na sequência in-ordem, tudo à esquerda de `A` são os nós da subárvore esquerda, e tudo à direita são os nós da subárvore direita. Para `A`, a sequência in-ordem é `D, B, E` (subárvore esquerda) e `F, C` (subárvore direita). Para a sequência `D, B, E` na in-ordem, a próxima raiz na pré-ordem é `B`. Portanto, `B` é a raiz da subárvore esquerda. Para a sequência `F, C` na in-ordem, a próxima raiz na pré-ordem após `B` e seus descendentes é `C`. Portanto, `C` é a raiz da subárvore direita. Para `B` (subárvore esquerda de `A`), a sequência in-ordem `D` é à esquerda de `B`, e `E` é à direita. Isso significa que `D` é o filho esquerdo de `B` e `E` é o filho direito de `B`.  Para `C` (subárvore direita de `A`), a sequência in-ordem `F` é à esquerda de `C`. Isso significa que `F` é o filho esquerdo de `C`. A árvore reconstruída será:
```
       A
     /   \
    B     C
   / \   /
  D   E F
```

### 7. Quais são as seqüências de nós encontradas ao atravessar a árvore abaixo em in-ordem, pré-ordem e pós-ordem?

- **In-ordem**: Esquerda -> Raiz -> Direita
- **Pré-ordem**: Raiz -> Esquerda -> Direita
- **Pós-ordem**: Esquerda -> Direita -> Raiz

### 10. Relação entre Travessia Pós-ordem e Pré-ordem Espelhada

A travessia pós-ordem de uma árvore é a mesma que a travessia pré-ordem de sua imagem no espelho.

### 11. Manipulação de Arquivos em Árvores de Busca Binárias

Para percorrer a árvore e apagar todos os arquivos cujos últimos acessos foram anteriores a uma certa data:

```java
class FileNode {
    String fileName;
    int lastAccessDate; // Representa a data do último acesso como um inteiro
    FileNode left, right;

    public FileNode(String fileName, int lastAccessDate) {
        this.fileName = fileName;
        this.lastAccessDate = lastAccessDate;
        this.left = this.right = null;
    }
}

public class FileTree {
    FileNode root;

    // Método para remover arquivos com acesso anterior a uma certa data
    public FileNode removeOldFiles(FileNode node, int dateThreshold) {
        if (node == null) {
            return null;
        }

        node.left = removeOldFiles(node.left, dateThreshold);
        node.right = removeOldFiles(node.right, dateThreshold);

        // Se o último acesso foi antes da data limite, remover o nó
        if (node.lastAccessDate < dateThreshold) {
            return null;
        }

        return node;
    }

    // Método auxiliar para iniciar a remoção a partir da raiz
    public void removeOldFiles(int dateThreshold) {
        root = removeOldFiles(root, dateThreshold);
    }

    // Método para adicionar um nó à árvore (inserção em uma árvore de busca binária)
    public void addFile(String fileName, int lastAccessDate) {
        root = addFile(root, fileName, lastAccessDate);
    }

    private FileNode addFile(FileNode node, String fileName, int lastAccessDate) {
        if (node == null) {
            return new FileNode(fileName, lastAccessDate);
        }

        if (fileName.compareTo(node.fileName) < 0) {
            node.left = addFile(node.left, fileName, lastAccessDate);
        } else if (fileName.compareTo(node.fileName) > 0) {
            node.right = addFile(node.right, fileName, lastAccessDate);
        }

        return node;
    }

    // Método para exibir a árvore (in-ordem)
    public void inorderDisplay(FileNode node) {
        if (node != null) {
            inorderDisplay(node.left);
            System.out.println("File: " + node.fileName + ", Last Access: " + node.lastAccessDate);
            inorderDisplay(node.right);
        }
    }

    public static void main(String[] args) {
        FileTree tree = new FileTree();
        tree.addFile("file1.txt", 20230101);
        tree.addFile("file2.txt", 20220715);
        tree.addFile("file3.txt", 20230630);
        tree.addFile("file4.txt", 20220420);

        System.out.println("Before removal:");
        tree.inorderDisplay(tree.root);

        int dateThreshold = 20230101; // Data limite para remoção
        tree.removeOldFiles(dateThreshold);

        System.out.println("\nAfter removal:");
        tree.inorderDisplay(tree.root);
    }
}

```

### 12. Reorganização de Árvore Binária com Frequência de Acesso

O código para reorganizar uma árvore de acordo com a frequência de acesso seria algo assim:

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class AccessNode {
    String key;
    int accessFrequency;
    AccessNode left, right;

    public AccessNode(String key, int accessFrequency) {
        this.key = key;
        this.accessFrequency = accessFrequency;
        this.left = this.right = null;
    }
}

public class AccessTree {
    AccessNode root;

    // Método para reorganizar a árvore baseada na frequência de acesso
    public AccessNode reorganizeTree() {
        List<AccessNode> nodes = new ArrayList<>();
        inorderTraversal(root, nodes);

        // Ordena os nós em ordem decrescente de frequência de acesso
        Collections.sort(nodes, new Comparator<AccessNode>() {
            @Override
            public int compare(AccessNode n1, AccessNode n2) {
                return Integer.compare(n2.accessFrequency, n1.accessFrequency);
            }
        });

        // Recria a árvore usando os nós ordenados
        AccessNode newRoot = null;
        for (AccessNode node : nodes) {
            newRoot = insertNode(newRoot, node.key, node.accessFrequency);
        }

        return newRoot;
    }

    // Método de percurso em-ordem para coletar os nós
    private void inorderTraversal(AccessNode node, List<AccessNode> nodes) {
        if (node != null) {
            inorderTraversal(node.left, nodes);
            nodes.add(node);
            inorderTraversal(node.right, nodes);
        }
    }

    // Método para adicionar um nó à árvore (inserção em uma árvore de busca binária)
    public AccessNode insertNode(AccessNode node, String key, int accessFrequency) {
        if (node == null) {
            return new AccessNode(key, accessFrequency);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = insertNode(node.left, key, accessFrequency);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertNode(node.right, key, accessFrequency);
        }

        return node;
    }

    // Método para adicionar um nó à árvore
    public void addAccessNode(String key, int accessFrequency) {
        root = insertNode(root, key, accessFrequency);
    }

    // Método para exibir a árvore (in-ordem)
    public void inorderDisplay(AccessNode node) {
        if (node != null) {
            inorderDisplay(node.left);
            System.out.println("Key: " + node.key + ", Access Frequency: " + node.accessFrequency);
            inorderDisplay(node.right);
        }
    }

    public static void main(String[] args) {
        AccessTree tree = new AccessTree();
        tree.addAccessNode("file1.txt", 15);
        tree.addAccessNode("file2.txt", 5);
        tree.addAccessNode("file3.txt", 20);
        tree.addAccessNode("file4.txt", 8);

        System.out.println("Before reorganization:");
        tree.inorderDisplay(tree.root);

        tree.root = tree.reorganizeTree();

        System.out.println("\nAfter reorganization:");
        tree.inorderDisplay(tree.root);
    }
}
```