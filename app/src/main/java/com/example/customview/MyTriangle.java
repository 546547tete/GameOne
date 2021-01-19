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

public class MyTriangle extends View {

    private Paint paint;
    private Path path;
    private int x1,y1;
    private int x2,y2;
    private int x3,y3;
    private int color = Color.WHITE;
    public MyTriangle(Context context, @Nullable AttributeSet attrs) {
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
//        path.lineTo(x1,y1);
        canvas.drawPath(path,paint);
        super.onDraw(canvas);
    }

    public void setData(NumberBean.DataBean numberBean){
        int radius = numberBean.getNum();
        int place_x = numberBean.getX();
        int place_y = numberBean.getY();
        int i = radius * radius;
        int i1 = radius / 2;
        int i2 = i1 * i1;
        int i3 = i - i2;
        int sqrt = (int) Math.sqrt(i3);
        x1=place_x;
        y1=place_y-radius;

        x2 = place_x+sqrt;
        y2 = place_y+i1;

        x3 = place_x-sqrt;
        y3 = place_y+i1;
    }
}
