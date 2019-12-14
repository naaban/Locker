package com.santhos.truelocker.MasterLogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.santhos.truelocker.Login.LoginActivity;
import com.santhos.truelocker.MasterHome.MasterHomeActivity;
import com.santhos.truelocker.MyConst.MyConst;
import com.santhos.truelocker.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MasterLogin extends AppCompatActivity implements MasterLoginView {

    @BindView(R.id.etPassword)
    AppCompatEditText etPassword;


    @BindView(R.id.timer)
    AppCompatTextView timer;

    @BindView(R.id.lgnBtn)
    MaterialButton lgnBtn;



    AlertDialog alertDialog ;

    private  long timee;

    private  int loginCount = 0;

    private long currentCount;

    MasterLoginViewModel loginViewModel;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_login);
        ButterKnife.bind(this);
        alertDialog = MyConst.getLoadingDialog(this);
        loginViewModel = new MasterLoginViewModel(this);
        timer.setVisibility(View.GONE);
    }

    @Override
    public void showSpinner() {
        alertDialog.show();
    }

    @Override
    public void hideSpinner() {
        alertDialog.hide();
    }

    @Override
    public void OnLoginSuccess() {
        Intent intent = new Intent(MasterLogin.this , MasterHomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnFailed(String message) {
        MyConst.showDialog(this , "Failed !" , message);
        loginCount++;
        if(loginCount == MyConst.LOGIN_COUNT) {
            Intent intent = new Intent(MasterLogin.this , LoginActivity.class);
            MyConst.START_TIMER = true;
          startActivity(intent);
          finish();
        }

    }

    @Override
    public void OnNetworkError(Throwable err) {
        if(err instanceof IOException) {
            MyConst.showDialog(this , "Server Connection Lost " , "Failed to get response");
        }
        else {
            MyConst.showDialog(this , "Unknown error " , "Something went wrong please try again after sometime ");
        }
    }

    public  void startTimer() {


        timer.setVisibility(View.VISIBLE);
        lgnBtn.setVisibility(View.GONE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
        countDownTimer = new CountDownTimer(timee, 1000) {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onTick(long l) {


                timer.setText(MyConst.WAIT_TEXT +  String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(l),
                        TimeUnit.MILLISECONDS.toMinutes(l) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));
                currentCount = l;
            }

            @Override
            public void onFinish() {
                lgnBtn.setVisibility(View.VISIBLE);
                timer.setVisibility(View.GONE);
                loginCount = 0;
            }
        }.start();

    }

    @Override
    protected void onStart() {
        super.onStart();

        currentCount = MyConst.getSPdata(this , MyConst.CURRENT_COUNT);
        long stopTime = MyConst.getSPdata(this , MyConst.CURRENT_MILLISECONDS);
        long currentTime = System.currentTimeMillis();
        timee = currentCount - (currentTime - stopTime);
        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();

        MyConst.storeCounter(this , currentCount);
    }

    @OnClick(R.id.lgnBtn)
    public void login() {


        if(etPassword.getText().toString().equals("")) {
            etPassword.setError(MyConst.USERNAME_ERROR);
            return;
        }
        loginViewModel.getLogin("admin" , etPassword.getText().toString());
    }

}
