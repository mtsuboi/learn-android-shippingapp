package com.example.shippingapp.shipping;

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

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShippingFragment extends Fragment implements ShippingContract.View {
    private static final String STATE_RESULT_MESSAGE = "STATE_RESULT_MESSAGE";
    private static final String STATE_ORDER_ID = "STATE_ORDER_ID";
    private static final String STATE_CUSTOMER_NAME = "STATE_CUSTOMER_NAME";
    private static final String STATE_ORDER_DATE = "STATE_ORDER_DATE";
    private static final String STATE_ORDER_STATUS = "STATE_ORDER_STATUS";
    private static final String STATE_BUTTON_TEXT = "STATE_BUTTON_TEXT";
    private static final String STATE_BUTTON_VISIBLE = "STATE_BUTTON_VISIBLE";

    @Inject
    public ShippingContract.Presenter mPresenter;

    private TextView mResultMessage;
    private TextView mOrderId;
    private TextView mCusomerName;
    private TextView mOrderDate;
    private TextView mOrderStatus;
    private Button mButtonChangeStatus;

    @Inject
    public ShippingFragment() {}

    //public static ShippingFragment newInstance() { return new ShippingFragment(); }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shipping, container, false);
        mResultMessage = (TextView) root.findViewById(R.id.result_message);
        mOrderId = (TextView) root.findViewById(R.id.order_id);
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

        if(savedInstanceState != null) {
            restoreSavingState(savedInstanceState);
        }

        return root;
    }

    private void restoreSavingState(Bundle savedInstanceState) {
        mResultMessage.setText(savedInstanceState.getString(STATE_RESULT_MESSAGE), TextView.BufferType.NORMAL);
        mOrderId.setText(savedInstanceState.getString(STATE_ORDER_ID), TextView.BufferType.NORMAL);
        mCusomerName.setText(savedInstanceState.getString(STATE_CUSTOMER_NAME), TextView.BufferType.NORMAL);
        mOrderDate.setText(savedInstanceState.getString(STATE_ORDER_DATE), TextView.BufferType.NORMAL);
        mOrderStatus.setText(savedInstanceState.getString(STATE_ORDER_STATUS), TextView.BufferType.NORMAL);
        mButtonChangeStatus.setText(savedInstanceState.getString(STATE_BUTTON_TEXT));
        mButtonChangeStatus.setVisibility(savedInstanceState.getInt(STATE_BUTTON_VISIBLE));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_RESULT_MESSAGE, mResultMessage.getText().toString());
        outState.putString(STATE_ORDER_ID, mOrderId.getText().toString());
        outState.putString(STATE_CUSTOMER_NAME, mCusomerName.getText().toString());
        outState.putString(STATE_ORDER_DATE, mOrderDate.getText().toString());
        outState.putString(STATE_ORDER_STATUS, mOrderStatus.getText().toString());
        outState.putString(STATE_BUTTON_TEXT, mButtonChangeStatus.getText().toString());
        outState.putInt(STATE_BUTTON_VISIBLE, mButtonChangeStatus.getVisibility());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showEmpty() {
        mResultMessage.setText(R.string.empty);
        mOrderId.setText(R.string.empty);
        mCusomerName.setText(R.string.empty);
        mOrderDate.setText(R.string.empty);
        mOrderStatus.setText(R.string.empty);
        hideButton();
    }

    @Override
    public void showOrder(Order order) {
        mOrderId.setText(order.getOrderId());
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

    @Override
    public void setLoadingIndicator(boolean active) {
        ShippingActivity shippingActivity = (ShippingActivity) getActivity();
        shippingActivity.setLoadingIndicator(active);
    }

    @Override
    public void setPresenter(ShippingContract.Presenter presenter) { this.mPresenter = presenter; }
}