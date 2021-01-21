package com.example.gameone;

public class MaoPao {

    public void SmallAndBig(int []arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                    if (arr[j] > arr[j+1]){
                        int temp;
                        temp=arr[j];
                        arr[j]=arr[j+1];
                        arr[j+1]=temp;
                    }
            }
        }
    }
}
