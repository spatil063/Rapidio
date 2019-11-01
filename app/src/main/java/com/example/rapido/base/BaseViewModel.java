package com.example.rapido.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.rapido.BaseApp;
import com.example.rapido.R;
import com.example.rapido.utils.NoInternetException;

import java.net.ConnectException;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<N extends BaseNavigator> extends ViewModel {

    protected N mNavigator;
    protected MutableLiveData<Boolean> dialogVisibility = new MutableLiveData<>(false);
    protected MutableLiveData<String> dialogMessage = new MutableLiveData<>("");
    protected CompositeDisposable mDisposable = new CompositeDisposable();
    MutableLiveData<Boolean> isTokenExpire = new MutableLiveData<>(false);


    void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    protected void checkError(Throwable throwable) {
        dialogVisibility.setValue(false);
        if (throwable instanceof NoInternetException) {
            mNavigator.onNoInternet();
        } else {
            String throwableMessage = throwable.getMessage();
            if (throwableMessage.contains("HTTP 401")) {
                isTokenExpire.setValue(true);
            } else if (throwableMessage.contains("500") || throwableMessage.contains("connection abort")
                    || throwableMessage.contains("Socket") || throwable instanceof ConnectException) {
                mNavigator.onError(BaseApp.getContext().getString(R.string.no_internet_connection) + "\n" + throwableMessage);
            } else {
                mNavigator.onError(throwableMessage);
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mNavigator = null;
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
