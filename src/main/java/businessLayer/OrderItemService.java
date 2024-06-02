package businessLayer;

import dataAccess.OrderItemDAO;
import model.OrderItem;
import model.Product;

import java.util.List;
/**
 * This class is responsible for managing the order items in the system.
 */
public class OrderItemService {
    private OrderItemDAO orderItemDAO;
    /**
     * Creates a new instance of the OrderItemService class.
     */
    public OrderItemService() {
        this.orderItemDAO = new OrderItemDAO();
    }
    /**
     * Adds a new order item to the system.
     * @param orderItem The order item to be added.
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItemDAO.insert(orderItem);
    }
    /**
     * Updates an order item in the system.
     * @param orderItem The updated order item.
     * @param id The ID of the order item to be updated.
     */
    public void updateOrderItem(OrderItem orderItem, int id) {
        orderItemDAO.update(orderItem, id);
    }
    /**
     * Deletes an order item from the system.
     * @param id The ID of the order item to be deleted.
     */
    public void deleteOrderItem(int id) {
        orderItemDAO.delete(OrderItem.class, id);
    }
    /**
     * Retrieves an order item by its ID.
     * @param id The ID of the order item to be retrieved.
     * @return The order item with the specified ID.
     */
    public OrderItem getOrderItemById(int id) {
        return orderItemDAO.findById(OrderItem.class, id);
    }
    /**
     * Retrieves all order items from the system.
     * @return A list of all order items.
     */
    public List<OrderItem> getAllOrderItems() {
        return orderItemDAO.findAll(OrderItem.class);
    }
    /**
     * Deletes all order items associated with a specific order.
     * @param orderId The ID of the order whose order items are to be deleted.
     */
    public void deleteOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = getAllOrderItems();
        ProductService productService = new ProductService();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrderdataId() == orderId) {

                Product product = productService.getProductById(orderItem.getProductId());
                product.setStock(product.getStock() + orderItem.getQuantity());
                productService.updateProduct(product, product.getProductId());

                deleteOrderItem(orderItem.getOrderitemId());
            }
        }
    }
}
