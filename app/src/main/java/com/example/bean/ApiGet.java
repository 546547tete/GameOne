package com.example.bean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ApiGet {

    String APIGET = "http://192.168.31.13:8000/";

    @Headers({
        "content-length:443",
            "content-type:application/json",
            "date:Tue, 19 Jan 2021 01:23:00 GMT",
            "server:hypercorn-h11"
    })
    @GET()
    Observable<NumberBean> getApi(@Url String url);
}
