package ua.goit.hw5.model;

public enum OrderStatus {
    PLACED("placed"),
    APPROVED("approved"),
    DELIVERED("delivered");

    private String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
