package com.santhos.truelocker.MasterHome;

public interface MasterHomePresenter {

    void getStatus();
    void getOpen(Integer otp);
    void getClose(Integer otp);
}

