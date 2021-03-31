package com.example.shippingapp.shipping;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.shippingapp.R;
import com.example.shippingapp.model.Order;
import com.example.shippingapp.network.OrderAccess;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShippingPresenter implements ShippingContract.Presenter {
    private ShippingContract.View mShippingView;

    private OrderAccess mOrderAccess;
    private Disposable mOrderAccessDisposable;

    @Inject
    public ShippingPresenter(OrderAccess orderAccess) {
        this.mOrderAccess = orderAccess;
    }

    // 受注を取得してビューに表示する
    @Override
    public void getOrder(String orderId) {
        // 受注IDで受注を検索
        mOrderAccessDisposable = mOrderAccess.findById(orderId)
            .subscribeOn(Schedulers.io()) // 送信側はスレッドプールで処理する
            .observeOn(AndroidSchedulers.mainThread()) // 受信側はメインスレッドで処理する
            .doOnSubscribe(disposable -> mShippingView.setLoadingIndicator(true)) // 開始時にローディングインジケータ表示
            .doFinally(() -> mShippingView.setLoadingIndicator(false)) // 終了時にローディングインジケーター非表示
            // 開始
            .subscribe(order -> { // onSuccess（orderを受信）
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
            }, throwable -> { // onError（throwableを受信）
                // 画面をクリアしてエラーメッセージを表示
                mShippingView.showEmpty();
                mShippingView.showMessage(R.string.result_message_no_data_found);
            });
    }

    @Override
    public void takeView(ShippingContract.View view) {
        this.mShippingView = view;
    }

    @Override
    public void dropView() {
        if(mOrderAccessDisposable != null) {
            mOrderAccessDisposable.dispose();
        }
        this.mShippingView = null;
    }
}
