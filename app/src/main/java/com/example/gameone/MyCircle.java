package com.example.gameone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCircle extends View {

    private Paint paint;
    private int rect;
    private int x;
    private int y;

    public MyCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x,y,rect,paint);
        super.onDraw(canvas);

    }

    public void init(int rect, int x, int y){
        this.rect = rect;
        this.x = x;
        this.y = y;
    }
}
