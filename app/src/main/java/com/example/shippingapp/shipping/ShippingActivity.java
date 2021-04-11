package com.example.shippingapp.shipping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.shippingapp.R;
import com.example.shippingapp.databinding.ActivityShippingBinding;
import com.example.shippingapp.network.HttpRequestInterceptor;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShippingActivity extends AppCompatActivity {
    // Activityはコンストラクターインジェクションができないので、フィールドインジェクションする
    @Inject
    public HttpRequestInterceptor mHttpRequestInterceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ビューモデルのインスタンスを取得(ViewModelはViewModelProviderを介してインスタンス取得する。@Injectでのインジェクションはエラーになる)
        ShippingViewModel shippingViewModel = new ViewModelProvider(this).get(ShippingViewModel.class);

        // DataBindingでバインドする
        ActivityShippingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping);
        // レイアウトXMLでViewModelを使うために変数にセットしておく
        binding.setShippingViewModel(shippingViewModel);
        // LiveDataで変更を監視するためのLifecycleOwnerのセット
        binding.setLifecycleOwner(this);

        // OkHttpのインターセプターにBaseUrlと認証情報セット
        // 最終的には設定ファイルから取得する予定
        mHttpRequestInterceptor.setBaseUrl("http://10.0.2.2:8080");
        mHttpRequestInterceptor.setUserPassword("mtsuboi", "mtsuboipass");
    }
}