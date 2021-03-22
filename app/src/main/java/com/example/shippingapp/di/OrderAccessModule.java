package com.example.shippingapp.di;

import com.example.shippingapp.network.OrderAccess;
import com.example.shippingapp.network.OrderAccessImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class OrderAccessModule {

    @Binds
    public abstract OrderAccess bindOrderAccess(OrderAccessImpl orderAccessImpl);
}
