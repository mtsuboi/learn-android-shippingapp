package com.example.shippingapp;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.shippingapp.network.HttpRequestInterceptor;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class ShippingApplication extends Application {
}
