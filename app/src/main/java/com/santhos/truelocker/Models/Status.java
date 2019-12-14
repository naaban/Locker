package com.santhos.truelocker.Models;

import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("response")
    private String response;

    @SerializedName("BT_s")
    private  String bt_s;

    @SerializedName("Lock1")
    private  String lock1;
    @SerializedName("Lock2")
    private  String lock2;

    @SerializedName("Lock1_close_s")
    private String lock1_close_s;

    @SerializedName("Lock1_open_s")
    private String lock1_open_s;

    @SerializedName("Lock2_close_s")
    private String lock2_close_s;

    @SerializedName("Lock2_open_s")
    private String lock2_open_s;

    @SerializedName("motor1")
    private  String motor1;

    @SerializedName("motor2")
    private  String motor2;

    @SerializedName("timeout")
    private  String timeout;

    @SerializedName("V_sen1")
    private  String v_sen1;

    @SerializedName("t_sen1")
    private  String t_sen1;

    @SerializedName("data1")
    private  String data1;

    @SerializedName("data2")
    private  String data2;

    @SerializedName("data3")
    private  String data3;
    @SerializedName("data4")
    private  String data4;
    @SerializedName("data5")
    private  String data5;
    @SerializedName("data6")
    private  String data6;

    @SerializedName("error_code")
    private  String error_code;

    @SerializedName("retry_cnt")
    private  String retry_cnt;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getBt_s() {
        return bt_s;
    }

    public void setBt_s(String bt_s) {
        this.bt_s = bt_s;
    }

    public String getLock1() {
        return lock1;
    }

    public void setLock1(String lock1) {
        this.lock1 = lock1;
    }

    public String getLock2() {
        return lock2;
    }

    public void setLock2(String lock2) {
        this.lock2 = lock2;
    }

    public String getLock1_close_s() {
        return lock1_close_s;
    }

    public void setLock1_close_s(String lock1_close_s) {
        this.lock1_close_s = lock1_close_s;
    }

    public String getLock1_open_s() {
        return lock1_open_s;
    }

    public void setLock1_open_s(String lock1_open_s) {
        this.lock1_open_s = lock1_open_s;
    }

    public String getLock2_close_s() {
        return lock2_close_s;
    }

    public void setLock2_close_s(String lock2_close_s) {
        this.lock2_close_s = lock2_close_s;
    }

    public String getLock2_open_s() {
        return lock2_open_s;
    }

    public void setLock2_open_s(String lock2_open_s) {
        this.lock2_open_s = lock2_open_s;
    }

    public String getMotor1() {
        return motor1;
    }

    public void setMotor1(String motor1) {
        this.motor1 = motor1;
    }

    public String getMotor2() {
        return motor2;
    }

    public void setMotor2(String motor2) {
        this.motor2 = motor2;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getV_sen1() {
        return v_sen1;
    }

    public void setV_sen1(String v_sen1) {
        this.v_sen1 = v_sen1;
    }

    public String getT_sen1() {
        return t_sen1;
    }

    public void setT_sen1(String t_sen1) {
        this.t_sen1 = t_sen1;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getData5() {
        return data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

    public String getData6() {
        return data6;
    }

    public void setData6(String data6) {
        this.data6 = data6;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getRetry_cnt() {
        return retry_cnt;
    }

    public void setRetry_cnt(String retry_cnt) {
        this.retry_cnt = retry_cnt;
    }
}
