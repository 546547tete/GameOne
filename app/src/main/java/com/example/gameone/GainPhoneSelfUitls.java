package com.example.gameone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.webkit.WebSettings;

import com.example.httplibrary.utils.LogUtils;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 */

public class GainPhoneSelfUitls {

    private static String appInfo;

    /**
     * 获取手机信息
     *
     * @return
     */
    public static String getAppInfo() {
        if (appInfo == null) {
            Gson gson = new Gson();
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("appVersion", getVersionName());
            stringMap.put("phoneName", Build.MANUFACTURER + "-" + Build.BRAND + "-" + Build.PRODUCT);
            stringMap.put("OSVersion", "android" + Build.VERSION.RELEASE);
//            stringMap.put("deviceToken", getSpNormal().getDeviceToken());
            appInfo = gson.toJson(stringMap);
        }
        return appInfo;
    }

    /**
     * 获取手机信息
     *
     * @param code 接口返回的状态码
     * @param msg  接口返回的消息
     * @return
     */
    public static String getAppInfo(int code, String msg) {
        if (appInfo == null) {
            Gson gson = new Gson();
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("appVersion", getVersionName());
            stringMap.put("phoneName", Build.MANUFACTURER + "-" + Build.BRAND + "-" + Build.PRODUCT);
            stringMap.put("OSVersion", "android" + Build.VERSION.RELEASE);
//            stringMap.put("deviceToken", getSpNormal().getDeviceToken());
            stringMap.put("code", String.valueOf(code));
            stringMap.put("message", msg);
            appInfo = gson.toJson(stringMap);
        }
        return appInfo;
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static int getVersionCode() {

        //获取包管理器
        PackageManager pm = GameApplication.getInstance().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(GameApplication.getInstance().getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本名称
     *
     * @return 版本名称
     */
    public static String getVersionName() {
        //获取包管理器
        Context context = GameApplication.getInstance();
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(GameApplication.getInstance().getPackageName(), 0);
            //返回版本名称
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取apk的渠道名称，针对对渠道打包的apk
     *
     * @param key
     * @return
     */
    public static String getPackageChannelName(String key) {
        try {
            Context context = GameApplication.getInstance();
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "android";
    }

    /**
     * 获得手机的UserAgent
     *
     * @return
     */
    public static String getUserAgent() {
        String userAgent = "";
        //在Api17之后可以通过WebSettings.getDefaultUserAgent(context)获取，
        // 但是经过测试极个别手机会出现找不到类的情况，因此try-catch一下，
        // 那么第二种方式是System.getProperty("http.agent"),
        // 这两种方式有什么不同呢？从结果上来看是第一种得到的信息更全一点；

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                Context context = GameApplication.getInstance();
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        //在一些国产手机上面这个User-Agent里面会包含中文，就会报错
        //对返回结果进行过滤，如果不符合条件，会进行转码
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }


    /**
     * 获取手机序列号
     *
     * @return 手机序列号
     */
    @SuppressLint({"NewApi", "MissingPermission"})
    public static String getSerialNumber() {
        String serial = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//9.0+
                serial = Build.getSerial();
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {//8.0+
                serial = Build.SERIAL;
            } else {//8.0-
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("e", "读取设备序列号异常：" + e.toString());
        }
        return serial;
    }
    public static String getAndroidId(){
        String ANDROID_ID ="";
        ANDROID_ID=  Settings.System.getString(GameApplication.getInstance().getContentResolver(), Settings.System.ANDROID_ID);
        if (StrUtils.isEmpty(ANDROID_ID)){
            ANDROID_ID="yongsheng666";
        }
        return ANDROID_ID;
    }

}
