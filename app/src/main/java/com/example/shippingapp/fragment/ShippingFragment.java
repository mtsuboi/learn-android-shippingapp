package com.example.shippingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shippingapp.R;
import com.example.shippingapp.model.Order;
import com.example.shippingapp.presenter.ShippingPresenter;

public class ShippingFragment extends Fragment implements ShippingPresenter.View {

    private ShippingPresenter mPresenter;

    private TextView mResultMessage;
    private TextView mCusomerName;
    private TextView mOrderDate;
    private TextView mOrderStatus;
    private Button mButtonChangeStatus;

    public static ShippingFragment newInstance() { return new ShippingFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shipping, container, false);
        mResultMessage = (TextView) root.findViewById(R.id.result_message);
        mCusomerName = (TextView) root.findViewById(R.id.customer_name);
        mOrderDate = (TextView) root.findViewById(R.id.order_date);
        mOrderStatus = (TextView) root.findViewById(R.id.order_status);
        mButtonChangeStatus = (Button) root.findViewById(R.id.button_change_status);

        // 画面初期化
        showEmpty();

        // ステータス更新ボタン押下時
        mButtonChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ステータス更新処理
            }
        });
        return root;
    }

    @Override
    public void showEmpty() {
        mResultMessage.setText(R.string.empty);
        mCusomerName.setText(R.string.empty);
        mOrderDate.setText(R.string.empty);
        mOrderStatus.setText(R.string.empty);
        hideButton();
    }

    @Override
    public void showOrder(Order order) {
        mCusomerName.setText(order.getCustomerName());
        mOrderDate.setText(order.getOrderDate().toString());
        mOrderStatus.setText(order.getOrderStatus().getText());
    }

    @Override
    public void showMessage(int messageId) {
        mResultMessage.setText(messageId);
    }

    @Override
    public void showButton(int buttonStringId) {
        mButtonChangeStatus.setText(buttonStringId);
        mButtonChangeStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButton() {
        mButtonChangeStatus.setVisibility(View.INVISIBLE);
    }

    public void setPresenter(ShippingPresenter presenter) {
        this.mPresenter = presenter;
    }
}