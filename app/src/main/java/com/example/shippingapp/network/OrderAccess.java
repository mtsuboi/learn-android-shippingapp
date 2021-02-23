package com.example.shippingapp.network;

import com.example.shippingapp.model.Order;

public interface OrderAccess {
    // コールバック用インタフェース
    public interface OrderAccessCallback {
        void onSuccess(Order order);
        void onFailure();
    }

    // 受注をOrder_idで検索する
    public void findById(final String orderId, final OrderAccessCallback callback);
}
