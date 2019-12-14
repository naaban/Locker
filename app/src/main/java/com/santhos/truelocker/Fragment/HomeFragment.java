package com.santhos.truelocker.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.santhos.truelocker.MainActivity;
import com.santhos.truelocker.MainActivityView;
import com.santhos.truelocker.MainActivityViewModel;
import com.santhos.truelocker.MasterHome.MasterHomeActivity;
import com.santhos.truelocker.MasterLogin.MasterLogin;
import com.santhos.truelocker.MyConst.MyConst;
import com.santhos.truelocker.R;
import com.santhos.truelocker.customComponent.RollingTextView;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MainActivityView {


    public HomeFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.status)
    public AppCompatTextView status;

    @BindView(R.id.ver_otp)
    public ImageView verOtp;

    @BindView(R.id.etOtp)
    RollingTextView etOtp;

    @BindView(R.id.masterLogin)
    public LinearLayout masterLogin;

    @BindView(R.id.generate_otp)
    public MaterialButton btnGenerateOtp;


    private boolean IS_DEVICE_CONNECTED = false;

    private AlertDialog alertDialog ;

    private MainActivityViewModel mainActivityViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        alertDialog = MyConst.getLoadingDialog(getActivity());
        status.setText(MyConst.CONNECTED);
        mainActivityViewModel =  new MainActivityViewModel(this , getContext());

        verOtp.setVisibility(View.GONE);
        return view;
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
    public void OnFailed(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        status.setText(MyConst.NOT_CONNECTED);
    }

    @Override
    public void OnNetworkError(Throwable err) {
        if(err instanceof IOException)  {
            Toast.makeText(getContext(), err.toString(), Toast.LENGTH_SHORT).show();
            status.setText(MyConst.NOT_CONNECTED);
        }
        else {

        }
    }


    @Override
    public void OnStatusSuccess() {
        status.setText(MyConst.CONNECTED);
        IS_DEVICE_CONNECTED = true;
//        status.setTextColor(getResources().getColor(R.color.green));
    }

    @Override
    public void OnKeyReceived(Integer key) {
        etOtp.setNumber(String.valueOf(key));
        verOtp.setVisibility(View.VISIBLE);

    }

    @Override
    public void OnOtpSuccess() {
        Intent intent = new Intent(getContext() , MasterHomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.generate_otp)
    public void generateOtp () {
        if (MyConst.getIpAddressExists(getView().getContext())) {
            mainActivityViewModel.getKey();
        } else {
            Toast.makeText(getContext(), "No Devices Found", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ver_otp)
    public void masterLogin(){
        Intent intent = new Intent(getContext() , MasterHomeActivity.class);
        startActivity(intent);
    }

    public int clickCount = 0;
    @OnClick(R.id.masterLogin)
    public void masterLgin(){
        clickCount ++;
        if(clickCount == 3) {
            startActivity(new Intent(getContext() , MasterLogin.class));
            clickCount = 0;
        }
    }


    private  void refreshState (int delayInMs) {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable(){
            public void run(){
                mainActivityViewModel.getDeviceConnected();
                handler.postDelayed(this, delayInMs);
            }
        }, delayInMs);
    }



}
