package com.example.shippingapp.shipping;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shippingapp.R;

import dagger.hilt.android.AndroidEntryPoint;

public class EntryOrderIdFragment extends Fragment {
    private static final String CURRENT_ORDER_ID = "CURRENT_ORDER_ID";

    private EditText mEditOrderId;
    private Button mButtonConfirm;

    public static EntryOrderIdFragment newInstance() { return new EntryOrderIdFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_entry_order_id, container, false);
        mEditOrderId = (EditText) root.findViewById(R.id.edit_order_id);
        mButtonConfirm = (Button) root.findViewById(R.id.button_confirm);

        // 確認ボタン押下時
        mButtonConfirm.setOnClickListener(v -> {
            // ソフトキーボードを閉じる
            hideSoftKeyboard(v);

            // 受注を取得
            ShippingActivity shippingActivity = (ShippingActivity) getActivity();
            shippingActivity.getOrder(mEditOrderId.getText().toString());
        });

        if(savedInstanceState != null) {
            mEditOrderId.setText(savedInstanceState.getString(CURRENT_ORDER_ID), EditText.BufferType.NORMAL);
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CURRENT_ORDER_ID, mEditOrderId.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private void hideSoftKeyboard(View view) {
        // ソフトキーボードを閉じる
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}