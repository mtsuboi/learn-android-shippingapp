package com.example.shippingapp.di;

import com.example.shippingapp.network.HttpRequestInterceptor;
import com.example.shippingapp.network.jsonadapter.LocalDateJsonAdapter;
import com.squareup.moshi.Moshi;

import java.time.LocalDate;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;

@Module
@InstallIn(SingletonComponent.class)
public class HttpModule {

    @Provides
    public static OkHttpClient provideOkHttpClient(HttpRequestInterceptor httpRequestInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpRequestInterceptor)
                .build();
    }

    @Provides
    public static Moshi provideMoshi() {
        return new Moshi.Builder().add(LocalDate.class, new LocalDateJsonAdapter()).build();
    }

}
