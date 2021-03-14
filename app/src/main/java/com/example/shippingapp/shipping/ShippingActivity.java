package com.example.shippingapp.shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.shippingapp.R;
import com.example.shippingapp.network.OrderAccessImpl;

public class ShippingActivity extends AppCompatActivity {
    private static final String CURRENT_ORDER_ID = "CURRENT_ORDER_ID";

    private ShippingPresenter mShippingPresenter;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        // ローディングインジケーター
        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

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

        // Daggerが難しいのでとりあえず手動でインスタンスを注入する
        // 受注アクセスのインスタンスを作成
        OrderAccessImpl orderAccess = new OrderAccessImpl();
        // 出荷プレゼンターのインスタンスを作成（フラグメントと受注アクセスのインスタンスを注入）
        mShippingPresenter = new ShippingPresenter(shippingFragment, orderAccess);

    }

    public void getOrder(String orderId) {
        // 出荷プレゼンターで受注を取得
        mShippingPresenter.getOrder(orderId);
    }

    public void setLoadingIndicator(boolean active) {
        if(active) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }
}