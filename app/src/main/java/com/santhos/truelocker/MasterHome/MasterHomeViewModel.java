package com.santhos.truelocker.MasterHome;

import android.content.Context;

import com.santhos.truelocker.ApiService.Api;
import com.santhos.truelocker.ApiService.ApiClient;
import com.santhos.truelocker.Models.Response;
import com.santhos.truelocker.MyConst.MyConst;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MasterHomeViewModel implements MasterHomePresenter {
    
    
    private MasterHomeView materHomeView ;
    public MasterHomeViewModel(MasterHomeView materHomeView) {
        this.materHomeView = materHomeView;
    }


    @Override
    public void getStatus() {

    }

    @Override
    public void getOpen(Integer otp) {
        materHomeView.showSpinner();
        Api.getInstance(MyConst.getIpAddress((Context) materHomeView, MyConst.IP_ADDRESS)).create(ApiClient.class).getOpen().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Response>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response response) {
                materHomeView.hideSpinner();
                    materHomeView.OnOpenSuccess();

            }

            @Override
            public void onError(Throwable e) {
                materHomeView.hideSpinner();
                materHomeView.OnNetworkError(e);
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void getClose(Integer otp) {
        materHomeView.showSpinner();
        Api.getInstance(MyConst.getIpAddress((Context) materHomeView, MyConst.IP_ADDRESS)).create(ApiClient.class).getClose().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Response>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response response) {
                materHomeView.hideSpinner();
                materHomeView.OnCloseSuccess();

            }

            @Override
            public void onError(Throwable e) {
                materHomeView.hideSpinner();
                materHomeView.OnNetworkError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
