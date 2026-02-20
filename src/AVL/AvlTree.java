package AVL;

/**
 * AvlTree.java
 * Fully functional AVL tree with insert, delete, search and traversals.
 */
public class AvlTree {

    private AvlNode root;

    public AvlTree() { this.root = null; }

    public AvlNode getRoot() { return root; }
    public void setRoot(AvlNode root) { this.root = root; }

    // height helper
    private int height(AvlNode n) { return n == null ? 0 : n.getHeight(); }

    // balance factor
    private int getBalance(AvlNode n) { return n == null ? 0 : height(n.getLeft()) - height(n.getRight()); }

    // right rotate
    private AvlNode rightRotate(AvlNode y) {
        AvlNode x = y.getLeft();
        AvlNode T2 = x.getRight();

        // rotate
        x.setRight(y);
        y.setLeft(T2);

        // update heights
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);

        return x;
    }

    // left rotate
    private AvlNode leftRotate(AvlNode x) {
        AvlNode y = x.getRight();
        AvlNode T2 = y.getLeft();

        // rotate
        y.setLeft(x);
        x.setRight(T2);

        // update heights
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);

        return y;
    }

    // insert public
    public void insert(int key) { root = insert(root, key); }

    private AvlNode insert(AvlNode node, int key) {
        if (node == null) return new AvlNode(key);

        if (key < node.getKey()) node.setLeft(insert(node.getLeft(), key));
        else if (key > node.getKey()) node.setRight(insert(node.getRight(), key));
        else return node; // duplicates ignored

        // update height
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.getLeft().getKey()) return rightRotate(node);

        // RR
        if (balance < -1 && key > node.getRight().getKey()) return leftRotate(node);

        // LR
        if (balance > 1 && key > node.getLeft().getKey()) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && key < node.getRight().getKey()) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    // min value
    private AvlNode minValueNode(AvlNode node) {
        AvlNode cur = node;
        while (cur.getLeft() != null) cur = cur.getLeft();
        return cur;
    }

    // delete public
    public void delete(int key) { root = delete(root, key); }

    private AvlNode delete(AvlNode node, int key) {
        if (node == null) return null;

        if (key < node.getKey()) node.setLeft(delete(node.getLeft(), key));
        else if (key > node.getKey()) node.setRight(delete(node.getRight(), key));
        else {
            // node found
            if (node.getLeft() == null || node.getRight() == null) {
                AvlNode temp = node.getLeft() != null ? node.getLeft() : node.getRight();
                if (temp == null) {
                    // no child
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                // two children
                AvlNode temp = minValueNode(node.getRight());
                node.setKey(temp.getKey());
                node.setRight(delete(node.getRight(), temp.getKey()));
            }
        }

        if (node == null) return null;

        // update height
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));

        int balance = getBalance(node);

        // LL
        if (balance > 1 && getBalance(node.getLeft()) >= 0) return rightRotate(node);

        // LR
        if (balance > 1 && getBalance(node.getLeft()) < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // RR
        if (balance < -1 && getBalance(node.getRight()) <= 0) return leftRotate(node);

        // RL
        if (balance < -1 && getBalance(node.getRight()) > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    // search
    public boolean search(int key) { return search(root, key); }
    private boolean search(AvlNode node, int key) {
        if (node == null) return false;
        if (key == node.getKey()) return true;
        return key < node.getKey() ? search(node.getLeft(), key) : search(node.getRight(), key);
    }

    // traversals
    public void inorder() { inorder(root); System.out.println(); }
    private void inorder(AvlNode node) {
        if (node == null) return;
        inorder(node.getLeft()); System.out.print(node.getKey() + " "); inorder(node.getRight());
    }

    public void preorder() { preorder(root); System.out.println(); }
    private void preorder(AvlNode node) {
        if (node == null) return;
        System.out.print(node.getKey() + " "); preorder(node.getLeft()); preorder(node.getRight());
    }

    public void postorder() { postorder(root); System.out.println(); }
    private void postorder(AvlNode node) {
        if (node == null) return;
        postorder(node.getLeft()); postorder(node.getRight()); System.out.print(node.getKey() + " ");
    }

    // helpers
    public int treeHeight() { return height(root); }
    public int countNodes() { return countNodes(root); }
    private int countNodes(AvlNode n) { return n == null ? 0 : 1 + countNodes(n.getLeft()) + countNodes(n.getRight()); }
}
