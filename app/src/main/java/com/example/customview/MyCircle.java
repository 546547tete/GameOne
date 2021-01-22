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
import com.example.gameone.R;

public class MyCircle extends View {

    private Paint paint;
    private int radius;
    private int x;
    private int y;
    private int color = getResources().getColor(R.color.color_00FF00);

    int[] colors = {R.color.color_FF0000,
            R.color.color_FF7F00,
            R.color.color_FFFF00,
            R.color.color_00FF00,
            R.color.color_0000FF,
            R.color.color_2E2B5F,
            R.color.color_8B00FF
    };

    String[] colorStr = {"FF0000",
            "FF7F00",
            "FFFF00",
            "00FF00",
            "0000FF",
            "2E2B5F",
            "8B00FF"
    };

    public MyCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(x,y,radius,paint);
        super.onDraw(canvas);

    }

    public void setData(NumberBean.DataBean numberBean){

        String color = numberBean.getColor();
        for (int k = 0; k < colorStr.length-1; k++) {
            if (colorStr[k].equals(color)){
                this.color = getResources().getColor(colors[k]);
            }
        }
//        canvas.drawCircle(x,y,radius,paint);
        this.radius = numberBean.getRadius();
        this.x = numberBean.getX();
        this.y = numberBean.getY()+100;


    }
}
