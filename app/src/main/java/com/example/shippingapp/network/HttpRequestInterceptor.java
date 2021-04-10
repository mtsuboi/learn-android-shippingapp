package com.example.shippingapp.network;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class HttpRequestInterceptor implements Interceptor {

    private String baseUrl;
    private String user;
    private String password;

    @Inject
    public HttpRequestInterceptor() {}

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl parsedBaseUrl = HttpUrl.parse(baseUrl);
        HttpUrl newUrl = request.url().newBuilder()
                .scheme(parsedBaseUrl.scheme())
                .host(parsedBaseUrl.host())
                .port(parsedBaseUrl.port())
                .build();
        Request newRequest = request.newBuilder()
                .url(newUrl)
                .header("Authorization", Credentials.basic(user, password))
                .build();

        Log.i("request", newRequest.url().toString());
        return chain.proceed(newRequest);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setUserPassword(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
