package com.sanshang.li.mybaseframwork.systemfloat.view.runner;


import android.content.Context;

/**
 * 菜单项的状态回调
 * Created by li on 2018/7/17.
 * WeChat 18571658038
 * author LiWei
 */

public interface ICarrier {

    Context getContext();

    void onMove(int lastX, int lastY, int curX, int curY);

    void onDone();

    boolean post(Runnable runnable);

    boolean removeCallbacks(Runnable action);
}
