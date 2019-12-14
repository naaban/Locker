package com.santhos.truelocker.Fragment.Locker;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.santhos.truelocker.Models.Status;
import com.santhos.truelocker.MyConst.MyConst;
import com.santhos.truelocker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LockerFragment extends Fragment implements ILockerView{


    public LockerFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.bt_s)
     TextView bt_s;

    @BindView(R.id.lock_1)
     TextView lock1;

    @BindView(R.id.lock_2)
      TextView lock2;

    @BindView(R.id.lock_1_close_s)
     TextView lock_1_close_s;

    @BindView(R.id.lock_1_open_s)
     TextView lock_1_open_s;

    @BindView(R.id.lock_2_close_s)
     TextView lock_2_close_s;

    @BindView(R.id.lock_2_open_s)
     TextView lock_2_open_s;

    @BindView(R.id.motor1)
     TextView motor1;

    @BindView(R.id.motor2)
     TextView motor2;

    @BindView(R.id.v_sen_1)
     TextView v_sen_1;

    @BindView(R.id.t_sen_1)
     TextView t_sen_1;

    @BindView(R.id.data1)
     TextView data1;
    @BindView(R.id.data2)
     TextView data2;
    @BindView(R.id.data3)
     TextView data3;
    @BindView(R.id.data4)
     TextView data4;

    @BindView(R.id.retry_count)
     TextView retry_cnt;
    @BindView(R.id.err_code)
     TextView err_code;

    @BindView(R.id.status)
     TextView status;

    @BindView(R.id.locker1)
     ImageView locker1;

    private  Status lockerStatus;
    private LockerViewModel lockerViewModel;
    private AlertDialog alertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_locker, container, false);
        ButterKnife.bind(this , view);
        lockerViewModel = new LockerViewModel(this , getContext());
        alertDialog = MyConst.getLoadingDialog(getActivity());
        getLockerStatus();

        refreshState(10000);
        return view;
    }


    private void assignValues() {
        bt_s.setText(lockerStatus.getBt_s());
        lock1.setText(lockerStatus.getLock1());
        lock2.setText(lockerStatus.getLock2());
        lock_1_close_s.setText(lockerStatus.getLock1_close_s());
        lock_2_close_s.setText(lockerStatus.getLock2_close_s());
        lock_1_open_s.setText(lockerStatus.getLock1_open_s());
        lock_2_open_s.setText(lockerStatus.getLock2_open_s());
        motor1.setText(lockerStatus.getMotor1());
        motor2.setText(lockerStatus.getMotor2());
        v_sen_1.setText(lockerStatus.getV_sen1());
        t_sen_1.setText(lockerStatus.getT_sen1());
        data1.setText(lockerStatus.getData1());
        data2.setText(lockerStatus.getData2());
        data3.setText(lockerStatus.getData3());
        data4.setText(lockerStatus.getData4());
        err_code.setText(lockerStatus.getError_code());
        retry_cnt.setText(lockerStatus.getError_code());

        if(lockerStatus.getLock1().equals("open")) {
            locker1.setImageDrawable(getResources().getDrawable(R.drawable.ic_unlocked));
            status.setText("Locker Status: UnLocked");
            status.setTextColor(getResources().getColor(R.color.green));
        }
        else {
            locker1.setImageDrawable(getResources().getDrawable(R.drawable.ic_locked));
            status.setText("Locker Status: Locked");
            status.setTextColor(getResources().getColor(R.color.red));

        }

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
    public void onNetworkError(Throwable e) {
        Toast.makeText(getContext(), "Failed to get status", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(getContext(), message,  Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLockerStatusReceived(Status status) {
        lockerStatus = status;
        assignValues();
    }

    @Override
    public void OnStatusSuccess() {

    }


    private  void refreshState (int delayInMs) {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
               getLockerStatus();
                handler.postDelayed(this, delayInMs);
            }
        }, delayInMs);
    }


    private  void getLockerStatus () {
        if(!MyConst.getIpAddressExists(getContext())) {
            Toast.makeText(getContext(), "No Devices Found", Toast.LENGTH_SHORT).show();
        }else {
            lockerViewModel.getLockerStatus();
        }
    }


}
