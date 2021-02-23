package com.example.shippingapp.constraints;

public enum OrderStatus {
    ORDER("1","受注"),
    SHIPPING("2","出荷作業中"),
    SHIPPED("3","出荷済"),
    CANCEL("9","キャンセル");

    private final String code;
    private final String text;

    private OrderStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }
    public String getText() {
        return text;
    }

    public static OrderStatus getByCode(String code) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.code.equals(code)){
                return orderStatus;
            }
        }
        throw new IllegalArgumentException();
    }
}

