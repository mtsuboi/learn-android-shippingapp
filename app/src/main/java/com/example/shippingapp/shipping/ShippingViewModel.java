package com.example.shippingapp.shipping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shippingapp.constraints.ShippingConfirmResult;
import com.example.shippingapp.constraints.ShippingOperation;
import com.example.shippingapp.model.Order;
import com.example.shippingapp.network.OrderAccess;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ShippingViewModel extends ViewModel {
    // ローディングの状態（データのローディング中はtrue）
    private final MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    // 入力用の受注ID（ここに受注IDをセットしてgetOrderメソッドを呼ぶ）
    private final MutableLiveData<String> mEntryOrderId = new MutableLiveData<>();
    // 出荷UIモデル
    private final MutableLiveData<ShippingUI> mShippingUI = new MutableLiveData<>();

    private OrderAccess mOrderAccess;

    @Inject
    public ShippingViewModel(OrderAccess mOrderAccess) {
        this.mOrderAccess = mOrderAccess;
    }

    // 受注を取得
    public void getOrder() {
        // 受注IDで受注を検索
        mOrderAccess.findById(mEntryOrderId.getValue())
            .subscribeOn(Schedulers.io()) // 送信側はスレッドプールで処理する
            .observeOn(AndroidSchedulers.mainThread()) // 受信側はメインスレッドで処理する
            .doOnSubscribe(disposable -> mLoading.setValue(true)) // 開始時にローディングインジケータ表示
            .doFinally(() -> mLoading.setValue(false)) // 終了時にローディングインジケーター非表示
            // 開始
            .subscribe(order -> { // onSuccess（orderを受信）
                // 検索した受注を表示するUIモデルをLiveDataにセット
                mShippingUI.setValue(showOrder(order));
            }, throwable -> { // onError（throwableを受信）
                mShippingUI.setValue(showError());
            });
    }

    private ShippingUI showOrder(Order order) {
        ShippingUI shippingUI = new ShippingUI();

        // 検索した受注をUIモデルにセット
        shippingUI.setOrderId(order.getOrderId());
        shippingUI.setCustomerName(order.getCustomerName());
        shippingUI.setOrderDate(order.getOrderDate());
        shippingUI.setOrderStatus(order.getOrderStatus());

        // 受注ステータスにより確認結果をセット
        switch(order.getOrderStatus()) {
            case ORDER:
            case SHIPPING:
                // 確認結果：出荷中⇒オペレーション：出荷
                shippingUI.setShippingConfirmResult(ShippingConfirmResult.SHIPPING);
                shippingUI.setShippingOperation(ShippingOperation.SHIP);
                break;
            case SHIPPED:
                // 確認結果：出荷済み⇒オペレーション：出荷取消
                shippingUI.setShippingConfirmResult(ShippingConfirmResult.SHIPPED);
                shippingUI.setShippingOperation(ShippingOperation.CANCEL_SHIPPING);
                break;
            default:
                // 確認結果：キャンセル済み⇒オペレーション：なし
                shippingUI.setShippingConfirmResult(ShippingConfirmResult.CANCELED);
                shippingUI.setShippingOperation(ShippingOperation.NONE);
                break;
        }

        return shippingUI;
    }

    private ShippingUI showError() {
        ShippingUI shippingUI = new ShippingUI();

        // 確認結果：エラー（またはデータなし）⇒オペレーション：なし
        shippingUI.setShippingConfirmResult(ShippingConfirmResult.NO_DATA_FOUND);
        shippingUI.setShippingOperation(ShippingOperation.NONE);

        return shippingUI;
    }

    // 単方向データバインディングの場合はLiveDataで渡す
    public LiveData<Boolean> getLoading() {
        return mLoading;
    }

    // 双方向データバインディングの場合はMutableLiveDataで渡す
    public MutableLiveData<String> getEntryOrderId() {
        return mEntryOrderId;
    }

    public LiveData<ShippingUI> getShippingUI() {
        return mShippingUI;
    }
}
