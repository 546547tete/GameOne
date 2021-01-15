package com.example.gameone;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DrawActivity extends AppCompatActivity {

    private RelativeLayout mLayoutRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        initView();


    }

    private void initView() {
        mLayoutRl = (RelativeLayout) findViewById(R.id.rl_layout);
        MyTriangle myTriangle = new MyTriangle(this, null);
        mLayoutRl.addView(myTriangle);
//        myTriangle.initData(100,500,500);

        MyQuadrilateral myQuadrilateral = new MyQuadrilateral(this, null);
        TextView textView = new TextView(this);
        mLayoutRl.addView(myQuadrilateral);
        mLayoutRl.addView(textView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        params.height = 100;
        params.width = 100;
//        params.setMargins(500-25,500-60,0,0);
        params.setMargins(500-50,500-60,0,0);
        textView.setLayoutParams(params);
        textView.setText("6");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLUE);
        myQuadrilateral.initData(100,500,500);


    }
}
