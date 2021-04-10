package com.example.shippingapp.shipping;

import com.example.shippingapp.constraints.OrderStatus;
import com.example.shippingapp.constraints.ShippingConfirmResult;
import com.example.shippingapp.constraints.ShippingOperation;

import java.time.LocalDate;

public class ShippingUI {
    private String orderId;
    private String customerName;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private ShippingConfirmResult shippingConfirmResult;
    private ShippingOperation shippingOperation;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ShippingConfirmResult getShippingConfirmResult() {
        return shippingConfirmResult;
    }

    public void setShippingConfirmResult(ShippingConfirmResult shippingConfirmResult) {
        this.shippingConfirmResult = shippingConfirmResult;
    }

    public ShippingOperation getShippingOperation() {
        return shippingOperation;
    }

    public void setShippingOperation(ShippingOperation shippingOperation) {
        this.shippingOperation = shippingOperation;
    }
}
