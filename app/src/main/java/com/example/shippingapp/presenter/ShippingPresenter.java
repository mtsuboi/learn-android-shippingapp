package com.example.shippingapp.presenter;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.StringRes;

import com.example.shippingapp.R;
import com.example.shippingapp.fragment.ShippingFragment;
import com.example.shippingapp.model.Order;
import com.example.shippingapp.network.OrderAccess;

public class ShippingPresenter {

    private ShippingFragment mShippingView;
    private OrderAccess orderAccess;

    // 受注を取得してビューに表示する
    public void getOrder(String orderId) {
        // ワーカースレッドのコールバックメソッドでUIスレッドにアクセスするためのハンドラ
        Handler mainHandler = new Handler(Looper.getMainLooper());

        // 受注IDで受注を検索
        orderAccess.findById(orderId, new OrderAccess.OrderAccessCallback() {
            @Override
            public void onSuccess(Order order) {
                // 受注が取得できたらUIスレッドで受注を表示
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 検索した受注を画面に表示
                        mShippingView.showOrder(order);

                        // ステータスによってメッセージとボタンを変える
                        switch(order.getOrderStatus()) {
                            case ORDER:
                            case SHIPPING:
                                mShippingView.showMessage(R.string.result_message_shipping);
                                mShippingView.showButton(R.string.button_change_status_shipped);
                                break;
                            case SHIPPED:
                                mShippingView.showMessage(R.string.result_message_shipped);
                                mShippingView.showButton(R.string.button_change_status_cancel);
                                break;
                            default:
                                mShippingView.showMessage(R.string.result_message_canceled);
                                mShippingView.hideButton();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onFailure() {
                // 受注の取得に失敗したらUIスレッドでエラーを表示
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 画面をクリアしてエラーメッセージを表示
                        mShippingView.showEmpty();
                        mShippingView.showMessage(R.string.result_message_no_data_found);
                    }
                });
            }
        });
    }

    public void setView(ShippingFragment view) {
        this.mShippingView = view;
    }

    public void setOrderAccess(OrderAccess orderAccess) {
        this.orderAccess = orderAccess;
    }

    // Viewのインタフェース
    public interface View {
        public void showEmpty();
        public void showOrder(final Order order);
        public void showMessage(final @StringRes int messageId);
        public void showButton(final @StringRes int buttonStringId);
        public void hideButton();
    }

}
