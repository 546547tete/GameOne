package com.example.bean;

import com.example.gameone.GainPhoneSelfUitls;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiGet {

    String APIGET = "http://192.168.31.13:8000/";

    @GET
    Observable<NumberBean> getApi(@Url String ur);
}
