package com.sanshang.li.mybaseframwork.systemfloat.view.ball;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.sanshang.li.mybaseframwork.systemfloat.util.FloatBallManager;
import com.sanshang.li.mybaseframwork.systemfloat.util.FloatBallUtils;

/**
 * 状态栏
 * 高度和监听视图变化
 * Created by li on 2018/7/17.
 * WeChat 18571658038
 * author LiWei
 */

public class StatusBar extends View {

    private Context mContext;
    private FloatBallManager mFloatBallManager;
    private WindowManager.LayoutParams mLayoutParams;
    private boolean isAdded;
    private OnLayoutChangeListener layoutChangeListener = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            mFloatBallManager.onStatusBarHeightChange();
        }
    };

    public StatusBar(Context context, FloatBallManager floatBallManager) {
        super(context);
        mContext = context;
        mFloatBallManager = floatBallManager;
        mLayoutParams = FloatBallUtils.getStatusBarLayoutParams(context);
    }

    public void attachToWindow(WindowManager wm) {
        if (!isAdded) {
            addOnLayoutChangeListener(layoutChangeListener);
            wm.addView(this, mLayoutParams);
            isAdded = true;
        }
    }

    public void detachFromWindow(WindowManager windowManager) {
        if (!isAdded) return;
        isAdded = false;
        removeOnLayoutChangeListener(layoutChangeListener);
        if (getContext() instanceof Activity) {
            windowManager.removeViewImmediate(this);
        } else {
            windowManager.removeView(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getStatusBarHeight() {
        int[] windowParams = new int[2];
        int[] screenParams = new int[2];
        getLocationInWindow(windowParams);
        getLocationOnScreen(screenParams);
        return screenParams[1] - windowParams[1];
    }
}