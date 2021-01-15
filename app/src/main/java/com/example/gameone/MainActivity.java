package com.example.gameone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_start;
    private Button bt_continue;
    private Button bt_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
//                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();

        //
    }

    private void initView() {
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_stop = (Button) findViewById(R.id.bt_stop);

        bt_start.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                startActivity(new Intent(MainActivity.this,DrawActivity.class));
                break;
            case R.id.bt_stop:
                finish();
                break;
        }
    }
}
