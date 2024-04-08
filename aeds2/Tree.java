
public class Tree {
  private Node root;

  public boolean add(Node node, int value) {
    if (node == null) {
      root = new Node(value);
      return true;
    }

    if (node.value < value) {
      if (node.right == null) {
        node.right = new Node(value);
        return true;
      } else {
        return add(node.right, value);
      }
    } else {
      if (node.left == null) {
        node.left = new Node(value);
        return true;
      } else {
        return add(node.left, value);
      }

    }
  }

  public void print(Node node) {
    if (node == null) {
      return;
    }

    print(node.left);
    System.out.println(node.value);
    print(node.right);
  }

  public boolean search(Node node, int value) {
    if (node == null) {
      return false;
    }

    if (node.value == value) {
      return true;
    }

    if (node.value < value) {
      return search(node.right, value);
    } else {
      return search(node.left, value);
    }
  }

  public static void main(String[] args) {
    Tree tree = new Tree();
    tree.add(tree.root, 10);
    tree.add(tree.root, 5);
    tree.add(tree.root, 15);
    tree.add(tree.root, 3);
    tree.add(tree.root, 7);
    tree.add(tree.root, 12);
    tree.add(tree.root, 17);

    tree.print(tree.root);

    System.out.println(tree.search(tree.root, 10));
    System.out.println(tree.search(tree.root, 5));
    System.out.println(tree.search(tree.root, 13));
  }
}

class Node {
  public Node left;
  public Node right;
  public int value;

  public Node(int value) {
    this.value = value;
  }
}