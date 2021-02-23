package com.example.shippingapp.model;

import com.example.shippingapp.constraints.OrderStatus;

import java.time.LocalDate;

public class Order {
    private String orderId;
    private OrderStatus orderStatus;
    private LocalDate orderDate;
    private LocalDate shipDate;
    private String customerName;
    private String customerZipcode;
    private String customerAddress;
    private String customerTel;
    private int orderAmount;

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public LocalDate getShipDate() {
        return shipDate;
    }
    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerZipcode() {
        return customerZipcode;
    }
    public void setCustomerZipcode(String customerZipcode) {
        this.customerZipcode = customerZipcode;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    public String getCustomerTel() {
        return customerTel;
    }
    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }
    public int getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}
