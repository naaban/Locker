package com.santhos.truelocker;

import android.content.Context;

public interface MainActivityPresenter {
    void getStatus();
    void getKey();
    void optAccess(Integer otp);
    void  getDeviceConnected();
}
