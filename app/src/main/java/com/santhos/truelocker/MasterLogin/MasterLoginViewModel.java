package com.santhos.truelocker.MasterLogin;

public class MasterLoginViewModel implements MasterLoginPresenter {
    public MasterLoginView loginView;
    public MasterLoginViewModel(MasterLoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void getLogin(String username, String password) {
        loginView.showSpinner();
        if(username.equals("admin") && password.equals("admin")){
            loginView.showSpinner();
            loginView.OnLoginSuccess();
        }
        else {
            loginView.OnFailed("Failed to login");
            loginView.hideSpinner();
        }
    }
}
