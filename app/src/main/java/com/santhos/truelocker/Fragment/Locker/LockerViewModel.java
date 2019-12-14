package com.santhos.truelocker.Fragment.Locker;

import android.content.Context;

import com.santhos.truelocker.ApiService.Api;
import com.santhos.truelocker.ApiService.ApiClient;
import com.santhos.truelocker.Models.Status;
import com.santhos.truelocker.MyConst.MyConst;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LockerViewModel implements ILockerPresenter {

    private  ILockerView lockerView;
    private Context context;
    LockerViewModel(ILockerView lockerView , Context context) {
        this.lockerView = lockerView;
        this.context = context;
    }
    @Override
    public void getLockerStatus() {
        lockerView.showSpinner();
        Api.getInstance(MyConst.getIpAddress(context, MyConst.IP_ADDRESS)).create(ApiClient.class).getStatus().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Status>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Status response) {
                lockerView.hideSpinner();
                if(response.getResponse().equals("ok5")) {
                    lockerView.onLockerStatusReceived(response);
                }
                else {
                    lockerView.onFailed("Failed to get data !!");
                }
            }

            @Override
            public void onError(Throwable e) {
                lockerView.hideSpinner();

                lockerView.onNetworkError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getDeviceConnected() {
        MyConst.getServer(context);
        if(MyConst.getIpAddressExists(context)){
            lockerView.OnStatusSuccess();
        }
        else  lockerView.onFailed("Failed to communicate with device !!!");
    }
}
