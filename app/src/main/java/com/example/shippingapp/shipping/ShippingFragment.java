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
import androidx.lifecycle.ViewModelProvider;

import com.example.shippingapp.R;
import com.example.shippingapp.constraints.ShippingConfirmResult;
import com.example.shippingapp.constraints.ShippingOperation;

public class ShippingFragment extends Fragment {

    private ShippingViewModel mShippingViewModel;

    private TextView mResultMessage;
    private TextView mOrderId;
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
        mOrderId = (TextView) root.findViewById(R.id.order_id);
        mCusomerName = (TextView) root.findViewById(R.id.customer_name);
        mOrderDate = (TextView) root.findViewById(R.id.order_date);
        mOrderStatus = (TextView) root.findViewById(R.id.order_status);
        mButtonChangeStatus = (Button) root.findViewById(R.id.button_change_status);

        // ビューモデルのインスタンスを取得(ViewModelはViewModelProviderを介してインスタンス取得する。@Injectでのインジェクションはエラーになる)
        mShippingViewModel = new ViewModelProvider(this.getActivity()).get(ShippingViewModel.class);
        // ビューモデルのshippingUIを監視して、変更をUIに反映させる。（変更またはLifecycleイベントで反応）
        mShippingViewModel.getShippingUI().observe(getViewLifecycleOwner(), shippingUI -> updateShippingUI(shippingUI));

        // ステータス更新ボタン押下時
        mButtonChangeStatus.setOnClickListener(v ->  {
            // ステータス更新処理
        });

        return root;
    }

    public void updateShippingUI(ShippingUI shippingUI) {
        // shippingUIで受け取ったフィールドををFragmentに反映させる
        mResultMessage.setText(getResultMessage(shippingUI.getShippingConfirmResult()));
        mOrderId.setText(shippingUI.getOrderId()==null ? "" : shippingUI.getOrderId());
        mCusomerName.setText(shippingUI.getCustomerName()==null ? "" : shippingUI.getCustomerName());
        mOrderDate.setText(shippingUI.getOrderDate()==null ? "" : shippingUI.getOrderDate().toString());
        mOrderStatus.setText(shippingUI.getOrderStatus()==null ? "" : shippingUI.getOrderStatus().getText());
        mButtonChangeStatus.setText(getButtonText(shippingUI.getShippingOperation()));
        mButtonChangeStatus.setVisibility(getButtonText(shippingUI.getShippingOperation())==R.string.empty ? View.INVISIBLE : View.VISIBLE);
    }

    private int getResultMessage(ShippingConfirmResult shippingConfirmResult) {
        // 確認結果によりメッセージを返す
        switch (shippingConfirmResult) {
            case SHIPPING:
                return R.string.result_message_shipping;
            case SHIPPED:
                return R.string.result_message_shipped;
            case CANCELED:
                return R.string.result_message_canceled;
            case NO_DATA_FOUND:
            default:
                return R.string.result_message_no_data_found;
        }
    }

    private int getButtonText(ShippingOperation shippingOperation) {
        // 確認結果によりボタンテキストを返す
        switch (shippingOperation) {
            case SHIP:
                return R.string.button_change_status_shipped;
            case CANCEL_SHIPPING:
                return R.string.button_change_status_cancel;
            case NONE:
            default:
                return R.string.empty;
        }
    }
}