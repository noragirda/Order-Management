package presentation;

import businessLayer.OrderDataService;
import businessLayer.OrderItemService;
import businessLayer.ProductService;
import model.OrderData;
import model.OrderItem;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * This class is responsible for displaying the bills in the system.
 */
public class BillViewWindow extends JFrame {
    private OrderDataService orderDataService;
    private OrderItemService orderItemService;
    private ProductService productService;
    /**
     * Creates a new instance of the BillViewWindow class.
     */
    public BillViewWindow() {
        setTitle("Bill View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        orderDataService = new OrderDataService();
        orderItemService = new OrderItemService();
        productService = new ProductService();

        // Add a JTextArea in a JScrollPane, to display the bills
        JTextArea billArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(billArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Fetch the bills from the database and display them in the billArea
        displayBills(billArea);
    }
    /**
     * Displays the bills in the system.
     * @param billArea The JTextArea where the bills are to be displayed.
     */
    private void displayBills(JTextArea billArea) {
        List<OrderData> orders = orderDataService.getAllOrders();
        for (OrderData order : orders)
        {
            double totalAmount = 0;
            billArea.append("Order ID: " + order.getOrderdataId() + ", Client ID: " + order.getClientId() + "\n");
            //double totalAmount = orderDataService.calculateTotalAmount(order);
            List<OrderItem> orderItems = orderItemService.getAllOrderItems();
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getOrderdataId() == order.getOrderdataId()) {
                    Product product = productService.getProductById(orderItem.getProductId());
                    double totalProduct = product.getPrice() * orderItem.getQuantity();
                    totalAmount += totalProduct;
                    billArea.append("Product ID: " + product.getProductId() + ", Quantity: " + orderItem.getQuantity() + ", Total Cost: " + totalProduct + "\n");
                }
            }
            billArea.append("Total Ordered Amount " + totalAmount + "\n");
            billArea.append("\n");
        }
    }
}