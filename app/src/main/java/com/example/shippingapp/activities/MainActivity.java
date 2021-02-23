package com.example.shippingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shippingapp.R;
import com.example.shippingapp.fragment.EntryOrderIdFragment;
import com.example.shippingapp.fragment.ShippingFragment;
import com.example.shippingapp.network.OrderAccessImpl;
import com.example.shippingapp.presenter.ShippingPresenter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 受注ID入力フラグメントを作成
        EntryOrderIdFragment entryOrderIdFragment = new EntryOrderIdFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_entry_order_id, entryOrderIdFragment).commit();

        // 出荷フラグメントを作成
        ShippingFragment shippingFragment = new ShippingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_shipping, shippingFragment).commit();

        // Daggerが難しいのでとりあえず手動でインスタンスを注入する
        // 出荷プレゼンターのインスタンスを作成し、フラグメントとプレゼンターにセット
        ShippingPresenter shippingPresenter = new ShippingPresenter();
        entryOrderIdFragment.setShippingPresenter(shippingPresenter);
        shippingFragment.setPresenter(shippingPresenter);

        // 出荷プレゼンターに出荷フラグメントのインスタンスをセット
        shippingPresenter.setView(shippingFragment);

        // 受注アクセスのインスタンスを作成し、プレゼンターにセット
        OrderAccessImpl orderAccess = new OrderAccessImpl();
        shippingPresenter.setOrderAccess(orderAccess);

    }
}