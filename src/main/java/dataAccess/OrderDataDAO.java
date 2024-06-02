package dataAccess;

import model.OrderData;

import java.sql.*;
/**
 * This class is responsible for managing the order data in the system.
 */
public class OrderDataDAO extends DataAccess<OrderData> {

    /**
     * Adds a new order to the system.
     * @param orderData The order to be added.
     */
    @Override
    public void insert(OrderData orderData) {
        String query = "INSERT INTO orderdata (clientId, orderDate) VALUES (?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, orderData.getClientId());
            statement.setTimestamp(2, Timestamp.valueOf(orderData.getOrderDate()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderData.setOrderdataId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
