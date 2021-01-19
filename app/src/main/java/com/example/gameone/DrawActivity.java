package com.example.gameone;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bean.ApiGet;
import com.example.bean.NumberBean;
import com.example.customview.MyCircle;
import com.example.customview.MyPolygon;
import com.example.customview.MyQuadrilateral;
import com.example.customview.MyTriangle;
import com.example.httplibrary.callback.HttpCallBack;
import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.server.ApiService;
import com.google.gson.JsonElement;

import java.util.ArrayList;
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
    private MyTriangle myTriangle;
    private TextView textView;
    private int timerCode = 50;
    private Timer timer;
    private List<Integer> list1;
    private MyPolygon myPolygon;
    private MyPolygon myPolygon1;
    private TextView tv_time;

    //参数
    private int numCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        initView();
        initData();

    }

    private void initView() {
        mLayoutRl = (RelativeLayout) findViewById(R.id.rl_layout);
        tv_time = findViewById(R.id.tv_time);
        lists = new ArrayList<>();
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
        int radius = height / (numCount * 2);

//        new HttpClient.Builder()
//                .get()
//                .setApiUrl("numCount/"+numCount+"/radius/"+radius+"/maxX/"+width+"/maxY/"+height)
//                .build()
//                .request(new HttpCallBack<List<NumberBean>>() {
//                    @Override
//                    public void onError(String message, int code) {
//
//                    }
//
//                    @Override
//                    public void cancle() {
//
//                    }
//
//                    @Override
//                    public void onSuccess(List<NumberBean> list) {
//
//                    }
//
//                    @Override
//                    public List<NumberBean> convert(JsonElement result) {
//                        return null;
//                    }
//                });

        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiGet.APIGET)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiGet apiGet = retrofit.create(ApiGet.class);
        apiGet.getApi("numCount/" + numCount + "/radius/" + radius + "/maxX/" + width + "/maxY/" + height,"zh-CN")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NumberBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NumberBean numberBean) {
                        if (numberBean!=null&&numberBean.getError_code()==0) {
                            List<NumberBean.DataBean> data = numberBean.getData();
                            lists.addAll(data);
                        }

                        Log.e(TAG, "onNext: " + numberBean.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        list1 = new ArrayList<>();
        for (NumberBean.DataBean bean : lists) {
            setCode(bean);
            list1.add(bean.getNum());
        }
        Log.e(TAG, "initData: 111"+list1.size() );
    }

    private void setCode(final NumberBean.DataBean bean) {
        int code = bean.getNum();
        switch (bean.getShape()) {
            case 3: //三角形
                myTriangle = new MyTriangle(this, null);
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
                        int number_i, number_j = 0,post = 0;
                        //循环判断出来最小的数字
                        for (int i = 0; i < list1.size(); i++) {
                            for (int j = 0; j < list1.size() - i; j++) {
                                number_i = list1.get(j);
                                number_j = list1.get(j + i);
                                if (number_i < number_j) {
                                    int num = number_i;
                                    number_i = number_j;
                                    number_j = num;
                                    post = j;
                                }
                            }
                        }
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (number_j == parseInt) {
//                            list1.remove(list1.get(j + 1));
                            mLayoutRl.removeView(myTriangle);
                            textView1.setVisibility(View.GONE);
//                            lists.remove(i);
//                                            initData(lists);
                            Log.e("tag", "onClick: 1==" + number_j + "parseInt" + parseInt);
                        }
                    }
                });
                break;
            case 4: //圆
                final MyCircle myCircle = new MyCircle(this, null);
                mLayoutRl.addView(myCircle);
                myCircle.setData(bean);
                final TextView textView2 = new TextView(this);
                codeText(textView2, bean);

                /**
                 * 监听点击事件
                 */
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = textView2.getText().toString().trim();
                        int parseInt = Integer.parseInt(trim);
                        int number_i, number_j = 0, post = 0;
                        //循环判断出来最小的数字
                        for (int i = 0; i < list1.size(); i++) {
                            for (int j = 0; j < list1.size() - i; j++) {
                                number_i = list1.get(j);
                                number_j = list1.get(j + i);
                                if (number_i < number_j) {
                                    int num = number_i;
                                    number_i = number_j;
                                    number_j = num;
                                    post = j;
                                    Log.e("tag", "onClick: yuan==" + number_j + "parseInt" + parseInt);
                                }
                            }
                        }
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (number_j == parseInt) {
                            for (int i = 0; i < list1.size(); i++) {
                                if (number_j==list1.get(i)){
                                    list1.remove(i);
                                }
                            }
                            mLayoutRl.removeView(myCircle);
                            mLayoutRl.removeView(textView2);
//                            lists.remove(i);
//                                            initData(lists);
                            Log.e("tag", "onClick: 2==" + number_j + "parseInt" + parseInt);
                        }
                    }
                });
                break;
            case 5: //四边形
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
                        int number_i, number_j = 0, post = 0;
                        //循环判断出来最小的数字
                        for (int i = 0; i < list1.size(); i++) {
                            for (int j = 0; j < list1.size() - i; j++) {
                                number_i = list1.get(j);
                                number_j = list1.get(j + i);
                                if (number_i < number_j) {
                                    int num = number_i;
                                    number_i = number_j;
                                    number_j = num;
                                    post = j;

                                }
                            }
                        }
                        /**
                         * 如果点击的数字是最小的，那么就消失
                         */
                        if (number_j == parseInt) {
                            for (int i = 0; i < list1.size(); i++) {
                                if (number_j==list1.get(i)){
                                    list1.remove(i);
                                }
                            }
                            mLayoutRl.removeView(myQuadrilateral);
                            textView3.setVisibility(View.GONE);
//                                        lists.remove(i);
//                                            initData(lists);
                            Log.e("tag", "onClick: 3==" + number_j + "parseInt" + parseInt + "post" + post);
                        }
                    }
                });
                break;
            case 6: //多边形
                if (myPolygon == null) {
                    myPolygon = new MyPolygon(this, null);
                    mLayoutRl.addView(myPolygon);
                    myPolygon.setData(bean);
                    final TextView textView4 = new TextView(this);
                    codeText(textView4, bean);
                    /**
                     * 监听点击事件
                     */
                    textView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String trim = textView4.getText().toString().trim();
                            int parseInt = Integer.parseInt(trim);
                            int number_i, number_j = 0, post = 0;
                            //循环判断出来最小的数字
                            for (int i = 0; i < list1.size(); i++) {
                                for (int j = 0; j < list1.size() - i; j++) {
                                    number_i = list1.get(j);
                                    number_j = list1.get(j + i);
                                    if (number_i < number_j) {
                                        int temp = number_i;
                                        number_i = number_j;
                                        number_j = temp;
                                        post = j;

                                    }
                                }
                            }

                            /**
                             * 如果点击的数字是最小的，那么就消失
                             */
                            if (number_j == parseInt) {
                                for (int i = 0; i < list1.size(); i++) {
                                    if (number_j==list1.get(i)){
                                        list1.remove(i);
                                    }
                                }
                                mLayoutRl.removeView(myPolygon);
                                mLayoutRl.removeView(textView4);
//                                            textView4.setVisibility(View.GONE);
//                                            lists.remove(i);
//                                            initData(lists);
                                Log.e("tag", "onClick: " + number_j + "parseInt" + parseInt + "post" + post);
                            }
                        }
                    });
                } else {
                    myPolygon1 = new MyPolygon(this, null);
                    mLayoutRl.addView(myPolygon1);
                    myPolygon1.setData(bean);
                    final TextView textView42 = new TextView(this);
                    codeText(textView42, bean);
                    /**
                     * 监听点击事件
                     */
                    textView42.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String trim = textView42.getText().toString().trim();
                            int parseInt = Integer.parseInt(trim);
                            int number_i, number_j = 0, post = 0;
                            //循环判断出来最小的数字
                            for (int i = 0; i < list1.size(); i++) {
                                for (int j = 0; j < list1.size() - i; j++) {
                                    number_i = list1.get(j);
                                    number_j = list1.get(j + i);
                                    if (number_i < number_j) {
                                        int num = number_i;
                                        number_i = number_j;
                                        number_j = num;
                                        post = j;
                                    }
                                }
                            }
                            /**
                             * 如果点击的数字是最小的，那么就消失
                             */
                            if (number_j == parseInt) {
                                for (int i = 0; i < list1.size(); i++) {
                                    if (number_j==list1.get(i)){
                                        list1.remove(i);
                                    }
                                }
                                mLayoutRl.removeView(myPolygon1);
                                mLayoutRl.removeView(textView42);
//                                            textView42.setVisibility(View.GONE);
//                                            lists.remove(i);
//                                            initData(lists);
                                Log.e("tag", "onClick: " + number_j + "parseInt" + parseInt + "post" + number_j);
                            }
                        }
                    });
                }
                break;
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
        int place_y = numberBean.getY();
        /**
         * 动态创建TextView
         * 添加到界面
         * 动态设置文字
         */

        mLayoutRl.addView(textView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        params.height = radius;
        params.width = radius;
//        params.setMargins(500-25,500-60,0,0);
        //设置位置
        params.setMargins(place_x - radius / 2, place_y - radius / 2 - 10, 0, 0);
        textView.setLayoutParams(params);
        textView.setText(number + "");
        //文字大小
        textView.setTextSize(radius / 3);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerCode = 0;
        timer = null;
    }
}
