package presentation;

import businessLayer.ProductService;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * This class is responsible for managing the products in the system.
 */
public class ProductManagementWindow extends JFrame {
    private ProductService productService = new ProductService();
    private JTextField addNameField;
    private JComboBox<String> editProductDropdown;
    private JComboBox<String> deleteProductDropdown;
    /**
     * Creates a new instance of the ProductManagementWindow class.
     */
    public ProductManagementWindow() {
        setTitle("Product Management");
        setSize(1100, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(createAddProductPanel());
        panel.add(createEditProductPanel());
        panel.add(createDeleteProductPanel());
        panel.add(createViewProductsPanel());

        getContentPane().add(panel);

        refreshProductDropdowns();
    }
    /**
     * Creates a panel for adding a product.
     * @return The panel for adding a product.
     */
    private JPanel createAddProductPanel() {
        JPanel addProductPanel = new JPanel(new GridBagLayout());
        addProductPanel.setBackground(new Color(255, 182, 193)); // pastel pink
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField addNameField = new JTextField(15);
        JTextField addPriceField = new JTextField(15);
        JTextField addStockField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        addProductPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        addProductPanel.add(addNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addProductPanel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        addProductPanel.add(addPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addProductPanel.add(new JLabel("Stock:"), gbc);

        gbc.gridx = 1;
        addProductPanel.add(addStockField, gbc);

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> addProduct( addNameField,  addPriceField, addStockField));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        addProductPanel.add(addButton, gbc);

        return addProductPanel;
    }
    /**
     * Creates a panel for editing a product.
     * @return The panel for editing a product.
     */
    private JPanel createEditProductPanel() {
        JPanel editProductPanel = new JPanel(new GridBagLayout());
        editProductPanel.setBackground(new Color(144, 238, 144)); // pastel green
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        editProductDropdown = new JComboBox<>();

        JTextField editNameField = new JTextField(15);
        JTextField editPriceField = new JTextField(15);
        JTextField editStockField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        editProductPanel.add(new JLabel("Select Product:"), gbc);

        gbc.gridx = 1;
        editProductPanel.add(editProductDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        editProductPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        editProductPanel.add(editNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        editProductPanel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        editProductPanel.add(editPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        editProductPanel.add(new JLabel("Stock:"), gbc);

        gbc.gridx = 1;
        editProductPanel.add(editStockField, gbc);

        JButton editButton = new JButton("Edit Product");
        editButton.addActionListener(e -> editProduct(editNameField,  editPriceField, editStockField));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        editProductPanel.add(editButton, gbc);

        return editProductPanel;
    }
    /**
     * Creates a panel for deleting a product.
     * @return The panel for deleting a product.
     */
    private JPanel createDeleteProductPanel() {
        JPanel deleteProductPanel = new JPanel(new GridBagLayout());
        deleteProductPanel.setBackground(new Color(173, 216, 230)); // pastel blue
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        deleteProductDropdown = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 0;
        deleteProductPanel.add(new JLabel("Select Product:"), gbc);

        gbc.gridx = 1;
        deleteProductPanel.add(deleteProductDropdown, gbc);

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(e -> deleteProduct());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        deleteProductPanel.add(deleteButton, gbc);

        return deleteProductPanel;
    }
    /**
     * Creates a panel for viewing all products.
     * @return The panel for viewing all products.
     */
    private JPanel createViewProductsPanel() {
        JPanel viewProductsPanel = new JPanel(new BorderLayout());
        viewProductsPanel.setBackground(new Color(240, 230, 140)); // pastel yellow

        JButton viewProductsButton = new JButton("View All Products");
        viewProductsButton.addActionListener(e -> new TableView<>(Product.class).setVisible(true));

        viewProductsPanel.add(viewProductsButton, BorderLayout.NORTH);
        JButton backButton = new JButton("Go Back");
        backButton.setBackground(Color.ORANGE);
        backButton.addActionListener(e -> {
            dispose();
            new InitGUI().setVisible(true);
        });
        viewProductsPanel.add(backButton,  BorderLayout.SOUTH);
        return viewProductsPanel;
    }
    /**
     * Adds a product to the system.
     * @param addNameField The JTextField containing the name of the product.
     * @param addPriceField The JTextField containing the price of the product.
     * @param addStockField The JTextField containing the stock of the product.
     */
    private void addProduct(JTextField addNameField, JTextField addPriceField, JTextField addStockField) {
        String name = addNameField.getText();
        double price = Double.parseDouble(addPriceField.getText());
        int stock = Integer.parseInt(addStockField.getText());
        Product product = new Product(0, name, price, stock);
        productService.addProduct(product);
        addNameField.setText("");
        addPriceField.setText("");
        addStockField.setText("");
        refreshProductDropdowns();
    }
    /**
     * Edits a product in the system.
     * @param editNameField The JTextField containing the name of the product.
     * @param editPriceField The JTextField containing the price of the product.
     * @param editStockField The JTextField containing the stock of the product.
     */
    private void editProduct(JTextField editNameField, JTextField editPriceField, JTextField editStockField) {
        String selectedProduct = (String) editProductDropdown.getSelectedItem();
        if (selectedProduct != null) {
            int productId = Integer.parseInt(selectedProduct.split(":")[0]);
            String name = editNameField.getText();
            double price = Double.parseDouble(editPriceField.getText());
            int stock = Integer.parseInt(editStockField.getText());
            Product product = new Product(productId, name, price, stock);
            productService.updateProduct(product, productId);
            editNameField.setText("");
            editPriceField.setText("");
            editStockField.setText("");
            refreshProductDropdowns();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to edit.");
        }
    }
    /**
     * Deletes a product from the system.
     */
    private void deleteProduct() {
        String selectedProduct = (String) deleteProductDropdown.getSelectedItem();
        if (selectedProduct != null) {
            int productId = Integer.parseInt(selectedProduct.split(":")[0]);
            productService.deleteProduct(productId);
            refreshProductDropdowns();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
        }
    }
    /**
     * Refreshes the product dropdowns.
     */
    private void refreshProductDropdowns() {
        List<Product> products = productService.getAllProducts();
        editProductDropdown.removeAllItems();
        deleteProductDropdown.removeAllItems();
        for (Product product : products) {
            String productInfo = product.getProductId() + ": " + product.getName();
            editProductDropdown.addItem(productInfo);
            deleteProductDropdown.addItem(productInfo);
        }
    }
}