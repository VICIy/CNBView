package com.viciy.cnbview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Baiqiang on 2017/5/31.
 */

public class CNBView extends View {

    private static final String TAG = "CNBView";
    private Paint mPaint;
    private int mWidthSize;
    private int mHeightSize;
    private int mHeightStart;
    private int mHeightEnd;
    private int mWidthStart = 0;
    private int mWidthEnd = 0;
    private boolean mIsFrist = true;
    float x1;
    float x2;

    public CNBView(Context context) {
        this(context, null);
    }

    public CNBView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public CNBView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        mWidthSize = MeasureSpec.getSize(widthSpec);
        mHeightSize = MeasureSpec.getSize(heightSpec);
        mHeightStart = mHeightSize / 2 - 5;
        mHeightEnd = mHeightSize / 2 + 5;

        Log.e(TAG, mWidthSize + "<---mWidthSize");
        Log.e(TAG, mHeightSize + "<---mHeightSize");
        Log.e(TAG, mHeightStart + "<---mHeightStart");
        Log.e(TAG, mHeightEnd + "<---mHeightEnd");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(10, mHeightStart, mWidthSize - 10, mHeightEnd, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(mWidthStart-10, mHeightStart, mWidthEnd+10, mHeightEnd, mPaint);
        if (mIsFrist) {
            canvas.drawCircle(10,mHeightSize/2,10,mPaint);
        } else {
            if (mWidthStart == 0) {
                canvas.drawCircle(10,mHeightSize/2,10,mPaint);
            } else {
                canvas.drawCircle(mWidthStart - 10, mHeightSize / 2, 10, mPaint);
            }
            canvas.drawCircle(mWidthEnd + 10, mHeightSize / 2, 10, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mIsFrist) {
                    x1 = 0;

                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsFrist) {
                    mIsFrist = false;
                }
                x2 = (int) event.getX();
                float min = Math.min(x1, x2);
                mWidthStart = (int) min;
                mWidthEnd = (int) (x1 + x2 - min);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                x1 = event.getX();
                break;
        }
        return true;
    }
}
