package AVL;

/**
 * AvlNode.java
 * Node class used by the AvlTree and compatible with the GUI (getData/getLeft/getRight).
 */
public class AvlNode {
    private int key;
    private AvlNode left;
    private AvlNode right;
    private int height; // leaf = 1

    public AvlNode(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    // core getters/setters
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }

    public AvlNode getLeft() { return left; }
    public void setLeft(AvlNode left) { this.left = left; }

    public AvlNode getRight() { return right; }
    public void setRight(AvlNode right) { this.right = right; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    // Compatibility methods so GUI code that expects Node-like API can be used unchanged:
    public int getData() { return getKey(); }
    public void setData(int v) { setKey(v); }
}
