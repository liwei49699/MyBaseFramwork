package com.sanshang.li.mybaseframwork.custom.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by li on 2018/7/23.
 * WeChat 18571658038
 * author LiWei
 */

public class CircleProgressView extends View {

    private Paint mPaint;
    private int mViewWidth = 300;
    private int mViewHeight = 300;
    private int mRingWidth = 5;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

         mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //2.1：画-背景圆弧
        int centerX = mViewWidth / 2;
        int centerY = mViewHeight / 2;

        //2.2：画-当前进度圆弧
        mPaint.setStrokeWidth(mRingWidth);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        int radius = centerX - mRingWidth;
        //2.3：画-步数文字
        //2.4：提供一些方法

        //view上面至父布局的距离
//        int top = getTop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //触摸点相较于View左边的距离
//        float x = event.getX();
        //触摸点相较于屏幕左边的距离
//        float x = event.getRawX();
        return super.onTouchEvent(event);

    }
}
