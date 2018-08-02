package com.sanshang.li.mybaseframwork.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by li on 2018/7/26.
 * WeChat 18571658038
 * author LiWei
 */

public class MyCustomView extends View{

    private Paint mPaint;
    private Context mContext;

    public MyCustomView(Context context) {
        this(context,null);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init();
    }

    private void init( ) {


        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 25, mContext.getResources().getDisplayMetrics());

        mPaint = new Paint();
        mPaint.setTextSize(textSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("hello world",100,100,mPaint);
    }
}
