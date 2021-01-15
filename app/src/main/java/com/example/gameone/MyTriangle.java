package com.example.gameone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyTriangle extends View {

    private final Paint paint;
    private Path path;
    int x1,y1;
    int x2,y2;
    int x3,y3;
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

    public void initData(int radius,int place_x,int place_y){
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
