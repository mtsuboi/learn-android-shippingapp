package com.example.shippingapp.shipping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.shippingapp.R;
import com.example.shippingapp.databinding.ActivityShippingBinding;
import com.example.shippingapp.network.HttpRequestInterceptor;
import com.example.shippingapp.preferences.PreferencesActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShippingActivity extends AppCompatActivity {

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // オプションメニューを作成
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // オプションメニューから「設定」を選択したらPreferenceを表示
        switch (item.getItemId()) {
            case R.id.menu_preferences:
                Intent intent = new Intent(getApplication(), PreferencesActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}