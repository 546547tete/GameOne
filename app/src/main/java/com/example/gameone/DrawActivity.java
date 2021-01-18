package com.example.gameone;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bean.NumberBean;
import com.example.customview.MyCircle;
import com.example.customview.MyPolygon;
import com.example.customview.MyQuadrilateral;
import com.example.customview.MyTriangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DrawActivity extends AppCompatActivity {

    private RelativeLayout mLayoutRl;
    private NumberBean numberBean;
    private ArrayList<NumberBean> lists;
    private MyTriangle myTriangle;
    private TextView textView;
    private int timerCode = 10;
    private Timer timer;
    private List<Integer> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        initView();
        initData(lists);

    }

    private void initData(List<NumberBean> list) {
        if (list == null && list.size() == 0) {
            return;
        }

        list1 = new ArrayList<>();
        for (NumberBean bean : list) {
            setCode(bean);
            list1.add(bean.getNumber());
        }

    }

    private void setCode(final NumberBean bean) {
        switch (bean.getShape()) {
            case 1: //三角形
                myTriangle = new MyTriangle(this, null);
                mLayoutRl.addView(myTriangle);
                myTriangle.setData(bean);
                codeText(bean);
                break;
            case 2: //圆
                MyCircle myCircle = new MyCircle(this, null);
                mLayoutRl.addView(myCircle);
                myCircle.setData(bean);
                codeText(bean);
                break;
            case 3: //四边形
                MyQuadrilateral myQuadrilateral = new MyQuadrilateral(this, null);
                mLayoutRl.addView(myQuadrilateral);
                myQuadrilateral.setData(numberBean);
                codeText(bean);
                break;
            case 4: //多边形
                MyPolygon myPolygon = new MyPolygon(this, null);
                mLayoutRl.addView(myPolygon);
                myPolygon.setData(bean);
                codeText(bean);
                break;
        }

        /**
         * 监听点击事件
         */
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = textView.getText().toString().trim();
                int parseInt = Integer.parseInt(trim);
                int number_i, number_j;
                //循环判断出来最小的数字
                for (int i = 0; i < list1.size(); i++) {
                    for (int j = 0; j < list1.size() - 1; j++) {
                        number_i = list1.get(i);
                        number_j = list1.get(j);
                        if (number_i > number_j) {
                            int num = number_i;
                            number_i = number_j;
                            number_j = num;

                            /**
                             * 如果点击的数字是最小的，那么就消失
                             */
                            if (num==parseInt){
                                list1.remove(list1.get(i));
                                myTriangle = null;
                                textView = null;
                            }
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        mLayoutRl = (RelativeLayout) findViewById(R.id.rl_layout);
        lists = new ArrayList<>();
        numberBean = new NumberBean(100, 500, 500, 6, "564812", 4);
        lists.add(numberBean);

        /**
         * 倒计时
         */
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerCode--;
                if (timerCode==0){
                    finish();
                }
            }
        },1000);
//        myQuadrilateral.initData(100,500,500);

    }

    /**
     * 动态创建TextView用来显示文字
     * @param numberBean
     */
    private void codeText(NumberBean numberBean) {
        int number = numberBean.getNumber();
        int radius = numberBean.getRadius();
        int place_x = numberBean.getPlace_x();
        int place_y = numberBean.getPlace_y();
        /**
         * 动态创建TextView
         * 添加到界面
         * 动态设置文字
         */
        textView = new TextView(this);
        mLayoutRl.addView(textView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        params.height = radius;
        params.width = radius;
//        params.setMargins(500-25,500-60,0,0);
        //设置位置
        params.setMargins(place_x - radius / 2, place_y - radius / 2, 0, 0);
        textView.setLayoutParams(params);
        textView.setText(number);
        //文字大小
        textView.setTextSize(radius / 3);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
    }
}
