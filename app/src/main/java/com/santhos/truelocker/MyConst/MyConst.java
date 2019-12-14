package com.santhos.truelocker.MyConst;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.santhos.truelocker.R;

import it.ennova.zerxconf.ZeRXconf;
import it.ennova.zerxconf.model.NetworkServiceDiscoveryInfo;
import rx.Subscription;
import rx.functions.Action1;

import static android.content.Context.MODE_PRIVATE;

public class MyConst {


    public static String BASE_URL_PROD = "http://locker_32.local/";

    public static String BASE_URL_TEST = "http://103.207.4.72:8080/lockapi/webapi/resource/";
    public static  String CREDENTAILS = "Basic "+ Base64.encodeToString(("user:pass").getBytes() , Base64.NO_WRAP);

    public static int PERMISSION_CHECK = 1000;
    public static String SERVICE_NAME = "Electro_Locker";
    public static String IMEI1_DEVICE_ID = "860508049974976";
    public static String IMEI2_DEVICE_ID = "860508049974986";

    public static int TIME = 2;
    public static int LOGIN_COUNT = 2;
    public static String CURRENT_COUNT = "current_count";
    public static String CURRENT_MILLISECONDS = "current_ms";
    public static  String VALID_IP = "Enter a valid ip";
    public static String SP_NAME = "my_locker";
    public static  String ACCESS_CODE = "access_code";
    public  static String OPENED = "LOCK OPENED";
    public  static String CLOSE_LOCK = "CLOSE LOCK";
    public  static String OPEN_LOCK = "OPEN LOCK";
    public static String WAIT_TEXT = "PLEASE WAIT UNTIL: ";

    public static String NOT_CONNECTED = "NOT CONNECTED";
    public static String CONNECTED = "CONNECTED";

    public static boolean START_TIMER = false;
    public static String USERNAME_ERROR = "Enter username";
    public static String PASSWORD_ERROR = "Enter password";
    public static Integer DELAY = 15000;
    private static Action1<? super NetworkServiceDiscoveryInfo> onNext;
    private static Action1<Throwable> onError;

    public static void showDialog(Context context, String title, String message){
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle(title).setMessage(message).show();
    }

    public static AlertDialog getLoadingDialog(Activity context){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.spinner, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return alertDialog;
    }


    public static String IP_ADDRESS = "ip_address";

    public static String IS_IP_ADDRESS_EXISTS = "ip_addrs_exists";

    public static void storeIpAddress(Context context , String ip_address) {
        SharedPreferences sPrefs = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString(IP_ADDRESS,  ip_address+"/");
        editor.apply();
    }


    public static void storeIsIpAddressExists(Context context , boolean exists) {
        SharedPreferences sPrefs = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putBoolean(IS_IP_ADDRESS_EXISTS,  exists);
        editor.apply();
    }

    public static boolean getIpAddressExists(Context context) {
        SharedPreferences sPrefs = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        return sPrefs.getBoolean(IS_IP_ADDRESS_EXISTS , false);
    }



    public static void storeCounter(Context context , long currentCount) {
        SharedPreferences sPrefs = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putLong(CURRENT_COUNT, currentCount);
        editor.putLong(CURRENT_MILLISECONDS, System.currentTimeMillis());
        editor.apply();
    }
    public static  long getSPdata (Context context , String key) {
        SharedPreferences sPrefs = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        return sPrefs.getLong(key, 0);
    }

    public static String getIpAddress (Context context , String key) {
        SharedPreferences sPrefs = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        return sPrefs.getString(key , null);
    }


    public static void checkPermissions (int reqId , Activity activity) {

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE},reqId);
    }

    public static void getServer(Context context) {
        onNext = (Action1<NetworkServiceDiscoveryInfo>) networkServiceDiscoveryInfo -> {


        };
        onError = throwable ->{

        };
//        subscription.unsubscribe();
    }

}
