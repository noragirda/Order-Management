package model;
/**
 * This class represents a product in the system.
 */
public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;
    /**
     * Creates a new instance of the Product class.
     */
    public Product() {
    }
    /**
     * Creates a new instance of the Product class.
     * @param productId The ID of the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The stock of the product.
     */
    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    /**
     * Retrieves the ID of the product.
     * @return The ID of the product.
     */
    public int getProductId() {
        return productId;
    }
    /**
     * Retrieves the name of the product.
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }
    /**
     * Retrieves the price of the product.
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }
    /**
     * Retrieves the stock of the product.
     * @return The stock of the product.
     */
    public int getStock() {
        return stock;
    }
    /**
     * Sets the ID of the product.
     * @param productId The ID of the product.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }
    /**
     * Sets the name of the product.
     * @param name The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }
/**
     * Sets the price of the product.
     * @param price The price of the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Sets the stock of the product.
     * @param stock The stock of the product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
}