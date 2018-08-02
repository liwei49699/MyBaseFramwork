package com.sanshang.li.mybaseframwork.custom.draw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.sanshang.li.mybaseframwork.R;
import com.sanshang.li.mybaseframwork.util.DeviceUtils;

/**
 * Created by li on 2018/7/30.
 * WeChat 18571658038
 * author LiWei
 */

public class MyDrawBoardView extends View {

    private int mBoardBack;
    private int mPaintColor;
    private float mPaintWidth;

    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;

    public MyDrawBoardView(Context context) {
        this(context,null);
    }

    public MyDrawBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyDrawBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MyDrawBoardView);

        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics());

        mBoardBack = attributes.getColor(R.styleable.MyDrawBoardView_boardBack, Color.YELLOW);
        mPaintColor = attributes.getColor(R.styleable.MyDrawBoardView_paintColor, Color.BLACK);
        mPaintWidth = attributes.getDimension(R.styleable.MyDrawBoardView_paintWidth, width);

        //资源回收
        attributes.recycle();

        setBackgroundColor(mBoardBack);

        mPaint = new Paint();
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(mPaintWidth);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mPath = new Path();

    }

    /**
     * moveTo 不会进行绘制，只用于移动移动画笔。
     * lineTo 用于进行直线绘制
     * quadTo 用于绘制圆滑曲线，即贝塞尔曲线
     * cubicTo 同样是用来实现贝塞尔曲线的
     * arcTo 用于绘制弧线（实际是截取圆或椭圆的一部分）
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        int action = event.getAction();
        switch(action){

            case MotionEvent.ACTION_DOWN:

                //moveTo 不会进行绘制，只用于移动移动画笔
                mPath.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:

                //lineTo 用于进行直线绘制
                mPath.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        //重绘 会回调onDraw方法
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(mCanvas == null) {

            mCanvas = canvas;
        }
        super.onDraw(canvas);

        canvas.drawPath(mPath,mPaint);

    }

    public void clearContent(){

        if(mCanvas != null) {

            Paint p = new Paint();
            //清屏
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mCanvas.drawPaint(p);
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        }
    }
}
