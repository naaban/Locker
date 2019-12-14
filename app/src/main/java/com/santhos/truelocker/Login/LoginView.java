package com.santhos.truelocker.Login;

public interface LoginView {
    void showSpinner();
    void hideSpinner();
    void OnLoginSuccess();
    void OnFailed(String message);
    void OnNetworkError(Throwable err);
}
