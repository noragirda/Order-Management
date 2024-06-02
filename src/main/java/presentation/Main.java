package presentation;

import dataAccess.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            if (connection != null) {
                System.out.println("Connection successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}