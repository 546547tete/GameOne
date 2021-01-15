package com.example.gameone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyLine extends View {

    private final Paint paint;

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
