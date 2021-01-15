package com.example.gameone;


import android.app.Application;

import com.example.httplibrary.HttpConstant;
import com.example.httplibrary.HttpGlobalConfig;

public class GameApplication extends Application {
    public static GameApplication baseapp;
    private static GameApplication sInstance;
    String game="";

    @Override
    public void onCreate() {
        super.onCreate();
        baseapp=this;
        sInstance=this;


        HttpGlobalConfig.getInsance()
                .setBaseUrl(game)
//                .setInterceptors(interceptors)
                .setTimeout(HttpConstant.TIME_OUT)
//                .setAppKey(Constans.JPUSHREGISTID,JPushInterface.getRegistrationID(this))
                .setShowLog(true)
                .setTimeUnit(HttpConstant.TIME_UNIT)
                .initReady(this);
    }
    public static GameApplication getInstance(){
        return sInstance;
    }
}
