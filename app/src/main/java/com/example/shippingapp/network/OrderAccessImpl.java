package com.example.shippingapp.network;

import android.util.Log;

import com.example.shippingapp.model.Order;
import com.example.shippingapp.network.jsonadapter.LocalDateJsonAdapter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDate;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderAccessImpl implements OrderAccess{

    @Override
    public void findById(String orderId, OrderAccessCallback callback) {
        // リクエストURLとパラメータ
        String url = "http://10.0.2.2:8080/orderService/findById";
        String queryString="?orderId=" + orderId;

        // HTTPクライアントオブジェクト作成
        OkHttpClient client = new OkHttpClient();

        // 送信用リクエスト作成
        Request request = new Request.Builder()
                .url(url + queryString)
                .header("Authorization",
                        Credentials.basic("mtsuboi","mtsuboipass"))
                .get().build();
        Log.i("request", request.url().toString());

        // リクエスト送信
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseBody = response.body().string();
                // エラーもしくはレスポンスが空の場合はonFailureをコールバックして中断
                if(!response.isSuccessful() || responseBody.isEmpty()) {
                    callback.onFailure();
                    return;
                }

                // レスポンスのJSONをOrderオブジェクトに変換してonSuccessをコールバック
                Moshi moshi = new Moshi.Builder().add(LocalDate.class, new LocalDateJsonAdapter()).build();
                JsonAdapter<Order> jsonAdapter = moshi.adapter(Order.class);
                try {
                    Order order = jsonAdapter.fromJson(responseBody);
                    callback.onSuccess(order);
                } catch (Exception e) {
                    Log.e("json parse error", "parse Order: ", e);
                    callback.onFailure();
                }
            }
        });

    }
}
