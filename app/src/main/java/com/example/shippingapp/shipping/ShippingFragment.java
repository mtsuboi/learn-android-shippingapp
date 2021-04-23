package com.example.shippingapp.shipping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shippingapp.R;
import com.example.shippingapp.constraints.ShippingConfirmResult;
import com.example.shippingapp.constraints.ShippingOperation;
import com.example.shippingapp.databinding.FragmentShippingBinding;

public class ShippingFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // ビューモデルのインスタンスを取得(ViewModelはViewModelProviderを介してインスタンス取得する。@Injectでのインジェクションはエラーになる)
        ShippingViewModel shippingViewModel = new ViewModelProvider(this.getActivity()).get(ShippingViewModel.class);

        // DataBindingでバインドする
        FragmentShippingBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shipping, container, false);
        // レイアウトXMLでViewModelを使うために変数にセットしておく
        binding.setShippingViewModel(shippingViewModel);
        // LiveDataで変更を監視するためのLifecycleOwnerのセット
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    @BindingAdapter("android:text")
    public static void setText(TextView textView, ShippingConfirmResult shippingConfirmResult) {
        // 確認結果によりメッセージを返す
        if(shippingConfirmResult == null) {
            textView.setText(R.string.empty);
            return;
        }
        switch (shippingConfirmResult) {
            case SHIPPING:
                textView.setText(R.string.result_message_shipping);
                break;
            case SHIPPED:
                textView.setText(R.string.result_message_shipped);
                break;
            case CANCELED:
                textView.setText(R.string.result_message_canceled);
                break;
            case NO_DATA_FOUND:
                textView.setText(R.string.result_message_no_data_found);
                break;
            default:
                textView.setText(R.string.empty);
        }
    }

    @BindingAdapter("android:text")
    public static void setText(Button button, ShippingOperation shippingOperation) {
        // 確認結果によりボタンテキストを返す
        if(shippingOperation == null) {
            button.setText(R.string.empty);
            return;
        }
        switch (shippingOperation) {
            case SHIP:
                button.setText(R.string.button_change_status_shipped);
                break;
            case CANCEL_SHIPPING:
                button.setText(R.string.button_change_status_cancel);
                break;
            case NONE:
            default:
                button.setText(R.string.empty);
        }
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(Button button, ShippingOperation shippingOperation) {
        // 確認結果によりボタンの表示・非表示を返す
        if(shippingOperation == null) {
            button.setVisibility(View.INVISIBLE);
            return;
        }
        switch (shippingOperation) {
            case SHIP:
            case CANCEL_SHIPPING:
                button.setVisibility(View.VISIBLE);
                break;
            case NONE:
            default:
                button.setVisibility(View.INVISIBLE);
        }
    }
}