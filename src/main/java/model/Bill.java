package model;
/**
 * This class represents a bill in the system.
 */
public record Bill(int orderId, int clientId, String clientName, double totalAmount) {}

