package presentation;

import businessLayer.ClientService;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * This class is responsible for managing the clients in the system.
 */
public class ClientManagementWindow extends JFrame {
    private ClientService clientService = new ClientService();
    //private JTextField nameField;
    //private JTextField emailField;
    private JComboBox<String> editClientDropdown;
    private JComboBox<String> deleteClientDropdown;
    /**
     * Creates a new instance of the ClientManagementWindow class.
     */
    public ClientManagementWindow() {
        setTitle("Client Management");
        setSize(1100, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(createAddClientPanel());
        panel.add(createEditClientPanel());
        panel.add(createDeleteClientPanel());
        panel.add(createViewClientsPanel());

        getContentPane().add(panel);

        refreshClientDropdowns();
    }
    /**
     * Creates a panel for adding a client.
     * @return The panel for adding a client.
     */
    private JPanel createAddClientPanel() {
        JPanel addClientPanel = new JPanel(new GridBagLayout());
        addClientPanel.setBackground(new Color(255, 182, 193));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        addClientPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        addClientPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addClientPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        addClientPanel.add(emailField, gbc);

        JButton addButton = new JButton("Add Client");
        addButton.addActionListener(e -> addClient(nameField, emailField));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        addClientPanel.add(addButton, gbc);

        return addClientPanel;
    }
    /**
     * Creates a panel for editing a client.
     * @return The panel for editing a client.
     */
    private JPanel createEditClientPanel() {
        JPanel editClientPanel = new JPanel(new GridBagLayout());
        editClientPanel.setBackground(new Color(144, 238, 144));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        editClientDropdown = new JComboBox<>();

        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        editClientPanel.add(new JLabel("Select Client:"), gbc);

        gbc.gridx = 1;
        editClientPanel.add(editClientDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        editClientPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        editClientPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        editClientPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        editClientPanel.add(emailField, gbc);

        JButton editButton = new JButton("Edit Client");
        editButton.addActionListener(e -> editClient(nameField, emailField));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        editClientPanel.add(editButton, gbc);

        return editClientPanel;
    }
    /**
     * Creates a panel for deleting a client.
     * @return The panel for deleting a client.
     */
    private JPanel createDeleteClientPanel() {
        JPanel deleteClientPanel = new JPanel(new GridBagLayout());
        deleteClientPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        deleteClientDropdown = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 0;
        deleteClientPanel.add(new JLabel("Select Client:"), gbc);

        gbc.gridx = 1;
        deleteClientPanel.add(deleteClientDropdown, gbc);

        JButton deleteButton = new JButton("Delete Client");
        deleteButton.addActionListener(e -> deleteClient());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        deleteClientPanel.add(deleteButton, gbc);

        return deleteClientPanel;
    }
    /**
     * Creates a panel for viewing all clients.
     * @return The panel for viewing all clients.
     */
    private JPanel createViewClientsPanel() {
        JPanel viewClientsPanel = new JPanel(new BorderLayout());
        viewClientsPanel.setBackground(new Color(240, 230, 140));

        JButton viewClientsButton = new JButton("View All Clients");
        viewClientsButton.addActionListener(e -> new TableView<>(Client.class).setVisible(true));

        viewClientsPanel.add(viewClientsButton, BorderLayout.NORTH);
        JButton backButton = new JButton("Go Back");
        backButton.setBackground(Color.ORANGE);
        backButton.addActionListener(e -> {
            dispose();
            new InitGUI().setVisible(true);
        });
        viewClientsPanel.add(backButton, BorderLayout.SOUTH);
        return viewClientsPanel;
    }
    /**
     * Adds a client to the system.
     * @param nameField The field for the name of the client.
     * @param emailField The field for the email of the client.
     */
    private void addClient(JTextField nameField, JTextField emailField) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email fields cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Client client = new Client(0, name, email);
        clientService.addClient(client);
        nameField.setText("");
        emailField.setText("");
        refreshClientDropdowns();
    }
    /**
     * Edits a client in the system.
     * @param nameField The field for the name of the client.
     * @param emailField The field for the email of the client.
     */
    private void editClient(JTextField nameField, JTextField emailField) {
        String selectedClient = (String) editClientDropdown.getSelectedItem();
        if (selectedClient != null) {
            int clientId = Integer.parseInt(selectedClient.split(":")[0]);
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Email fields cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Client client = new Client(clientId, name, email);
            clientService.updateClient(client, clientId);
            nameField.setText("");
            emailField.setText("");
            refreshClientDropdowns();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client to edit.");
        }
    }
    /**
     * Deletes a client from the system.
     */
    private void deleteClient() {
        String selectedClient = (String) deleteClientDropdown.getSelectedItem();
        if (selectedClient != null) {
            int clientId = Integer.parseInt(selectedClient.split(":")[0]);
            //clientService.deleteClient(clientId);
            clientService.deleteClientWithOrder(clientId);
            refreshClientDropdowns();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client to delete.");
        }
    }
    /**
     * Refreshes the client dropdowns.
     */
    private void refreshClientDropdowns() {
        List<Client> clients = clientService.getAllClients();
        editClientDropdown.removeAllItems();
        deleteClientDropdown.removeAllItems();
        for (Client client : clients) {
            String clientInfo = client.getClientId() + ": " + client.getName();
            editClientDropdown.addItem(clientInfo);
            deleteClientDropdown.addItem(clientInfo);
        }
    }

}

