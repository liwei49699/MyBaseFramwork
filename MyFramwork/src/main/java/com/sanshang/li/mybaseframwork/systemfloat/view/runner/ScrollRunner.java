package com.sanshang.li.mybaseframwork.systemfloat.view.runner;


import android.view.animation.BounceInterpolator;
import android.widget.Scroller;

/**
 * 悬浮框滑动的任务
 * Created by li on 2018/7/17.
 * WeChat 18571658038
 * author LiWei
 */

public class ScrollRunner implements Runnable {
    private Scroller mScroller;
    private ICarrier mCarrier;
    private int mDuration = 250;
    private int lastX, lastY;

    public ScrollRunner(ICarrier carrier) {
        mCarrier = carrier;
        mScroller = new Scroller(carrier.getContext(), new BounceInterpolator());
    }

    public void start(int dx, int dy) {
        start(dx, dy, mDuration);
    }

    public void start(int dx, int dy, int duration) {
        start(0, 0, dx, dy, duration);
    }

    public void start(int startX, int startY, int dx, int dy) {
        start(startX, startY, dx, dy, mDuration);
    }

    public void start(int startX, int startY, int dx, int dy, int duration) {
        this.mDuration = duration;
        mScroller.startScroll(startX, startY, dx, dy, duration);
        mCarrier.removeCallbacks(this);
        mCarrier.post(this);
        lastX = startX;
        lastY = startY;
    }

    public boolean isRunning() {
        return !mScroller.isFinished();
    }

    @Override
    public void run() {
        if (mScroller.computeScrollOffset()) {
            final int currentX = mScroller.getCurrX();
            final int currentY = mScroller.getCurrY();
            mCarrier.onMove(lastX, lastY, currentX, currentY);
            mCarrier.post(this);
            lastX = currentX;
            lastY = currentY;
        } else {
            mCarrier.removeCallbacks(this);
            mCarrier.onDone();
        }
    }
}
