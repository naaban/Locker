package com.santhos.truelocker;

import android.content.Context;

import com.santhos.truelocker.ApiService.Api;
import com.santhos.truelocker.ApiService.ApiClient;
import com.santhos.truelocker.Models.Response;
import com.santhos.truelocker.Models.Status;
import com.santhos.truelocker.MyConst.MyConst;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel implements  MainActivityPresenter {

    private MainActivityView mainActivityView ;
    private Context context;
    public MainActivityViewModel(MainActivityView mainActivityView , Context context ) {
     this.mainActivityView = mainActivityView;
     this.context = context;
    }


    @Override
    public void getStatus() {
        mainActivityView.showSpinner();
        Api.getInstance(MyConst.getIpAddress( context, MyConst.IP_ADDRESS)).create(ApiClient.class).getStatus().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Status>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Status response) {
                mainActivityView.hideSpinner();
                if( response.getResponse().equals("ok5")) {
                    mainActivityView.OnStatusSuccess();
                }
                else  mainActivityView.OnFailed("Failed to get response");
            }

            @Override
            public void onError(Throwable e) {
                mainActivityView.hideSpinner();
                mainActivityView.OnNetworkError(e);
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void getKey() {
        mainActivityView.showSpinner();
        Api.getInstance(MyConst.getIpAddress(context, MyConst.IP_ADDRESS)).create(ApiClient.class).getOtp().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Response>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response response) {
                mainActivityView.hideSpinner();
                if( response.getResponse().equals("ok")) {
                    mainActivityView.OnKeyReceived(Integer.parseInt(response.getKey()));
                }
                else  mainActivityView.OnFailed("Failed to get response");
            }

            @Override
            public void onError(Throwable e) {
                mainActivityView.hideSpinner();
                mainActivityView.OnNetworkError(e);
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void optAccess(Integer otp) {
        mainActivityView.showSpinner();
        Api.getInstance(MyConst.getIpAddress(context, MyConst.IP_ADDRESS)).create(ApiClient.class).getVerifyOtp(otp).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Response>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response response) {
                mainActivityView.hideSpinner();
                if( response.getResponse().equals("ok")) {
                    mainActivityView.OnOtpSuccess();
                }
                else  mainActivityView.OnFailed("Failed to get response");
            }

            @Override
            public void onError(Throwable e) {
                mainActivityView.hideSpinner();
                mainActivityView.OnNetworkError(e);
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
            mainActivityView.OnStatusSuccess();
        }
        else  mainActivityView.OnFailed("Failed to communicate with device !!!");
    }
}
