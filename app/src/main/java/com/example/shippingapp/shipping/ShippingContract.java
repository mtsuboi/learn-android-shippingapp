package com.example.shippingapp.shipping;

import androidx.annotation.StringRes;

import com.example.shippingapp.BasePresenter;
import com.example.shippingapp.BaseView;
import com.example.shippingapp.model.Order;

public interface ShippingContract {
    public interface View extends BaseView<Presenter> {
        public void setLoadingIndicator(boolean active);
        public void showEmpty();
        public void showOrder(final Order order);
        public void showMessage(final @StringRes int messageId);
        public void showButton(final @StringRes int buttonStringId);
        public void hideButton();
    }

    public interface Presenter extends BasePresenter<View> {
        public void getOrder(final String orderId);
    }
}
