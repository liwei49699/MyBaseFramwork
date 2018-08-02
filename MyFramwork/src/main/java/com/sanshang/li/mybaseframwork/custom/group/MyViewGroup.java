package com.sanshang.li.mybaseframwork.custom.group;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by li on 2018/7/26.
 * WeChat 18571658038
 * author LiWei
 */

public class MyViewGroup extends ViewGroup {

    public MyViewGroup(Context context) {
        this(context,null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() > 0) {
            View childView = getChildAt(0);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

    }

    /**
     * 源码分析：LinearLayout复写的onLayout（）
     * 注：复写的逻辑 和 LinearLayout measure过程的 onMeasure()类似
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (getChildCount() > 0) {
            View childView = getChildAt(0);
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
        }
    }
}
