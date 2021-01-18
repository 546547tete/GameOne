package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.bean.NumberBean;

public class MyQuadrilateral extends View {

    private final Paint paint;
    private Path path;
    int x1,y1;
    int x2,y2;
    int x3,y3;
    int x4,y4;
    public MyQuadrilateral(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path = new Path();

        path.moveTo(x1,y1);
        path.lineTo(x1,y1);
        path.lineTo(x2,y2);
        path.lineTo(x3,y3);
        path.lineTo(x4,y4);
//        path.lineTo(x1,y1);
        canvas.drawPath(path,paint);
        super.onDraw(canvas);
    }

    public void setData(NumberBean numberBean){
        int radius = numberBean.getRadius();
        int place_x = numberBean.getPlace_x();
        int place_y = numberBean.getPlace_y();
        int a1 = radius * radius;
        int a2 = radius * radius;
        int a3 = a1 + a2;
        int a4 = (int) Math.sqrt(a3);
        //底长/2
        int a5 = a4 / 2;

        int i = radius * radius;
        int i2 = a5 * a5;
        int i3 = i - i2;
        //垂直线
        int sqrt = (int) Math.sqrt(i3);
        x1=place_x-sqrt;
        y1=place_y-sqrt;

        x2 = place_x+sqrt;
        y2 = place_y-sqrt;

        x3 = place_x+sqrt;
        y3 = place_y+sqrt;

        x4 = place_x-sqrt;
        y4 = place_y+sqrt;
    }
}
