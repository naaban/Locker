package com.santhos.truelocker.Fragment.Locker;

import com.santhos.truelocker.Models.Status;

public interface ILockerView {

    void showSpinner();

    void hideSpinner();

    void onNetworkError(Throwable e);

    void onFailed(String message);

    void onLockerStatusReceived(Status status);

    void OnStatusSuccess();

}
