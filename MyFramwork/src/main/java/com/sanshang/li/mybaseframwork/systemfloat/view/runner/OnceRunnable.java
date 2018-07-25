package com.sanshang.li.mybaseframwork.systemfloat.view.runner;

import android.view.View;

/**
 * 要运行的任务
 * Created by li on 2018/7/17.
 * WeChat 18571658038
 * author LiWei
 */

public abstract class OnceRunnable implements Runnable {

    private boolean mScheduled;

    public final void run() {
        onRun();
        mScheduled = false;
    }

    public abstract void onRun();

    public void postSelf(View carrier) {
        postDelaySelf(carrier, 0);
    }

    public void postDelaySelf(View carrier, int delay) {
        if (!mScheduled) {
            carrier.postDelayed(this, delay);
            mScheduled = true;
        }
    }

    public boolean isRunning() {
        return mScheduled;
    }

    public void removeSelf(View carrier) {
        mScheduled = false;
        carrier.removeCallbacks(this);
    }
}
