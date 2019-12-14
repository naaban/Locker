package com.santhos.truelocker;

import android.annotation.SuppressLint;
import android.content.Context;

public interface MainActivityView {
    void showSpinner();
    void hideSpinner();
    void OnFailed(String message);
    void OnNetworkError(Throwable err);
    void OnStatusSuccess();
    void OnKeyReceived(Integer key);
    void OnOtpSuccess();
}
