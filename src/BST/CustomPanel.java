package BST;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {

    private final Bst tree;

    private static final int NODE_RADIUS = 22;
    private static final int VERTICAL_GAP = 70;

    public CustomPanel(Bst tree) {
        this.tree = tree;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Smooth edges & text
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Gradient background
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(10, 24, 61),
                0, getHeight(), new Color(46, 134, 193)
        );
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // If tree is empty
        if (tree.getRoot() == null) {
            g2.setColor(Color.WHITE);
            g2.setFont(getFont().deriveFont(Font.BOLD, 18f));
            String msg = "Tree is empty. Insert nodes to visualize!";
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(msg)) / 2;
            int y = getHeight() / 2;
            g2.drawString(msg, x, y);
            g2.dispose();
            return;
        }

        // Draw tree starting from root
        drawTree(g2, tree.getRoot(), getWidth() / 2, 80, getWidth() / 4);

        g2.dispose();
    }

    private void drawTree(Graphics2D g2, Node node, int x, int y, int gap) {
        if (node == null) return;

        // To avoid nodes collapsing when tree is big
        int minGap = 40;
        if (gap < minGap) {
            gap = minGap;
        }

        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(230, 230, 230));   // edge color

        // Draw left edge + subtree
        if (node.getLeft() != null) {
            int childX = x - gap;
            int childY = y + VERTICAL_GAP;
            g2.drawLine(x, y, childX, childY);
            drawTree(g2, node.getLeft(), childX, childY, gap / 2);
        }

        // Draw right edge + subtree
        if (node.getRight() != null) {
            int childX = x + gap;
            int childY = y + VERTICAL_GAP;
            g2.drawLine(x, y, childX, childY);
            drawTree(g2, node.getRight(), childX, childY, gap / 2);
        }

        // Draw the node itself (with a small shadow)
        int d = NODE_RADIUS * 2;
        int nodeX = x - NODE_RADIUS;
        int nodeY = y - NODE_RADIUS;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillOval(nodeX + 3, nodeY + 3, d, d);

        // Node gradient (orange/yellow)
        GradientPaint nodeGradient = new GradientPaint(
                nodeX, nodeY, new Color(255, 140, 0),
                nodeX, nodeY + d, new Color(255, 215, 0)
        );
        g2.setPaint(nodeGradient);
        g2.fillOval(nodeX, nodeY, d, d);

        // Node border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(nodeX, nodeY, d, d);

        // Draw value (centered)
        String text = String.valueOf(node.getData());
        g2.setFont(getFont().deriveFont(Font.BOLD, 16f));
        FontMetrics fm = g2.getFontMetrics();
        int tx = x - fm.stringWidth(text) / 2;
        int ty = y + fm.getAscent() / 2 - 2;
        g2.setColor(new Color(30, 30, 30));
        g2.drawString(text, tx, ty);
    }
}

