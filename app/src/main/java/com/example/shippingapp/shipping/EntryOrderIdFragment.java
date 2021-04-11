package com.example.shippingapp.shipping;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.shippingapp.R;
import com.example.shippingapp.databinding.FragmentEntryOrderIdBinding;

public class EntryOrderIdFragment extends Fragment {

    public static EntryOrderIdFragment newInstance() { return new EntryOrderIdFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // ビューモデルのインスタンスを取得(ViewModelはViewModelProviderを介してインスタンス取得する。@Injectでのインジェクションはエラーになる)
        ShippingViewModel shippingViewModel = new ViewModelProvider(this.getActivity()).get(ShippingViewModel.class);

        // DataBindingでバインドする
        FragmentEntryOrderIdBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_entry_order_id, container, false);
        // レイアウトXMLでViewModelを使うために変数にセットしておく
        binding.setShippingViewModel(shippingViewModel);
        // LiveDataで変更を監視するためのLifecycleOwnerのセット
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // 確認ボタン押下時（レイアウトxmlから直接getOrderを呼ぶこともできるが、ソフトキーボードをここで閉じたいので）
        binding.setOnButtonConfirmClick(v -> {
            // ソフトキーボードを閉じる
            hideSoftKeyboard(v);
            // 受注を取得
            shippingViewModel.getOrder();
        });

        return binding.getRoot();
    }

    private void hideSoftKeyboard(View view) {
        // ソフトキーボードを閉じる
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}