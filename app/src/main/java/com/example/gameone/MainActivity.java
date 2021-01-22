package com.example.gameone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_start;
    private Button bt_continue;
    private Button bt_stop;
    private TextView mNameMainTv;
    private TextView mCodeMainTv;
    private int time = 5;
    private int num_code = 1;
    private String type;

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
        mNameMainTv = (TextView) findViewById(R.id.tv_name_main);
        mCodeMainTv = (TextView) findViewById(R.id.tv_code_main);

        bt_start.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
//                if (bt_start.getText().equals("继续闯关")||bt_start.getText().equals("开始")) {
                    intent.putExtra("time", time);
                    intent.putExtra("code",num_code);
//                } else {
//                    int times = time + 5;
//                    int num_codes = num_code + 2;
//                    intent.putExtra("time", times);
//                    intent.putExtra("code",num_codes);
//                }
                startActivityForResult(intent, 200);
                break;
            case R.id.bt_stop:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300) {
            mNameMainTv.setVisibility(View.GONE);
            mCodeMainTv.setVisibility(View.VISIBLE);
//            if (data!=null) {
                type = data.getStringExtra("type");
//            }
            if ("闯关失败".equals(type)) {
                mCodeMainTv.setText("闯关失败");
                bt_start.setText("继续闯关");
            } else {
                mCodeMainTv.setText("闯关成功");
                bt_start.setText("继续下一关");
                time = time+5;
                num_code ++;
            }

        }
    }
}
