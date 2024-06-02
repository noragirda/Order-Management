package dataAccess;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Logger;
/**
 * This class is responsible for creating connections to the database.
 */
public class ConnectionFactory {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Logger LOGGER=Logger.getLogger(ConnectionFactory.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/ordermanager";
    private static final String USER = "root";
    private static final String PASSWORD = "Norica11mysql.ro";
    /**
     * The single instance of the class.
     */
    private static ConnectionFactory singleInstance = new ConnectionFactory();
    /**
     * Creates a new instance of the ConnectionFactory class.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a connection to the database.
     * @return The connection to the database.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }
    /**
     * Creates a connection to the database.
     * @return The connection to the database.
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * Closes the specified connection.
     * @param connection The connection to be closed.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Closes the specified statement.
     * @param statement The statement to be closed.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(ResultSet resultset) {
        if (resultset != null) {
            try {
                resultset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}