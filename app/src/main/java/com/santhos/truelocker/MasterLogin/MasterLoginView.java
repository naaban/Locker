package com.santhos.truelocker.MasterLogin;

public interface MasterLoginView {
    void showSpinner();
    void hideSpinner();
    void OnLoginSuccess();
    void OnFailed(String message);
    void OnNetworkError(Throwable err);
}
