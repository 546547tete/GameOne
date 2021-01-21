package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.gameone.R;

import androidx.annotation.Nullable;

public class MyLine extends View {

    private final Paint paint;
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

    public MyLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int firstx=50;
        int firsty=100;
        int secondx=300;
        int secondy=100;
        canvas.drawLine(firstx,firsty,secondx,secondy,paint);
        super.onDraw(canvas);

    }
}
