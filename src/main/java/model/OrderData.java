package model;

import java.time.LocalDateTime;
/**
 * This class represents an order in the system.
 */
public class OrderData {
    private int orderdataId;
    private int clientId;
    private LocalDateTime orderDate;

    /**
     * Creates a new instance of the OrderData class.
     */
    public OrderData() {
    }
    /**
     * Creates a new instance of the OrderData class.
     * @param orderdataId The ID of the order.
     * @param clientId The ID of the client.
     * @param orderDate The date of the order.
     */
    public OrderData(int orderdataId, int clientId, LocalDateTime orderDate) {
        this.orderdataId = orderdataId;
        this.clientId = clientId;
        this.orderDate = orderDate;
    }
    /**
     * Retrieves the ID of the order.
     * @return The ID of the order.
     */
    public int getOrderdataId() {
        return orderdataId;
    }
    /**
     * Retrieves the ID of the client.
     * @return The ID of the client.
     */
    public int getClientId() {
        return clientId;
    }
    /**
     * Retrieves the date of the order.
     * @return The date of the order.
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    /**
     * Sets the ID of the order.
     * @param orderdataId The ID of the order.
     */
    public void setOrderdataId(int orderdataId) {
        this.orderdataId = orderdataId;
    }
    /**
     * Sets the ID of the client.
     * @param clientId The ID of the client.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    /**
     * Sets the date of the order.
     * @param orderDate The date of the order.
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}