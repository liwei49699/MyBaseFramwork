package com.sanshang.li.mybaseframwork.custom.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sanshang.li.mybaseframwork.R;

/**
 * Created by li on 2018/8/1.
 * WeChat 18571658038
 * author LiWei
 */

public class CompletedView extends View {

    /**
     * 自定义属性
     */
    // 內圆颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 圆环背景颜色
    private int mRingBgColor;
    // 內圆半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;

    /**
     * 画笔
     */
    // 画內圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画圆环背景色的画笔
    private Paint mRingPaintBg;
    // 画字体的画笔
    private Paint mTextPaint;
    private Paint mTextPaint1;

    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;


    public CompletedView(Context context) {

        this(context,null);
    }

    public CompletedView(Context context, AttributeSet attrs) {

        this(context, attrs,0);
    }

    public CompletedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    //属性
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
        mRingBgColor = typeArray.getColor(R.styleable.TasksCompletedView_ringBgColor, 0xFFFFFFFF);

        typeArray.recycle();

        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    //初始化画笔
    private void initVariable() {
        //内圆
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(mCircleColor);
        //Paint.Style.STROKE 只绘制图形轮廓（描边）
        //Paint.Style.FILL 只绘制图形内容
        //Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容 (边 + 内容)
        mCirclePaint.setStyle(Paint.Style.FILL);

        //外圆弧背景
        mRingPaintBg = new Paint();
        mRingPaintBg.setAntiAlias(true);
        mRingPaintBg.setDither(true);

        mRingPaintBg.setColor(mRingBgColor);
        mRingPaintBg.setStyle(Paint.Style.STROKE);
        mRingPaintBg.setStrokeWidth(mStrokeWidth);


        //外圆弧
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setDither(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);
        //mRingPaint.setStrokeCap(Paint.Cap.ROUND);//设置线冒样式，有圆 有方

        //中间字
        mTextPaint = new Paint();
        mTextPaint1 = new Paint();
        mTextPaint.setDither(true);

        mTextPaint1.setColor(Color.BLUE);
        mTextPaint1.setStyle(Paint.Style.FILL);


        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mRingColor);
        mTextPaint.setTextSize(mRadius / 2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width;
        int height;
        //宽设置
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY :

                width = widthSize;
                break;
            default :

                width = (int) ((mRadius + mStrokeWidth) * 2);
                break;
        }

        //高设置
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.EXACTLY :

                height = heightSize;
                break;
            default :

                height = (int) ((mRadius + mStrokeWidth) * 2);
                break;
        }

        int radius = width > height ? height : width;
        setMeasuredDimension(radius,radius);
    }

    //画图
    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getMeasuredWidth() / 2;
        mYCenter = getMeasuredHeight() / 2;

        //内圆
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

        //外圆弧背景
        RectF oval1 = new RectF();
        oval1.left = (mXCenter - mRingRadius);
        oval1.top = (mYCenter - mRingRadius);
        oval1.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval1.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
        //圆弧所在的椭圆对象、圆弧的起始角度、圆弧的角度、是否经过半径连线
        canvas.drawArc(oval1, 0, 360, false, mRingPaintBg);

        //外圆弧
        if (mProgress > 0 ) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(oval, -90, ((float)mProgress / mTotalProgress) * 360, true, mRingPaint); //

            //字体
            String txt = mProgress + "%";

            Rect rect = new Rect();
            mTextPaint.getTextBounds(txt,0,txt.length(),rect);
            mTxtWidth = rect.width();
            mTxtHeight = rect.height();

            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 2, mTextPaint);
        }

        canvas.drawPoint(mXCenter,mYCenter,mRingPaintBg);


    }

    //设置进度
    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();//重绘
    }
}
