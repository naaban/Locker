package com.santhos.truelocker.Login;

public class LoginViewModel implements  LoginPresenter {

    public LoginView loginView;
    public LoginViewModel(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void getLogin(String username, String password) {
        loginView.showSpinner();
        if(username.equals("admin") && password.equals("admin")){
            loginView.hideSpinner();
            loginView.OnLoginSuccess();
        }
        else {
            loginView.OnFailed("Failed to login");
            loginView.hideSpinner();
        }
    }
}
