package com.example.shippingapp.di;

import com.example.shippingapp.shipping.EntryOrderIdFragment;
import com.example.shippingapp.shipping.ShippingContract;
import com.example.shippingapp.shipping.ShippingFragment;
import com.example.shippingapp.shipping.ShippingPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public abstract class ShippingModule {

    @Binds
    @ActivityScoped
    public abstract ShippingContract.Presenter bindShippingPresenter(ShippingPresenter shippingPresenter);
}
