package com.sanshang.li.mybaseframwork.custom.group;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by li on 2018/8/2.
 * WeChat 18571658038
 * author LiWei
 */

public class CouponDisplayView extends LinearLayout {

    private Paint mPaint;
    /**
     * 圆间距
     */
    private float gap = 8;
    /**
     * 半径
     */
    private float radius = 10;
    /**
     * 圆数量
     */
    private int circleNum;
    /**
     * 多出的水平间距
     */
    private float remain;


    public CouponDisplayView(Context context) {
        this(context,null);
    }


    public CouponDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (remain==0){
            remain = (int)(w-gap)%(2*radius+gap);
        }
        circleNum = (int) ((w-gap)/(2*radius+gap));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < circleNum; i ++){

            // **前面的是定值 并不是累加（前面留一半，画完圆后剩一半）
            float x = gap + radius + remain / 2 + ((gap + radius * 2) * i);

            //圆心，半径，画笔
            canvas.drawCircle(x,0,radius,mPaint);
            canvas.drawCircle(x , getHeight(), radius, mPaint);
        }
    }
}
