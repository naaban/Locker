package com.santhos.truelocker.MasterHome;

public interface MasterHomeView {
    void showSpinner();
    void hideSpinner();
    void OnFailed(String message);
    void OnNetworkError(Throwable err);
    void OnStatusSuccess();
    void OnOpenSuccess();
    void OnCloseSuccess();
}

