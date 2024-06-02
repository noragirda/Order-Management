package businessLayer;

import dataAccess.ConnectionFactory;
import dataAccess.OrderDataDAO;
import model.Bill;
import model.OrderData;
import model.OrderItem;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
/**
 * This class is responsible for managing the order data in the system.
 */
public class OrderDataService {
    private OrderDataDAO orderDataDAO;
    /**
     * Creates a new instance of the OrderDataService class.
     */
    public OrderDataService() {
        this.orderDataDAO = new OrderDataDAO();
    }
    /**
     * Adds a new order to the system.
     * @param orderData The order to be added.
     */
    public void addOrderData(OrderData orderData) {
        orderDataDAO.insert(orderData);
        logBill(orderData);
    }
    /**
     * Updates an order in the system.
     * @param orderData The updated order.
     * @param id The ID of the order to be updated.
     */
    public void updateOrderData(OrderData orderData, int id) {
        orderDataDAO.update(orderData, id);
    }
    /**
     * Deletes an order from the system.
     * @param id The ID of the order to be deleted.
     */
    public void deleteOrderData(int id) {
        orderDataDAO.delete(OrderData.class, id);
    }
    /**
     * Retrieves all orders from the system.
     * @return A list of all orders.
     */
    public List<OrderData> getAllOrders() {
        return orderDataDAO.findAll(OrderData.class);
    }
    /**
     * Retrieves an order by its ID.
     * @param orderId The ID of the order to be retrieved.
     * @return The order with the specified ID.
     */
    public OrderData getOrderById(int orderId) {
        return orderDataDAO.findById(OrderData.class, orderId);
    }
    /**
     * Logs a bill for an order.
     * @param orderData The order for which the bill is logged.
     */
    private void logBill(OrderData orderData) {
        double totalAmount = calculateTotalAmount(orderData); // I will implement ths later
        Bill bill = new Bill(orderData.getOrderdataId(), orderData.getClientId(), "Client Name", totalAmount);
        insertBill(bill);
    }
    /**
     * Inserts a bill into the database.
     * @param bill The bill to be inserted.
     */
    private void insertBill(Bill bill) {
        String query = "INSERT INTO log (orderId, clientId, clientName, totalAmount) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bill.orderId());
            statement.setInt(2, bill.clientId());
            statement.setString(3, bill.clientName());
            statement.setDouble(4, bill.totalAmount());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Calculates the total amount of an order.
     * @param orderData The order for which the total amount is calculated.
     * @return The total amount of the order.
     */
    public double calculateTotalAmount(OrderData orderData) {
        double totalAmount = 0.0;
        ProductService productService = new ProductService();
        OrderItemService orderItemService = new OrderItemService();
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrderdataId() == orderData.getOrderdataId()) {
                Product product = productService.getProductById(orderItem.getProductId());
                totalAmount += product.getPrice() * orderItem.getQuantity();
            }
        }

        return totalAmount;
    }
    /**
     * Deletes an order from the system along with all its items.
     * @param orderId The ID of the order to be deleted.
     */
    public void deleteOrderDataWithItems(int orderId) {
        // Delete all associated order items and update product stock
        OrderItemService orderItemService = new OrderItemService();
        orderItemService.deleteOrderItemsByOrderId(orderId);

        // Delete the order data
        deleteOrderData(orderId);
    }
}
