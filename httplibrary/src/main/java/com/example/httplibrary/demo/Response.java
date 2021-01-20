package com.example.httplibrary.demo;

import com.google.gson.JsonElement;
public class Response {
    private JsonElement data;
    private int error_code;
    private String error_msg;

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public int getErrorCode() {
        return error_code;
    }

    public void setErrorCode(int errorCode) {
        this.error_code = errorCode;
    }

    public String getErrorMsg() {
        return error_msg;
    }

    public void setErrorMsg(String errorMsg) {
        this.error_msg = errorMsg;
    }
}
