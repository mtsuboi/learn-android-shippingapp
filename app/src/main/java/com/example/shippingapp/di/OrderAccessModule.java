package com.example.shippingapp.di;

import com.example.shippingapp.network.OrderAccess;
import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(ActivityComponent.class)
public class OrderAccessModule {

    @Provides
    public static OrderAccess provideOrderAccess(OkHttpClient client, Moshi moshi) {
        String baseUrl = "https://hostname/";

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build();

        return retrofit.create(OrderAccess.class);
    }
}
