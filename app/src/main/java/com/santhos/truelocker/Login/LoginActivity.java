package com.santhos.truelocker.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.santhos.truelocker.Adapter.ServiceListAdapter;
import com.santhos.truelocker.MainActivity;
import com.santhos.truelocker.Models.Server;
import com.santhos.truelocker.MyConst.MyConst;
import com.santhos.truelocker.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.ennova.zerxconf.ZeRXconf;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import rx.Observer;

public class LoginActivity extends AppCompatActivity implements LoginView {


    @BindView(R.id.etPassword)
    AppCompatEditText etPassword;

    @BindView(R.id.etUser)
    AppCompatEditText etUser;

    @BindView(R.id.timer)
    AppCompatTextView timer;

    @BindView(R.id.lgnBtn)
    MaterialButton lgnBtn;
//
//    @BindView(R.id.etIpAddress)
//    AppCompatEditText etIpAddress;

//    @BindView(R.id.findIpAddress)
//    MaterialButton findDns;


    AlertDialog alertDialog;
    private long timee;
    static ArrayList<Server> mServers;
    private int loginCount = 0;
    private long currentCount;
    LoginViewModel loginViewModel;
    CountDownTimer countDownTimer;
    List<NetworkServiceDiscoveryInfo> networkServiceDiscoveryInfos;
    private ServiceListAdapter serviceListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        alertDialog = MyConst.getLoadingDialog(this);
        loginViewModel = new LoginViewModel(this);
        networkServiceDiscoveryInfos = new ArrayList<>();
//        etIpAddress.setEnabled(false);
        serviceListAdapter = new ServiceListAdapter(networkServiceDiscoveryInfos, this, url -> {
//            etIpAddress.setText(url);
            MyConst.storeIpAddress(LoginActivity.this, url);
            alertDialog.hide();
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
               MyConst.checkPermissions(MyConst.PERMISSION_CHECK ,this);
            }
        }


//        if(getDeviceId() == null  || !getDeviceId().equals(MyConst.IMEI1_DEVICE_ID)) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
//            builder.setNeutralButton("Dismiss", (dialog, which) -> {
//                finish();
//            });
//            alertDialog = builder.create();
//            alertDialog.setTitle("You are not a valid user !!!");
//            alertDialog.setMessage("Please contact administrator");
//            alertDialog.show();
//        }

//        getServer();
        timer.setVisibility(View.GONE);
        if (MyConst.START_TIMER) {
            startTimer();
        }
    }

    private void getServer() {
        ZeRXconf.startDiscovery(getApplicationContext(), "_http._tcp.").subscribe(new Observer<NetworkServiceDiscoveryInfo>() {
            @Override
            public void onCompleted() {
                Toast.makeText(LoginActivity.this, "CO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(NetworkServiceDiscoveryInfo networkServiceDiscoveryInfo) {
                networkServiceDiscoveryInfos.add(networkServiceDiscoveryInfo);
                if(networkServiceDiscoveryInfo.getServiceName().equals(MyConst.SERVICE_NAME)) {
//                    Toast.makeText(LoginActivity.this, networkServiceDiscoveryInfo.getServiceName(), Toast.LENGTH_LONG).show();
//                    etIpAddress.setText("http:/"+networkServiceDiscoveryInfo.getAddress() + ":"+networkServiceDiscoveryInfo.getServicePort());
                    MyConst.storeIpAddress(LoginActivity.this, "http:/"+networkServiceDiscoveryInfo.getAddress() + ":"+networkServiceDiscoveryInfo.getServicePort());
                }
                serviceListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void createServer() {
        Map<String, String> attributes = new HashMap<>();
        ZeRXconf.advertise(this, "ZeRXconf", "_http._tcp.", 1234, attributes)
                .subscribe(new Observer<NetworkServiceDiscoveryInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NetworkServiceDiscoveryInfo networkServiceDiscoveryInfo) {

                    }
                });
    }


    private String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
        }
        return tm.getDeviceId();
    }

/*

    private  void loadServiceList() {
        if(networkServiceDiscoveryInfos.size() == 0) {
            Toast.makeText(this, "No Devices found", Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);
            dialogBuilder.setView(dialogView);

           RecyclerView recyclerView = dialogView.findViewById(R.id.serviceListView);
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
           recyclerView.setAdapter(serviceListAdapter);
            alertDialog = dialogBuilder.create();
            alertDialog.show();

        }

    }
*/



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
        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnFailed(String message) {
        MyConst.showDialog(this , "Failed !" , message);
        loginCount++;
        if(loginCount == MyConst.LOGIN_COUNT) {
            timee = MyConst.TIME * 60000;
            startTimer();
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

        MyConst.START_TIMER = false;
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

        if(etUser.getText().toString().equals("")){
            etUser.setError(MyConst.USERNAME_ERROR);
            return;
        }

        if(etPassword.getText().toString().equals("")) {
            etPassword.setError(MyConst.USERNAME_ERROR);
            return;
        }

//        if(etIpAddress.getText().toString().split(".").length == 4) {
//            etIpAddress.setError(MyConst.VALID_IP);
//            return;
//        }



        loginViewModel.getLogin(etUser.getText().toString() , etPassword.getText().toString());
    }

//    @OnClick(R.id.findIpAddress)
//    public void findmDnsService() {
//        loadServiceList();
//    }

}
