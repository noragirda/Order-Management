package businessLayer;
import dataAccess.ClientDAO;
import model.Client;
import model.OrderData;

import java.util.List;
/**
 * This class is responsible for managing the clients in the system.
 */
public class ClientService {
    private ClientDAO clientDAO;
    /**
     * Creates a new instance of the ClientService class.
     */
    public ClientService() {
        this.clientDAO = new ClientDAO();
    }
    /**
     * Adds a new client to the system.
     * @param client The client to be added.
     */
    public void addClient(Client client) {
        clientDAO.insert(client);
    }
    /**
     * Retrieves all clients from the system.
     * @return A list of all clients.
     */
    public List<Client> getAllClients() {
        return clientDAO.findAll(Client.class);
    }
    /**
     * Retrieves a client by its ID.
     * @param id The ID of the client to be retrieved.
     * @return The client with the specified ID.
     */
    public Client getClientById(int id) {
        return clientDAO.findById(Client.class, id);
    }
    /**
     * Updates a client in the system.
     * @param client The updated client.
     * @param id The ID of the client to be updated.
     */
    public void updateClient(Client client, int id) {
        clientDAO.update(client, id);
    }
    /**
     * Deletes a client from the system.
     * @param id The ID of the client to be deleted.
     */
    public void deleteClient(int id) {
        clientDAO.delete(Client.class, id);
    }
    /**
     * Deletes a client from the system along with all its orders.
     * @param clientId The ID of the client to be deleted.
     */
    public void deleteClientWithOrder(int clientId) {
        // Delete all orders associated with the client
        OrderDataService orderDataService = new OrderDataService();
        List<OrderData> orders = orderDataService.getAllOrders();
        for (OrderData order : orders) {
            if (order.getClientId() == clientId) {
                orderDataService.deleteOrderDataWithItems(order.getOrderdataId());
            }
        }

        // Delete the client
        deleteClient(clientId);
    }
}
