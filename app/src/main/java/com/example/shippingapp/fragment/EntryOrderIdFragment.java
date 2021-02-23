package com.example.shippingapp.fragment;

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

import com.example.shippingapp.R;
import com.example.shippingapp.presenter.ShippingPresenter;

public class EntryOrderIdFragment extends Fragment {

    private ShippingPresenter shippingPresenter;

    private EditText mEditOrderId;
    private Button mButtonConfirm;

    public static EntryOrderIdFragment newInstance() {
        return new EntryOrderIdFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_entry_order_id, container, false);
        mEditOrderId = (EditText) root.findViewById(R.id.edit_order_id);
        mButtonConfirm = (Button) root.findViewById(R.id.button_confirm);

        // 確認ボタン押下時
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ソフトキーボードを閉じる
                hideSoftKeyboard(v);

                // 受注を取得
                shippingPresenter.getOrder(mEditOrderId.getText().toString());
            }
        });

        return root;
    }

    public void setShippingPresenter(ShippingPresenter shippingPresenter) {
        this.shippingPresenter = shippingPresenter;
    }

    private void hideSoftKeyboard(View view) {
        // ソフトキーボードを閉じる
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}