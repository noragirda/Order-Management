package dataAccess;

import model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * This class is responsible for managing the order items in the system.
 */
public class OrderItemDAO extends DataAccess<OrderItem> {

    /**
     * Adds a new order item to the system.
     * @param orderItem The order item to be added.
     */
    @Override
    public void insert(OrderItem orderItem) {
        String query = "INSERT INTO orderitem (orderdataId, productId, quantity) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderItem.getOrderdataId());
            statement.setInt(2, orderItem.getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

