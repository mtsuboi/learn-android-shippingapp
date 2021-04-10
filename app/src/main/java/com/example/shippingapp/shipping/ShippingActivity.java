package com.example.shippingapp.shipping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.shippingapp.R;
import com.example.shippingapp.network.HttpRequestInterceptor;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShippingActivity extends AppCompatActivity {
    // Activityはコンストラクターインジェクションができないので、フィールドインジェクションする
    @Inject
    public HttpRequestInterceptor mHttpRequestInterceptor;

    private ShippingViewModel mShippingViewModel;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        // ローディングインジケーター
        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        // ビューモデルのインスタンスを取得(ViewModelはViewModelProviderを介してインスタンス取得する。@Injectでのインジェクションはエラーになる)
        mShippingViewModel = new ViewModelProvider(this).get(ShippingViewModel.class);
        // フラグメント作成
        createFragment();

        // OkHttpのインターセプターにBaseUrlと認証情報セット
        // 最終的には設定ファイルから取得する予定
        mHttpRequestInterceptor.setBaseUrl("http://10.0.2.2:8080");
        mHttpRequestInterceptor.setUserPassword("mtsuboi", "mtsuboipass");

        // ビューモデルのloadingフィールドを監視して、ローディングインジケータをON・OFFする
        mShippingViewModel.getLoading().observe(this, loading -> setLoadingIndicator(loading));
    }

    public void getOrder(String orderId) {
        // 出荷ビューモデルで受注を取得
        mShippingViewModel.getOrder(orderId);
    }

    public void setLoadingIndicator(boolean active) {
        // ローディングインジケータをON・OFFする
        if(active) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    private void createFragment() {
        // 受注ID入力フラグメントを作成
        EntryOrderIdFragment entryOrderIdFragment = (EntryOrderIdFragment) getSupportFragmentManager().findFragmentById(R.id.frame_entry_order_id);
        if(entryOrderIdFragment == null) {
            entryOrderIdFragment = EntryOrderIdFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_entry_order_id, entryOrderIdFragment).commit();
        }

        // 出荷フラグメントを作成
        ShippingFragment shippingFragment = (ShippingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_shipping);
        if(shippingFragment == null) {
            shippingFragment = ShippingFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_shipping, shippingFragment).commit();
        }
    }
}