package com.sanshang.li.mybaseframwork.custom.gesture;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by li on 2018/7/30.
 * WeChat 18571658038
 * author LiWei
 */

public class GestureListenerImpl implements GestureDetector.OnGestureListener {

    /**
     * 触摸屏幕时均会调用该方法
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * 按下且未移动和抬起时调用
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 单击屏幕时调用
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * 滑动时调用
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * 长按时调用
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 滑翔时调用
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
