package com.example.shippingapp.network;

import com.example.shippingapp.model.Order;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderAccess {
    // 受注をOrder_idで検索する
    @GET("orderService/findById")
    public Single<Order> findById(@Query("orderId") final String orderId);

    // ステータスを更新する
    @POST("orderService/changeStatus")
    public Single<Integer> changeStatus(@Body final Order order);
}
