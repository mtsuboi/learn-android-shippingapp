package com.example.shippingapp.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.shippingapp.constraints.PrefKey;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
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
    public HttpRequestInterceptor(@ApplicationContext Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.baseUrl = sharedPreferences.getString(PrefKey.SERVER_URL,"");
        this.user = sharedPreferences.getString(PrefKey.USER_NAME, "");
        this.password = sharedPreferences.getString(PrefKey.PASSWORD, "");
    }

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

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
