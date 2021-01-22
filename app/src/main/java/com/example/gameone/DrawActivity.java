package com.example.gameone;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bean.ApiGet;
import com.example.bean.NumBerDateBean;
import com.example.bean.NumberBean;
import com.example.customview.MyCircle;
import com.example.customview.MyPolygon;
import com.example.customview.MyQuadrilateral;
import com.example.customview.MyTriangle;
import com.example.httplibrary.callback.HttpCallBack;
import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.server.ApiService;
import com.example.httplibrary.utils.LogUtils;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DrawActivity extends AppCompatActivity {
    private static final String TAG = "DrawActivity";
    private RelativeLayout mLayoutRl;
    private NumberBean numberBean;
    private ArrayList<NumberBean.DataBean> lists;

    private int timerCode = 50;
    private Timer timer;
    private List<Integer> list1;
    private TextView tv_time;
    private TextView tv_guan;

    //参数
    private int numCount = 1;
    private MaoPao maoPao;
    private int[] arr;
    private NumberBean.DataBean[] arr1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        timerCode = getIntent().getIntExtra("time", 0);
        numCount = getIntent().getIntExtra("code", 0);
        initView();
        initData();

    }

    private void initView() {
        maoPao = new MaoPao();
        mLayoutRl = (RelativeLayout) findViewById(R.id.rl_layout);
        tv_time = findViewById(R.id.tv_time);
        tv_guan = findViewById(R.id.tv_guan);
        lists = new ArrayList<>();
        list1 = new ArrayList<>();

        tv_guan.setText("第"+numCount+"关");
        /**
         * 倒计时
         */
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_time.setText("倒计时：" + timerCode);
                        if (timerCode == 0) {
                            intent = getIntent();
                            if (lists.size() > 0) {
                                intent.putExtra("type", "闯关失败");
                            }
                            setResult(300, intent);
                            finish();
                        }
                        timerCode--;
                    }
                });

            }
        }, 0, 1000);
//        myQuadrilateral.initData(100,500,500);

    }

    private void initData() {


        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        int radius;
        int num = numCount*2+1;
        if (numCount<2){
            radius = height / (num * 6);
        } else if (numCount<4){
            radius = height / (num * 4);
        } else if (numCount<6){
            radius = height / (num * 3);
        } else {
            radius = height / (num * 2);
        }
//        int radius = 50;


        setNetWorkData(num,width, height-100, radius);

//        lists.add(new NumberBean.DataBean(60, "0000FF", 8, 800, 1500, 50));
//        lists.add(new NumberBean.DataBean(59, "FF0000", 6, 100, 100, 50));
//        lists.add(new NumberBean.DataBean(50, "0000FF", 4, 500, 500, 50));
//        lists.add(new NumberBean.DataBean(70, "0000FF", 5, 500, 800, 50));
//        lists.add(new NumberBean.DataBean(77, "FFFF00", 3, 500, 900, 50));
//        lists.add(new NumberBean.DataBean(89, "0000FF", 7, 500, 1000, 50));
//        lists.add(new NumberBean.DataBean(35, "00FF00", 4, 300, 100, 50));
//        lists.add(new NumberBean.DataBean(99, "00FF00", 5, 600, 1000, 50));

//        arr1 = new NumberBean.DataBean[lists.size()];
//        for (NumberBean.DataBean list : lists) {
//            setCode(list);
//            list1.add(list.getNum());
//
//            Log.e(TAG, "initData: " + list.getNum());
//        }
//        for (int i = 0; i < lists.size(); i++) {
//            arr1[i] = lists.get(i);
//        }
    }

    private void setNetWorkData(int num,int width, int height, int radius) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiGet.APIGET)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiGet apiGet = retrofit.create(ApiGet.class);
        apiGet.getApi("numCount/" + num + "/radius/" + radius + "/width/" + width + "/height/" + height)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NumberBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NumberBean numberBean) {
                        if (numberBean != null && numberBean.getError_code() == 0) {
                            List<NumberBean.DataBean> data = numberBean.getData();
                            lists.addAll(data);
                            for (NumberBean.DataBean bean : lists) {
                                list1.add(bean.getNum());
                                setCode(bean);
                            }
                            arr1 = new NumberBean.DataBean[lists.size()];
                            for (int i = 0; i < lists.size(); i++) {
                                arr1[i] = lists.get(i);
                            }
                        }

                        Log.e(TAG, "onNext: " + numberBean.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setCode(NumberBean.DataBean bean) {
        switch (bean.getShape()) {
            case 3: //三角形
                final MyTriangle myTriangle = new MyTriangle(this, null);
                mLayoutRl.addView(myTriangle);
                myTriangle.setData(bean);
                final TextView textView1 = new TextView(this);
                codeText(textView1, bean);

                /**
                 * 监听点击事件
                 */
                textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = textView1.getText().toString().trim();
                        int parseInt = Integer.parseInt(trim);
                        //循环判断出来最小的数字 放到最前面
                        maoPao.SmallAndBig(arr1);
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (arr1[0].getNum() == parseInt) {
                            NumberBean.DataBean dataBean = arr1[0];
                            arr1[0] = new NumberBean.DataBean(999, "", 3, 0, 0, 0);
                            lists.remove(dataBean);
                            mLayoutRl.removeView(textView1);
                            mLayoutRl.removeView(myTriangle);
                        }
                        finishMain(lists);
                    }
                });
                break;
            case 4: //四方

                final MyQuadrilateral myQuadrilateral = new MyQuadrilateral(this, null);
                mLayoutRl.addView(myQuadrilateral);
                myQuadrilateral.setData(bean);
                final TextView textView3 = new TextView(this);
                codeText(textView3, bean);

                /**
                 * 监听点击事件
                 */
                textView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = textView3.getText().toString().trim();
                        int parseInt = Integer.parseInt(trim);
                        //循环判断出来最小的数字 放到最前面
                        maoPao.SmallAndBig(arr1);
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (arr1[0].getNum() == parseInt) {
                            NumberBean.DataBean dataBean = arr1[0];
                            arr1[0] = new NumberBean.DataBean(999, "", 3, 0, 0, 0);
                            lists.remove(dataBean);
                            mLayoutRl.removeView(myQuadrilateral);
                            mLayoutRl.removeView(textView3);
                        }
                        finishMain(lists);
                    }
                });
                break;
            case 5: //圆
                final MyCircle myCircle = new MyCircle(this, null);
                mLayoutRl.addView(myCircle);
                myCircle.setData(bean);
                final TextView textView5 = new TextView(this);
                codeText(textView5, bean);
                /**
                 * 监听点击事件
                 */
                textView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = textView5.getText().toString().trim();
                        int parseInt = Integer.parseInt(trim);
                        //循环判断出来最小的数字 放到最前面
                        maoPao.SmallAndBig(arr1);
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (arr1[0].getNum() == parseInt) {
                            NumberBean.DataBean dataBean = arr1[0];
                            arr1[0] = new NumberBean.DataBean(999, "", 3, 0, 0, 0);
                            lists.remove(dataBean);
                            mLayoutRl.removeView(myCircle);
                            mLayoutRl.removeView(textView5);
                        }
                        finishMain(lists);
                    }
                });
                break;
            case 6: //6边形
                final MyPolygon myPolygon = new MyPolygon(this, null);
                mLayoutRl.addView(myPolygon);
                myPolygon.setData(bean);
                final TextView textView6 = new TextView(this);
                codeText(textView6, bean);
                /**
                 * 监听点击事件
                 */
                textView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = textView6.getText().toString().trim();
                        int parseInt = Integer.parseInt(trim);
                        //循环判断出来最小的数字 放到最前面
                        maoPao.SmallAndBig(arr1);
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (arr1[0].getNum() == parseInt) {
                            NumberBean.DataBean dataBean = arr1[0];
                            arr1[0] = new NumberBean.DataBean(999, "", 3, 0, 0, 0);
                            lists.remove(dataBean);
                            mLayoutRl.removeView(myPolygon);
                            mLayoutRl.removeView(textView6);
                        }
                        finishMain(lists);
                    }
                });
                break;
            case 7:
                final MyPolygon myPolygon7 = new MyPolygon(this, null);
                mLayoutRl.addView(myPolygon7);
                myPolygon7.setData(bean);
                final TextView textView7 = new TextView(this);
                codeText(textView7, bean);
                /**
                 * 监听点击事件
                 */
                textView7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = textView7.getText().toString().trim();
                        int parseInt = Integer.parseInt(trim);
                        //循环判断出来最小的数字 放到最前面
                        maoPao.SmallAndBig(arr1);
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (arr1[0].getNum() == parseInt) {
                            NumberBean.DataBean dataBean = arr1[0];
                            arr1[0] = new NumberBean.DataBean(999, "", 3, 0, 0, 0);
                            lists.remove(dataBean);
                            mLayoutRl.removeView(myPolygon7);
                            mLayoutRl.removeView(textView7);
                        }
                        finishMain(lists);
                    }
                });
                break;
            case 8:

                final MyCircle myCircle8 = new MyCircle(this, null);
                mLayoutRl.addView(myCircle8);
                myCircle8.setData(bean);
                final TextView textView82 = new TextView(this);
                codeText(textView82, bean);

                /**
                 * 监听点击事件
                 */
                textView82.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = textView82.getText().toString().trim();
                        int parseInt = Integer.parseInt(trim);
                        //循环判断出来最小的数字 放到最前面
                        maoPao.SmallAndBig(arr1);
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (arr1[0].getNum() == parseInt) {
                            NumberBean.DataBean dataBean = arr1[0];
                            arr1[0] = new NumberBean.DataBean(999, "", 3, 0, 0, 0);
                            lists.remove(dataBean);
                            mLayoutRl.removeView(myCircle8);
                            mLayoutRl.removeView(textView82);
                        }
                        finishMain(lists);
                    }
                });
                break;
        }

    }

    private void finishMain(ArrayList<NumberBean.DataBean> lists) {
        if (lists.size()<=0){
            Intent intent = getIntent();
            intent.putExtra("type","闯关成功");
            setResult(300, intent);
            finish();
        }
    }


    /**
     * 动态创建TextView用来显示文字
     *
     * @param numberBean
     */
    private void codeText(TextView textView, NumberBean.DataBean numberBean) {
        int number = numberBean.getNum();
        int radius = numberBean.getRadius();
        int place_x = numberBean.getX();
        int place_y = numberBean.getY()+100;
        /**
         * 动态创建TextView
         * 添加到界面3
         * 动态设置文字
         */
        Log.e(TAG, "codeText: ");
        mLayoutRl.addView(textView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        params.height = radius;
        params.width = radius;
//        params.setMargins(500-25,500-60,0,0);
        //设置位置
        params.setMargins(place_x - radius / 2, place_y - radius / 2, 0, 0);
        textView.setLayoutParams(params);
        textView.setText(number + "");
        //文字大小
        textView.setTextSize(radius / 4);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(Color.WHITE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerCode = 0;
        timer = null;
    }

}
