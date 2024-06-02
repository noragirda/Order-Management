package model;
/**
 * This class represents a client in the system.
 */
public class Client {
    private int clientId;
    private String name;
    private String email;

    /**
     * Creates a new instance of the Client class.
     */
    public Client() {
    }

    /**
     * Creates a new instance of the Client class.
     * @param clientId The ID of the client.
     * @param name The name of the client.
     * @param email The email of the client.
     */
    public Client(int clientId, String name, String email) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
    }
    /**
     * Retrieves the ID of the client.
     * @return The ID of the client.
     */
    public int getClientId() {
        return clientId;
    }
    /**
     * Retrieves the name of the client.
     * @return The name of the client.
     */
    public String getName() {
        return name;
    }
    /**
     * Retrieves the email of the client.
     * @return The email of the client.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the ID of the client.
     * @param clientId The ID of the client.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    /**
     * Sets the name of the client.
     * @param name The name of the client.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the email of the client.
     * @param email The email of the client.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}