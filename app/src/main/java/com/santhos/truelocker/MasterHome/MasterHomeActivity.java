package com.santhos.truelocker.MasterHome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.santhos.truelocker.MyConst.MyConst;
import com.santhos.truelocker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MasterHomeActivity extends AppCompatActivity implements  MasterHomeView {
    public AlertDialog alertDialog;

    @BindView(R.id.status)
    public AppCompatTextView status;

    @BindView(R.id.openLock)
    public MaterialButton openBtn;

    @BindView(R.id.lock_img)
    ImageView lockImage;

    Handler handler;
    Runnable runnable;
    MasterHomeViewModel masterHomeViewModel;

    Integer accessCode;

    private boolean notConnected = false;
    private boolean opened = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_home);
        ButterKnife.bind(this);
        status.setText(MyConst.CONNECTED);
        alertDialog = MyConst.getLoadingDialog(this);
        masterHomeViewModel =  new MasterHomeViewModel(this);
//        runTask(10000);

        accessCode = getIntent().getIntExtra(MyConst.ACCESS_CODE , 0);

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
        notConnected = true;
//        handler.removeCallbacks(runnable);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        status.setText(MyConst.NOT_CONNECTED);
    }

    @Override
    public void OnNetworkError(Throwable err) {
        notConnected = true;
//        handler.removeCallbacks(runnable);
//        status.setText(MyConst.NOT_CONNECTED);

}


    public void runTask(int delay) {
         handler = new Handler();
         runnable = new Runnable() {
            @Override
            public void run() {
                masterHomeViewModel.getStatus();
                if(!notConnected)
                    handler.postDelayed(this , delay);
            }
        };
        handler.postDelayed(runnable , delay);


    }

    @Override
    public void OnStatusSuccess() {
        status.setText(MyConst.CONNECTED);
    }

    @Override
    public void OnOpenSuccess() {
        opened = true;
        lockImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_unlocked));
        Toast.makeText(this, "LOCK OPENED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnCloseSuccess() {
        opened = false;
        lockImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_locked));
        Toast.makeText(this, "LOCK CLOSED", Toast.LENGTH_SHORT).show();

    }


    @OnClick(R.id.lock_img)
    public void openCloseLock() {
        if(opened) {
            masterHomeViewModel.getClose(accessCode);
        }
        else {
            masterHomeViewModel.getOpen(accessCode);

        }
    }
}
