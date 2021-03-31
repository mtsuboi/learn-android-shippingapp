package com.example.shippingapp.network;

import android.util.Log;

import com.example.shippingapp.model.Order;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderAccessImpl implements OrderAccess{

    private OkHttpClient mClient;

    private Moshi mMoshi;

    @Inject
    public OrderAccessImpl(OkHttpClient client, Moshi moshi) {
        this.mClient = client;
        this.mMoshi = moshi;
    }

    @Override
    // Single（1つだけデータを送信するObservable）を使う
    public Single<Order> findById(String orderId) {
        return Single.create(emitter ->  {
            // リクエストURLとパラメータ
            String url = "https://hostname/orderService/findById";
            String queryString="?orderId=" + orderId;

            // 送信用リクエスト作成
            Request request = new Request.Builder()
                    .url(url + queryString)
                    .get().build();

            // リクエスト送信
            Response response = mClient.newCall(request).execute();
            String responseBody = response.body().string();
            // エラーもしくはレスポンスが空の場合はonErrorでExceptionを送信
            if(!response.isSuccessful() || responseBody.isEmpty()) {
                emitter.onError(new Exception("response error or no data found"));
            }

            // レスポンスのJSONをOrderオブジェクトに変換
            JsonAdapter<Order> jsonAdapter = mMoshi.adapter(Order.class);
            Order order = jsonAdapter.fromJson(responseBody);

            // onSuccessでorderを送信
            emitter.onSuccess(order);
        });
    }
}
