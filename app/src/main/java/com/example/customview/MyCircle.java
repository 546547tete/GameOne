package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.bean.NumberBean;

public class MyCircle extends View {

    private Paint paint;
    private int radius;
    private int x;
    private int y;

    public MyCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x,y,radius,paint);
        super.onDraw(canvas);

    }

    public void setData(NumberBean numberBean){
        this.radius = numberBean.getRadius();
        this.x = numberBean.getPlace_x();
        this.y = numberBean.getPlace_x();
    }
}
