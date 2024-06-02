package presentation;

import javax.swing.*;
import java.awt.*;
/**
 * This class is responsible for displaying the main window of the application.
 */
public class InitGUI extends JFrame {
    public InitGUI() {
        setTitle("Order Management Application");
        setSize(1100, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 182, 193));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title label
        JLabel titleLabel = new JLabel("Order Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 182, 193));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Client management button
        JButton clientButton = new JButton("Managing Clients");
        clientButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clientButton.setBackground(Color.CYAN);
        clientButton.addActionListener(e -> new ClientManagementWindow().setVisible(true));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(clientButton, gbc);

        // Product management button
        JButton productButton = new JButton("Managing Products");
        productButton.setFont(new Font("Arial", Font.PLAIN, 16));
        productButton.setBackground(Color.GREEN);
        productButton.addActionListener(e -> new ProductManagementWindow().setVisible(true));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(productButton, gbc);

        // Order management button
        JButton orderButton = new JButton("Managing Orders");
        orderButton.setFont(new Font("Arial", Font.PLAIN, 16));
        orderButton.setBackground(Color.ORANGE);
        orderButton.addActionListener(e -> new OrderManagementWindow().setVisible(true));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(orderButton, gbc);

        getContentPane().add(panel);
    }
    /**
     * The main method of the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new InitGUI().setVisible(true);
        });
    }
}

