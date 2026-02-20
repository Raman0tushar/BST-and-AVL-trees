import AVL.AvlNode;
import AVL.AvlTree;
import AVL.CustomPanelAvl;
import BST.Bst;
import BST.CustomPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("Tree Visualizer — BST & AVL");

            JTabbedPane tabs = new JTabbedPane();

            // Tab 1: BST
            tabs.addTab("BST", createBstTab());

            // Tab 2: AVL
            tabs.addTab("AVL", createAvlTab());

            frame.setLayout(new BorderLayout());
            frame.add(tabs, BorderLayout.CENTER);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(960, 680);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // ---------------- BST TAB ----------------
    private static JPanel createBstTab() {
        Bst tree = new Bst();

        CustomPanel mainPanel = new CustomPanel(tree);
        mainPanel.setPreferredSize(new Dimension(900, 580));

        JLabel titleLabel = new JLabel("Binary Search Tree (BST) Visualization");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(15, 52, 96));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Controls
        JTextField inputField = new JTextField(6);
        JButton insertButton = new JButton("Insert");
        JButton deleteButton = new JButton("Delete");

        Font controlFont = new Font("SansSerif", Font.PLAIN, 16);
        inputField.setFont(controlFont);
        insertButton.setFont(controlFont);
        deleteButton.setFont(controlFont);

        styleButton(insertButton, new Color(46, 204, 113)); // green
        styleButton(deleteButton, new Color(231, 76, 60));  // red

        JLabel inputLabel = new JLabel("Enter Number: ");
        inputLabel.setFont(controlFont);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(236, 240, 241));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(inputLabel);
        bottomPanel.add(inputField);
        bottomPanel.add(insertButton);
        bottomPanel.add(deleteButton);

        // Actions
        insertButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(inputField.getText().trim());
                tree.insert(value);
                inputField.setText("");
                mainPanel.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        mainPanel,
                        "Please enter a valid integer",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(inputField.getText().trim());
                tree.delete(value);
                inputField.setText("");
                mainPanel.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        mainPanel,
                        "Please enter a valid integer",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        // Press Enter to insert
        inputField.addActionListener(e -> insertButton.doClick());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ---------------- AVL TAB ----------------
    private static JPanel createAvlTab() {
        AvlTree tree = new AvlTree();

        // Initial AVL nodes (like your MainAvl)
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(10);
        tree.insert(25);
        tree.insert(35);
        tree.insert(50);

        CustomPanelAvl mainPanel = new CustomPanelAvl(tree);
        mainPanel.setPreferredSize(new Dimension(900, 580));

        JLabel title = new JLabel("AVL Tree (Self-Balancing BST) — Colored Balance Factor");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(15, 52, 96));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        titlePanel.add(title, BorderLayout.CENTER);

        JTextField inputField = new JTextField(6);
        JButton insertBtn = new JButton("Insert");
        JButton deleteBtn = new JButton("Delete");
        JButton inorderBtn = new JButton("Inorder");
        JButton infoBtn = new JButton("Info");

        Font controlFont = new Font("SansSerif", Font.PLAIN, 16);
        inputField.setFont(controlFont);
        insertBtn.setFont(controlFont);
        deleteBtn.setFont(controlFont);
        inorderBtn.setFont(controlFont);
        infoBtn.setFont(controlFont);

        styleButton(insertBtn, new Color(46, 204, 113));
        styleButton(deleteBtn, new Color(231, 76, 60));
        styleButton(inorderBtn, new Color(52, 152, 219));
        styleButton(infoBtn, new Color(142, 68, 173));

        JLabel inputLabel = new JLabel("Enter Number: ");
        inputLabel.setFont(controlFont);

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(236, 240, 241));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottom.add(inputLabel);
        bottom.add(inputField);
        bottom.add(insertBtn);
        bottom.add(deleteBtn);
        bottom.add(inorderBtn);
        bottom.add(infoBtn);

        // Actions
        insertBtn.addActionListener(e -> {
            try {
                int v = Integer.parseInt(inputField.getText().trim());
                tree.insert(v);
                inputField.setText("");
                mainPanel.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        mainPanel,
                        "Please enter a valid integer",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int v = Integer.parseInt(inputField.getText().trim());
                tree.delete(v);
                inputField.setText("");
                mainPanel.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        mainPanel,
                        "Please enter a valid integer",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        inorderBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            buildInorderString(tree.getRoot(), sb);
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Inorder: " + sb,
                    "Traversal",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        infoBtn.addActionListener(e -> {
            String info = String.format("Nodes: %d\nHeight: %d",
                    tree.countNodes(), tree.treeHeight());
            JOptionPane.showMessageDialog(
                    mainPanel,
                    info,
                    "Tree Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        inputField.addActionListener(e -> insertBtn.doClick());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // ---------- Shared helpers ----------
    private static void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
    }

    private static void buildInorderString(AvlNode node, StringBuilder sb) {
        if (node != null) {
            buildInorderString(node.getLeft(), sb);
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(node.getKey());
            buildInorderString(node.getRight(), sb);
        }
    }
}
