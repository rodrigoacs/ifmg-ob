public class Tree {
  private Node root;

  public void setRoot(Node root) {
    this.root = root;
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

    if (value < node.value) {
      return search(node.left, value);
    } else {
      return search(node.right, value);
    }
  }

  public boolean isSubtree(Node tree, Node subtree) {
    if (subtree == null) {
      return true;
    }
    if (tree == null) {
      return false;
    }
    if (areIdentical(tree, subtree)) {
      return true;
    }
    return isSubtree(tree.left, subtree) || isSubtree(tree.right, subtree);
  }

  private boolean areIdentical(Node tree1, Node tree2) {
    if (tree1 == null && tree2 == null) {
      return true;
    }
    if (tree1 == null || tree2 == null) {
      return false;
    }
    return (tree1.value == tree2.value) &&
        areIdentical(tree1.left, tree2.left) &&
        areIdentical(tree1.right, tree2.right);
  }

  public static void main(String[] args) {
    Tree treeT = new Tree();
    Node tRoot = new Node(26);
    tRoot.left = new Node(10);
    tRoot.right = new Node(3);
    tRoot.left.left = new Node(4);
    tRoot.left.right = new Node(6);
    tRoot.left.left.left = new Node(30);
    tRoot.right.right = new Node(3);
    treeT.setRoot(tRoot);

    Tree treeS = new Tree();
    Node sRoot = new Node(10);
    sRoot.left = new Node(4);
    sRoot.right = new Node(6);
    sRoot.left.left = new Node(30);
    treeS.setRoot(sRoot);

    System.out.println(treeT.isSubtree(treeT.root, treeS.root));
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
