package com.example.shippingapp.network;

import com.example.shippingapp.model.Order;

import io.reactivex.rxjava3.core.Single;

public interface OrderAccess {
    // 受注をOrder_idで検索する
    public Single<Order> findById(final String orderId);
}
