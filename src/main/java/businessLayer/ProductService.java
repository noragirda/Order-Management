package businessLayer;

import dataAccess.ProductDAO;
import model.Product;

import java.util.List;
/**
 * This class is responsible for managing the products in the system.
 */
public class ProductService {
    private ProductDAO productDAO;
    /**
     * Creates a new instance of the ProductService class.
     */
    public ProductService() {
        this.productDAO = new ProductDAO();
    }
    /**
     * Adds a new product to the system.
     * @param product The product to be added.
     */
    public void addProduct(Product product) {
        productDAO.insert(product);
    }
    /**
     * Updates a product in the system.
     * @param product The updated product.
     * @param id The ID of the product to be updated.
     */
    public void updateProduct(Product product, int id) {
        productDAO.update(product, id);
    }
    /**
     * Deletes a product from the system.
     * @param id The ID of the product to be deleted.
     */
    public void deleteProduct(int id) {
        productDAO.delete(Product.class, id);
    }
    /**
     * Retrieves a product by its ID.
     * @param id The ID of the product to be retrieved.
     * @return The product with the specified ID.
     */
    public Product getProductById(int id) {
        return productDAO.findById(Product.class, id);
    }
    /**
     * Retrieves all products from the system.
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        return productDAO.findAll(Product.class);
    }
}
