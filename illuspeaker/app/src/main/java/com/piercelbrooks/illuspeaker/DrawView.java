package com.piercelbrooks.illuspeaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {
    private Picture mPicture;
    private Drawable mDrawable;

    public DrawView(Context context) {
        super(context);
        initialize(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    static private void drawSomething(Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0x88FF0000);
        canvas.drawCircle(50, 50, 40, p);
        p.setColor(Color.GREEN);
        p.setTextSize(30);
        canvas.drawText("Pictures", 60, 60, p);
    }

    private void initialize(Context context) {
        setFocusable(true);
        setFocusableInTouchMode(true);
        mPicture = new Picture();
        drawSomething(mPicture.beginRecording(200, 100));
        mPicture.endRecording();
        mDrawable = new PictureDrawable(mPicture);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawPicture(mPicture);
        canvas.drawPicture(mPicture, new RectF(0, 100, getWidth(), 200));
        mDrawable.setBounds(0, 200, getWidth(), 300);
        mDrawable.draw(canvas);
    }
}