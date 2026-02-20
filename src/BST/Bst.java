package BST;

public class Bst {

    private Node root;

    public Bst() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    // ---------- INSERT ----------
    private Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        }

        if (data < root.getData()) {
            root.setLeft(insert(root.getLeft(), data));
        } else if (data > root.getData()) {
            root.setRight(insert(root.getRight(), data));
        }
        // if equal, ignore (no duplicates)
        return root;
    }

    public void insert(int data) {
        root = insert(root, data);
    }

    // ---------- FIND MIN (used in delete) ----------
    public Node findMin(Node root) {
        if (root == null) return null;

        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        return root;
    }

    // ---------- DELETE ----------
    private Node delete(Node root, int data) {
        if (root == null) {
            return null;
        }

        if (data < root.getData()) {
            root.setLeft(delete(root.getLeft(), data));
        } else if (data > root.getData()) {
            root.setRight(delete(root.getRight(), data));
        } else {

            // Case 1: No child
            if (root.getLeft() == null && root.getRight() == null) {
                return null;
            }

            // Case 2: One child (right only)
            if (root.getLeft() == null) {
                return root.getRight();
            }

            // Case 2: One child (left only)
            if (root.getRight() == null) {
                return root.getLeft();
            }

            // Case 3: Two children
            Node successor = findMin(root.getRight());
            root.setData(successor.getData());
            root.setRight(delete(root.getRight(), successor.getData()));
        }

        return root;
    }

    public void delete(int data) {
        root = delete(root, data);
    }

    // ---------- SEARCH ----------
    private boolean search(Node root, int data) {
        if (root == null) {
            return false;
        }
        if (root.getData() == data) {
            return true;
        }
        return data < root.getData()
                ? search(root.getLeft(), data)
                : search(root.getRight(), data);
    }

    public boolean search(int data) {
        return search(root, data);
    }

    // ---------- TRAVERSALS (optional for console) ----------
    public void preOrder(Node node) {
        if (node == null) return;
        System.out.print(node.getData() + "\t");
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    public void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.getLeft());
        System.out.print(node.getData() + "\t");
        inOrder(node.getRight());
    }

    public void postOrder(Node node) {
        if (node == null) return;
        postOrder(node.getLeft());
        postOrder(node.getRight());
        System.out.print(node.getData() + "\t");
    }
}

