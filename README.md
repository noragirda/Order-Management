# Order-Management
=======
# Overview

The Order Management System is a Java-based application designed to manage clients, products, and orders efficiently. The system allows for the creation, updating, and deletion of clients, products, and orders, along with the functionality to view bills and manage order items. The application uses a MySQL database for data storage and retrieval.

## Features

Client Management: Add, edit, delete, and view clients.
Product Management: Add, edit, delete, and view products.
Order Management: Create, edit, delete, and view orders.
Bill Management: View detailed bills for each order.
Database Interaction: Seamless integration with a MySQL database for data storage.

## Getting Started
### Prerequisites
Java Development Kit (JDK) 11 or higher
MySQL Server
Maven (for dependency management)

## Setting Up the Database
1. Create a database named ordermanager in your MySQL server.
2. Run the SQL script provided in the sql/database.sql file to create the necessary tables.

## Building and running the app
Clone the repo 

## Configuration
Update the database connection details in the dataAccess/ConnectionFactory.java file:

private static final String URL = "jdbc:mysql://localhost:3306/ordermanager";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
