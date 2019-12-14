package com.santhos.truelocker;


import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.santhos.truelocker.MyConst.MyConst;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.ennova.zerxconf.ZeRXconf;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import rx.Observer;


public class MainActivity extends AppCompatActivity  implements  MainActivityView {



    public AlertDialog  alertDialog;
    private Integer accessKey = 0;




    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    public NavigationView navigationView;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private AppBarConfiguration mAppBarConfiguration;


    public ActionBarDrawerToggle actionBarDrawerToggle;

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configureToolbar();
        alertDialog = MyConst.getLoadingDialog(this);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home , R.id.nav_status)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        getServer();
     /*   navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id)
                {
                    case R.id.status:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }


                return true;
            }
        });*/
    }



    public void configureToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

    }

    @Override
    public void OnNetworkError(Throwable err) {

    }

    @Override
    public void OnStatusSuccess() {

    }

    @Override
    public void OnKeyReceived(Integer key) {

    }

    @Override
    public void OnOtpSuccess() {

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


   private void   getServer() {
        ZeRXconf.startDiscovery(this, "_http._tcp.").subscribe(new Observer<NetworkServiceDiscoveryInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this , "Failed to get contact with device" , Toast.LENGTH_LONG).show();
                MyConst.storeIsIpAddressExists(MainActivity.this, false);
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(NetworkServiceDiscoveryInfo networkServiceDiscoveryInfo) {
                if(networkServiceDiscoveryInfo.getServiceName().equals(MyConst.SERVICE_NAME)) {
                    MyConst.storeIpAddress(MainActivity.this, "http:/"+networkServiceDiscoveryInfo.getAddress() + ":"+networkServiceDiscoveryInfo.getServicePort());
                    MyConst.storeIsIpAddressExists(MainActivity.this, true);
                }
            }
        });
    }

}
