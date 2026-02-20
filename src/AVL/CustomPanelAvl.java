package AVL;

import javax.swing.*;
import java.awt.*;

/**
 * CustomPanelAvl draws an AvlTree on a JPanel and shows colored balance factors.
 */
public class CustomPanelAvl extends JPanel {

    private final AvlTree tree;
    private static final int NODE_RADIUS = 22;
    private static final int VERTICAL_GAP = 70;

    public CustomPanelAvl(AvlTree tree) {
        this.tree = tree;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background gradient
        GradientPaint gp = new GradientPaint(0, 0, new Color(10, 24, 61), 0, getHeight(), new Color(46, 134, 193));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (tree.getRoot() == null) {
            g2.setColor(Color.WHITE);
            g2.setFont(getFont().deriveFont(Font.BOLD, 18f));
            String msg = "AVL Tree is empty. Insert nodes to visualize!";
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(msg)) / 2;
            int y = getHeight() / 2;
            g2.drawString(msg, x, y);
            g2.dispose();
            return;
        }

        drawTree(g2, tree.getRoot(), getWidth() / 2, 80, getWidth() / 4);
        g2.dispose();
    }

    private void drawTree(Graphics2D g2, AvlNode node, int x, int y, int gap) {
        if (node == null) return;

        int minGap = 40;
        if (gap < minGap) gap = minGap;

        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(230, 230, 230)); // edge color

        if (node.getLeft() != null) {
            int cx = x - gap;
            int cy = y + VERTICAL_GAP;
            g2.drawLine(x, y, cx, cy);
            drawTree(g2, node.getLeft(), cx, cy, gap / 2);
        }

        if (node.getRight() != null) {
            int cx = x + gap;
            int cy = y + VERTICAL_GAP;
            g2.drawLine(x, y, cx, cy);
            drawTree(g2, node.getRight(), cx, cy, gap / 2);
        }

        // draw node with shadow and gradient
        int d = NODE_RADIUS * 2;
        int nodeX = x - NODE_RADIUS;
        int nodeY = y - NODE_RADIUS;

        // shadow
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillOval(nodeX + 3, nodeY + 3, d, d);

        // gradient for node
        GradientPaint ng = new GradientPaint(nodeX, nodeY, new Color(255, 140, 0), nodeX, nodeY + d, new Color(255, 215, 0));
        g2.setPaint(ng);
        g2.fillOval(nodeX, nodeY, d, d);

        // border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(nodeX, nodeY, d, d);

        // data text
        String s = String.valueOf(node.getData());
        g2.setFont(getFont().deriveFont(Font.BOLD, 16f));
        FontMetrics fm = g2.getFontMetrics();
        int tx = x - fm.stringWidth(s) / 2;
        int ty = y + fm.getAscent() / 2 - 2;
        g2.setColor(new Color(30, 30, 30));
        g2.drawString(s, tx, ty);

        // ----- BALANCE FACTOR (colored) -----
        int leftH = (node.getLeft() != null) ? node.getLeft().getHeight() : 0;
        int rightH = (node.getRight() != null) ? node.getRight().getHeight() : 0;
        int balance = leftH - rightH; // BF = left height - right height

        // choose color: green for balanced (-1..1), red for imbalance
        Color bfColor = (balance >= -1 && balance <= 1) ? new Color(46, 204, 113) : new Color(231, 76, 60);

        String bfText = "BF:" + balance;
        g2.setFont(getFont().deriveFont(Font.BOLD, 13f));
        FontMetrics bfFm = g2.getFontMetrics();
        int bfW = bfFm.stringWidth(bfText);
        int bfH = bfFm.getHeight();

        // position: top-right of node
        int bfX = x + NODE_RADIUS + 6;
        int bfY = y - NODE_RADIUS - 6;

        // draw rounded rectangle background for bf
        int pad = 6;
        g2.setColor(new Color(0, 0, 0, 140)); // slightly transparent outline background
        g2.fillRoundRect(bfX - 3, bfY - bfH + 4, bfW + pad, bfH, 10, 10);

        // colored small rectangle or circle indicator
        g2.setColor(bfColor);
        int indicatorSize = 10;
        g2.fillOval(bfX + bfW + pad - indicatorSize - 2, bfY - bfH/2 + 3, indicatorSize, indicatorSize);

        // draw BF text in white on top of the dark rounded background
        g2.setColor(Color.WHITE);
        g2.drawString(bfText, bfX + 3, bfY);

        // (optional) small tooltip-like hint if imbalanced
        if (balance < -1 || balance > 1) {
            g2.setFont(getFont().deriveFont(Font.PLAIN, 11f));
            g2.setColor(new Color(255, 200, 200));
            g2.drawString("!imbalance", bfX + bfW + pad + 12, bfY + 2);
        }
    }
}
