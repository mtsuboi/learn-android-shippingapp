package com.example.shippingapp;

public interface BasePresenter<T> {
    public void takeView(T view);
    public void dropView();
}
