package presentation;

import businessLayer.ClientService;
import businessLayer.OrderDataService;
import businessLayer.OrderItemService;
import businessLayer.ProductService;
import model.Client;
import model.OrderData;
import model.OrderItem;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is responsible for managing the orders in the system.
 */
public class OrderManagementWindow extends JFrame {
    private ClientService clientService = new ClientService();
    private ProductService productService = new ProductService();
    private OrderDataService orderDataService = new OrderDataService();
    private OrderItemService orderItemService = new OrderItemService();
    private JComboBox<String> editOrderDropdown;
    private JComboBox<String> deleteOrderDropdown;

    private List<OrderItem> currentOrderItems = new ArrayList<>();
    private JComboBox<String> clientComboBox;
    private JComboBox<String> productComboBox;
    private JTextField quantityField;
    private JTextArea orderItemsArea;
    int currentOrderId;

    public OrderManagementWindow() {
        setTitle("Order Management");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(createAddOrderPanel());
        panel.add(createEditOrderPanel());
        panel.add(createDeleteOrderPanel());
        panel.add(createViewOrdersPanel());

        getContentPane().add(panel);

        refreshDropdowns();
    }
    /**
     * Creates a panel for adding an order.
     * @return The panel for adding an order.
     */
    private JPanel createAddOrderPanel() {
        JPanel addOrderPanel = new JPanel(new GridBagLayout());
        addOrderPanel.setBackground(new Color(255, 182, 193)); // pastel pink
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        clientComboBox = new JComboBox<>();
        productComboBox = new JComboBox<>();
        quantityField = new JTextField(5);
        orderItemsArea = new JTextArea(10, 20);
        orderItemsArea.setEditable(false);
        populateComboBoxes(clientComboBox, productComboBox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        addOrderPanel.add(new JLabel("Client:"), gbc);
        gbc.gridx = 1;
        addOrderPanel.add(clientComboBox, gbc);

        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(e -> createOrder());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        addOrderPanel.add(createOrderButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addOrderPanel.add(new JLabel("Product:"), gbc);
        gbc.gridx = 1;
        addOrderPanel.add(productComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addOrderPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        addOrderPanel.add(quantityField, gbc);

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> addItem());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        addOrderPanel.add(addItemButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        addOrderPanel.add(new JScrollPane(orderItemsArea), gbc);

        JButton finalizeOrderButton = new JButton("Finalize Order");
        finalizeOrderButton.addActionListener(e -> finalizeOrder());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        addOrderPanel.add(finalizeOrderButton, gbc);

        return addOrderPanel;
    }
    /**
     * Creates an order in the system.
     */
    private void createOrder() {
        String selectedClientString = (String) clientComboBox.getSelectedItem();
        if (selectedClientString == null) {
            JOptionPane.showMessageDialog(this, "Please select a client.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int clientId = Integer.parseInt(selectedClientString.split(":")[0].trim());
        OrderData orderData = new OrderData(0, clientId, LocalDateTime.now());
        orderDataService.addOrderData(orderData);
        currentOrderId = orderData.getOrderdataId();
    }
    /**
     * Creates a panel for editing an order.
     * @return The panel for editing an order.
     */
    private JPanel createEditOrderPanel() {
        JPanel editOrderPanel = new JPanel(new GridBagLayout());
        editOrderPanel.setBackground(new Color(144, 238, 144));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        editOrderDropdown = new JComboBox<>();

        //JComboBox<String> clientComboBox = new JComboBox<>();
        JComboBox<String> productComboBox = new JComboBox<>();
        JTextField quantityField = new JTextField(5);
        populateComboBoxes(clientComboBox, productComboBox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        editOrderPanel.add(new JLabel("Select Order:"), gbc);
        gbc.gridx = 1;
        editOrderPanel.add(editOrderDropdown, gbc);

        /*gbc.gridx = 0;
        gbc.gridy = 1;
        editOrderPanel.add(new JLabel("Client:"), gbc);
        gbc.gridx = 1;
        editOrderPanel.add(clientComboBox, gbc);*/

        gbc.gridx = 0;
        gbc.gridy = 1;
        editOrderPanel.add(new JLabel("Product:"), gbc);
        gbc.gridx = 1;
        editOrderPanel.add(productComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        editOrderPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        editOrderPanel.add(quantityField, gbc);

        JButton editButton = new JButton("Edit Order");
        editButton.addActionListener(e -> editOrder(productComboBox, quantityField));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        editOrderPanel.add(editButton, gbc);

        return editOrderPanel;
    }
    /**
     * Creates a panel for deleting an order.
     * @return The panel for deleting an order.
     */
    private JPanel createDeleteOrderPanel() {
        JPanel deleteOrderPanel = new JPanel(new GridBagLayout());
        deleteOrderPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        deleteOrderDropdown = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 0;
        deleteOrderPanel.add(new JLabel("Select Order:"), gbc);
        gbc.gridx = 1;
        deleteOrderPanel.add(deleteOrderDropdown, gbc);

        JButton deleteButton = new JButton("Delete Order");
        deleteButton.addActionListener(e -> deleteOrder());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        deleteOrderPanel.add(deleteButton, gbc);

        return deleteOrderPanel;
    }
    /**
     * Creates a panel for viewing all orders.
     * @return The panel for viewing all orders.
     */
    private JPanel createViewOrdersPanel() {
        JPanel viewOrdersPanel = new JPanel(new BorderLayout());
        viewOrdersPanel.setBackground(new Color(240, 230, 140));

        JButton viewOrdersButton = new JButton("View All Orders");
        viewOrdersButton.addActionListener(e -> new TableView<>(OrderData.class).setVisible(true));
        viewOrdersPanel.add(viewOrdersButton, BorderLayout.NORTH);

        JButton billButton = new JButton("Bill");
        billButton.addActionListener(e -> new BillViewWindow().setVisible(true));
        viewOrdersPanel.add(billButton, BorderLayout.WEST);

        JButton backButton = new JButton("Go Back");
        backButton.setBackground(Color.ORANGE);
        backButton.addActionListener(e -> {
            dispose();
            new InitGUI().setVisible(true);
        });
        viewOrdersPanel.add(backButton, BorderLayout.SOUTH);

        return viewOrdersPanel;
    }
    /**
     * Adds an item to the order.
     */
    private void addItem() {
        String selectedClientString = (String) clientComboBox.getSelectedItem();
        String selectedProductString = (String) productComboBox.getSelectedItem();

        if (selectedClientString == null || selectedProductString == null) {
            JOptionPane.showMessageDialog(this, "Please select a client and a product.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int clientId = Integer.parseInt(selectedClientString.split(":")[0].trim());
        int productId = Integer.parseInt(selectedProductString.split(":")[0].trim());
        System.out.println("Client ID: " + clientId + ", Product ID: " + productId + ", Order ID: " + currentOrderId + "\n");

        Client selectedClient = clientService.getClientById(clientId);
        Product selectedProduct = productService.getProductById(productId);

        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedProduct.getStock() < quantity) {
            JOptionPane.showMessageDialog(this, "Not enough stock for the selected product.", "Stock Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //adding each item to order items
        OrderItem orderItem = new OrderItem(0, currentOrderId, productId, quantity);
        orderItemService.addOrderItem(orderItem);
        currentOrderItems.add(orderItem);
        //updating stock
        selectedProduct.setStock(selectedProduct.getStock() - quantity);
        productService.updateProduct(selectedProduct, selectedProduct.getProductId());

        orderItemsArea.append("Product: " + selectedProduct.getName() + ", Quantity: " + quantity + "\n");

        quantityField.setText("");
    }
    /**
     * Finalizes the order.
     */
    private void finalizeOrder() {
        if (orderItemsArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items in the order.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedClientString = (String) clientComboBox.getSelectedItem();
        if (selectedClientString == null) {
            JOptionPane.showMessageDialog(this, "No client selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int clientId = Integer.parseInt(selectedClientString.split(":")[0].trim());
        Client selectedClient = clientService.getClientById(clientId);

        currentOrderItems.clear();
        orderItemsArea.setText("");

        JOptionPane.showMessageDialog(this, "Order finalized successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        refreshDropdowns();
    }
    /**
     * Edits an order in the system.
     * @param productComboBox The combo box for the product.
     * @param quantityField The field for the quantity.
     */
    private void editOrder(JComboBox<String> productComboBox, JTextField quantityField) {
        String selectedOrder = (String) editOrderDropdown.getSelectedItem();
        if (selectedOrder != null) {
            int orderId = Integer.parseInt(selectedOrder.split(":")[0]);
            //String selectedClientString = (String) clientComboBox.getSelectedItem();
            String selectedProductString = (String) productComboBox.getSelectedItem();

            if (selectedProductString == null) {
                JOptionPane.showMessageDialog(this, "Please select a product.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //int clientId = Integer.parseInt(selectedClientString.split(":")[0].trim());
            int productId = Integer.parseInt(selectedProductString.split(":")[0].trim());

            //Client selectedClient = clientService.getClientById(clientId);
            Product selectedProduct = productService.getProductById(productId);

            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            OrderData orderData = orderDataService.getOrderById(orderId);
            //orderData.setClientId(selectedClient.getClientId());
            orderDataService.updateOrderData(orderData, orderId);

            OrderItem orderItem = new OrderItem(0, orderData.getOrderdataId(), selectedProduct.getProductId(), quantity);
            orderItemService.addOrderItem(orderItem);
            selectedProduct.setStock(selectedProduct.getStock() - quantity);
            productService.updateProduct(selectedProduct, selectedProduct.getProductId());

            JOptionPane.showMessageDialog(this, "Order updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshDropdowns();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to edit.");
        }
    }
    /**
     * Deletes an order from the system.
     */
    private void deleteOrder() {
        String selectedOrder = (String) deleteOrderDropdown.getSelectedItem();
        if (selectedOrder != null) {
            int orderId = Integer.parseInt(selectedOrder.split(":")[0]);
            //orderDataService.deleteOrderData(orderId);
            orderDataService.deleteOrderDataWithItems(orderId);
            JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshDropdowns();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to delete.");
        }
    }
    /**
     * Refreshes the dropdowns.
     */
    private void refreshDropdowns() {
        List<OrderData> orders = orderDataService.getAllOrders();
        editOrderDropdown.removeAllItems();
        deleteOrderDropdown.removeAllItems();
        for (OrderData order : orders) {
            String orderInfo = order.getOrderdataId() + ": " + order.getClientId();
            editOrderDropdown.addItem(orderInfo);
            deleteOrderDropdown.addItem(orderInfo);
        }

        clientComboBox.removeAllItems();
        productComboBox.removeAllItems();

        List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            clientComboBox.addItem(client.getClientId() + ": " + client.getName());
        }

        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            productComboBox.addItem(product.getProductId() + ": " + product.getName());
        }
    }
    /**
     * Populates the combo boxes.
     * @param clientComboBox The combo box for the client.
     * @param productComboBox The combo box for the product.
     */
    private void populateComboBoxes(JComboBox<String> clientComboBox, JComboBox<String> productComboBox) {
        List<Client> clients = clientService.getAllClients();
        List<Product> products = productService.getAllProducts();

        clientComboBox.removeAllItems();
        productComboBox.removeAllItems();

        for (Client client : clients) {
            clientComboBox.addItem(client.getClientId() + ": " + client.getName());
        }

        for (Product product : products) {
            productComboBox.addItem(product.getProductId() + ": " + product.getName());
        }
    }

}











