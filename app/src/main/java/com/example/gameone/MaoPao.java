package com.example.gameone;

import com.example.bean.NumberBean;

public class MaoPao {

    public void SmallAndBig( NumberBean.DataBean []arr){
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length - i-1; j++) {
                if (arr[j].getNum() > arr[j+1].getNum()){
                    NumberBean.DataBean temp;
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }
}
