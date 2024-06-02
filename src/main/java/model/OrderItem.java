package model;

public class OrderItem {
    private int orderitemId;
    private int orderdataId;
    private int productId;
    private int quantity;

   /**
     * Creates a new instance of the OrderItem class.
     */
    public OrderItem() {
    }
    /**
     * Creates a new instance of the OrderItem class.
     * @param orderitemId The ID of the order item.
     * @param orderdataId The ID of the order.
     * @param productId The ID of the product.
     * @param quantity The quantity of the product.
     */
    public OrderItem(int orderitemId, int orderdataId, int productId, int quantity) {
        this.orderitemId = orderitemId;
        this.orderdataId = orderdataId;
        this.productId = productId;
        this.quantity = quantity;
    }
    /**
     * Retrieves the ID of the order item.
     * @return The ID of the order item.
     */
    public int getOrderitemId() {
        return orderitemId;
    }
    /**
     * Retrieves the ID of the order.
     * @return The ID of the order.
     */
    public int getOrderdataId() {
        return orderdataId;
    }
    /**
     * Retrieves the ID of the product.
     * @return The ID of the product.
     */
    public int getProductId() {
        return productId;
    }
    /**
     * Retrieves the quantity of the product.
     * @return The quantity of the product.
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Sets the ID of the order item.
     * @param orderitemId The ID of the order item.
     */
    public void setOrderitemId(int orderitemId) {
        this.orderitemId = orderitemId;
    }
    /**
     * Sets the ID of the order.
     * @param orderdataId The ID of the order.
     */
    public void setOrderdataId(int orderdataId) {
        this.orderdataId = orderdataId;
    }
    /**
     * Sets the ID of the product.
     * @param productId The ID of the product.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }
    /**
     * Sets the quantity of the product.
     * @param quantity The quantity of the product.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}